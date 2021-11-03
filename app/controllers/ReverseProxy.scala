package controllers

import auth.RequestHeadersUtil
import cats.data.Validated.{Invalid, Valid}
import cats.implicits._
import io.flow.customer.v0.{Client => CustomerClient}
import io.flow.log.RollbarLogger
import io.flow.organization.v0.{Client => OrganizationClient}
import io.flow.session.v0.{Client => SessionClient}
import io.flow.token.v0.{Client => TokenClient}
import io.opentracing.util.GlobalTracer

import javax.inject.{Inject, Singleton}
import lib._
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ReverseProxy @Inject () (
  authorizationParser: AuthorizationParser,
  val flowAuth: FlowAuth,
  val logger: RollbarLogger,
  proxyConfigFetcher: ProxyConfigFetcher,
  serverProxyFactory: ServerProxy.Factory,
  val controllerComponents: ControllerComponents,
  ws: play.api.libs.ws.WSClient,
  override val requestHeadersUtil: RequestHeadersUtil
)(implicit ec: ExecutionContext) extends BaseController
  with lib.Errors
  with auth.OrganizationAuth
  with auth.TokenAuth
  with auth.SessionAuth
  with auth.CustomerAuth
{

  val index: Index = proxyConfigFetcher.current()

  override val organizationClient: OrganizationClient = {
    val server = mustFindServerByName("organization")
    logger.withKeyValue("base_url", server.host).info("Creating OrganizationClient")
    new OrganizationClient(ws, baseUrl = server.host)
  }

  override val sessionClient: SessionClient = {
    val server = mustFindServerByName("session")
    logger.withKeyValue("base_url", server.host).info("Creating SessionClient")
    new SessionClient(ws, baseUrl = server.host)
  }

  override val tokenClient: TokenClient = {
    val server = mustFindServerByName("token")
    logger.withKeyValue("base_url", server.host).info("Creating TokenClient")
    new TokenClient(ws, baseUrl = server.host)
  }

  override val customerClient: CustomerClient = {
    val server = mustFindServerByName("customer")
    logger.withKeyValue("base_url", server.host).info("Creating CustomerClient")
    new CustomerClient(ws, baseUrl = server.host)
  }

  private[this] val proxies: Map[String, ServerProxy] = {
    logger.
      withKeyValues("source", index.config.sources.map(_.uri)).
      info("ReverseProxy loading config")

    val all = scala.collection.mutable.Map[String, ServerProxy]()
    index.config.servers.map { s =>
      if (all.isDefinedAt(s.name)) {
        logger.
          withKeyValue("name", s.name).
          error("Duplicate server")
        sys.error(s"Duplicate server with name[${s.name}]")
      } else {
        all += (s.name -> serverProxyFactory(s))
      }
    }
    all.toMap
  }

  def handle: Action[RawBuffer] = Action.async(parse.raw) { request =>
    ProxyRequest.validate(request)(logger).andThen { proxyRequest =>
      if (proxyRequest.requestEnvelope) {
        proxyRequest.parseRequestEnvelope()
      } else {
        proxyRequest.validNec
      }
    } match {
      case Valid(proxyRequest) => internalHandle(proxyRequest)
      case Invalid(errors) => Future.successful {
        UnprocessableEntity(genericErrors(errors.toList))
      }
    }
  }

  private[this] def internalHandle(request: ProxyRequest): Future[Result] = {
    index.resolve(request.method, request.path) match {
      case None => {
        Future.successful(
          request.responseUnprocessableEntity(
            s"HTTP operation '${request.method} ${request.path}' is not defined"
          )
        )
      }

      case Some(route) => {
        internalHandleValid(request, route)
      }
    }
  }

  private[this] def internalHandleValid(request: ProxyRequest, route: Operation) = {
    authorizationParser.parse(request.headers.get("Authorization")) match {
      case Authorization.NoCredentials => {
        proxyPostAuth(request, route, token = ResolvedToken(requestId = request.requestId))
      }

      case Authorization.Unrecognized => Future.successful(
        request.responseUnauthorized("Authorization header value must start with one of: " + Authorization.Prefixes.all.mkString(", "))
      )

      case Authorization.InvalidApiToken => Future.successful(
        request.responseUnauthorized("API Token is not valid")
      )

      case Authorization.InvalidJwt(missing) => Future.successful(
        request.responseUnauthorized(s"JWT Token is not valid. Missing ${missing.mkString(", ")} from the JWT Claimset")
      )

      case Authorization.InvalidBearer => Future.successful(
        request.responseUnauthorized("Value for Bearer header was not formatted correctly. We expect a JWT Token.")
      )

      case Authorization.Token(token) => {
        resolveToken(
          requestId = request.requestId,
          token = token
        ).flatMap {
          case None => Future.successful(
            request.responseUnauthorized("API Token is not valid")
          )
          case Some(t) => {
            proxyPostAuth(request, route, token = t)
          }
        }
      }

      case Authorization.Session(sessionId) => {
        internalResolveSession(
          request = request,
          route = route,
          sessionId = sessionId
        )
      }

      case Authorization.User(userId) => {
        proxyPostAuth(
          request,
          route,
          ResolvedToken(
            requestId = request.requestId,
            userId = Some(userId)
          )
        )
      }

      case Authorization.Customer(number, sessionId) => {
        resolveCustomer(
          requestId = request.requestId,
          customerNumber = number,
          sessionId = sessionId
        ).flatMap {
          // fallback to Authorization.Session
          case None => {
            internalResolveSession(
              request = request,
              route = route,
              sessionId = sessionId
            )
          }
          case Some(token) => {
            proxyPostAuth(request, route, token)
          }
        }
      }
    }
  }

  private[this] def internalResolveSession(
    request: ProxyRequest,
    route: Operation,
    sessionId: String
  ): Future[Result] = {
    resolveSession(
      requestId = request.requestId,
      sessionId = sessionId
    ).flatMap {
      case None => Future.successful(
        request.responseUnauthorized("Session is not valid")
      )
      case Some(token) => {
        proxyPostAuth(request, route, token)
      }
    }
  }

  private[this] def proxyPostAuth(
    request: ProxyRequest,
    route: Operation,
    token: ResolvedToken
  ): Future[Result] = {
    resolve(request, route, token).flatMap {
      case Left(result) => {
        Future.successful(result)
      }

      case Right(operation) => {
        operation.route.organization(request.path) match {
          case None => {
            operation.route.channel(request.path) match {
              case None => {
                operation.route.partner(request.path) match {
                  case None => proxyDefault(operation, request, token)
                  case Some(partner) => {
                    // should return 401 if the path is for a partner route, but the token doesn't have an explicit partnerId
                    token.partnerId match {
                      case None => Future.successful(request.responseUnauthorized(invalidPartnerMessage(partner)))
                      case Some(_) => proxyPartner(operation, partner, request, token)
                    }
                  }
                }
              }
              case Some(channel) => {
                // should return 401 if the path is for a partner route, but the token doesn't have an explicit partnerId
                token.channelId match {
                  case None => Future.successful(request.responseUnauthorized(invalidChannelMessage(channel)))
                  case Some(_) => proxyChannel(operation, channel, request, token)
                }
              }
            }
          }

          case Some(org) => {
            // should return 401 if route is for an org, but token is a partner token
            // note that console uses a token without an org, just a user - so can't be too strict here
            (token.channelId, token.partnerId) match {
              case (None, None) => proxyOrganization(operation, org, request, token)
              case (Some(_), _) => Future.successful(request.responseUnauthorized(
                s"Token is associated with a channel and not the organization '$org'"
              ))
              case (_, Some(_)) => Future.successful(request.responseUnauthorized(
                s"Token is associated with a partner and not the organization '$org'"
              ))
            }
          }
        }
      }
    }
  }

  private[this] def proxyDefault(
    operation: Operation,
    request: ProxyRequest,
    token: ResolvedToken
  ): Future[Result] = {
    lookup(operation.server.name).proxy(
      request,
      operation.route,
      token,
    )
  }

  private[this] def proxyOrganization(
    operation: Operation,
    organization: String,
    request: ProxyRequest,
    token: ResolvedToken
  ): Future[Result] = {

    // Capture organization id in datadog tracing span
    val span = Option(GlobalTracer.get().activeSpan())
    span.foreach(_.setBaggageItem("flow.organization", organization))

    token.userId match {
      case None => {
        token.organizationId match {
          case None => {
            // Pass to backend w/ no auth headers and let backend enforce
            // if path requires auth or not. Needed to support use case
            // like adding a credit card over JSONP or anonymous org
            // access from sessions
            proxyDefault(operation, request, token)
          }

          case Some(tokenOrganizationId) => {
            if (tokenOrganizationId == organization) {
              proxyDefault(operation, request, token)
            } else {
              Future.successful(
                request.responseUnauthorized(
                  s"Session Id belongs to organization '${tokenOrganizationId}' and not '$organization'"
                )
              )
            }
          }
        }
      }

      case Some(_) => {
        authorizeOrganization(token, organization).flatMap {
          case None => Future.successful(
            request.responseUnauthorized(
              s"Token is not associated with the organization '$organization'"
            )
          )

          case Some(orgToken) => {
            // Use org token here as the data returned came from the
            // organization service (supports having a sandbox token
            // on a production org)
            proxyDefault(operation, request, orgToken)
          }
        }
      }
    }
  }

  private[this] def proxyChannel(
    operation: Operation,
    channel: String,
    request: ProxyRequest,
    token: ResolvedToken
  ): Future[Result] = {
    token.userId match {
      case None => {
        // Currently all channel requests require authorization. Deny
        // access as there is no auth token present.
        Future.successful(
          request.responseUnauthorized("Missing authorization headers")
        )
      }

      case Some(_) => {
        if (token.channelId.contains(channel)) {
          proxyDefault(operation, request, token)
        } else {
          Future.successful(
            request.responseUnauthorized(invalidChannelMessage(channel))
          )
        }
      }
    }
  }

  private[this] def proxyPartner(
    operation: Operation,
    partner: String,
    request: ProxyRequest,
    token: ResolvedToken
  ): Future[Result] = {
    token.userId match {
      case None => {
        // Currently all partner requests require authorization. Deny
        // access as there is no auth token present.
        Future.successful(
          request.responseUnauthorized("Missing authorization headers")
        )
      }

      case Some(_) => {
        if (token.partnerId.contains(partner)) {
          lookup(operation.server.name).proxy(
            request,
            operation.route,
            token,
          )
        } else {
          Future.successful(
            request.responseUnauthorized(invalidPartnerMessage(partner))
          )
        }
      }
    }
  }

  /**
    * Resolves the incoming method and path to a specific operation. Also implements
    * overrides from incoming request headers:
    *
    *   - headers['X-Flow-Server']: If specified we use this server name
    *   - headers['X-Flow-Host']: If specified we use this host
    *
    * If any override headers are specified, we also verify that we
    * have an auth token identifying a user that is a member of the
    * flow organization. Otherwise we return an error.
    */
  private[this] def resolve(
    request: ProxyRequest,
    route: Operation,
    token: ResolvedToken
  ): Future[Either[Result, Operation]] = {
    val path = request.path
    val serverNameOverride: Option[String] = request.headers.get(Constants.Headers.FlowServer)
    val hostOverride: Option[String] = request.headers.get(Constants.Headers.FlowHost)

    if (serverNameOverride.isEmpty && hostOverride.isEmpty) {
      Future.successful(Right(route))
    } else {
      token.userId match {
        case None => Future.successful(
          Left(
            request.responseUnauthorized(s"Must authenticate to specify value for header '${Constants.Headers.FlowServer}' or '${Constants.Headers.FlowHost}'")
          )
        )

        case Some(_) => {
          authorizeOrganization(token, Constants.FlowOrganizationId).map {
            case None => {
              Left(
                request.responseUnauthorized(invalidOrgMessage(Constants.FlowOrganizationId))
              )
            }

            case Some(_) => {
              hostOverride match {
                case Some(host) => {
                  if (host.startsWith("http://") || host.startsWith("https://")) {
                    Right(
                      Operation(
                        route = Route(
                          method = request.method,
                          path = path
                        ),
                        server = Server(name = "override", host = host, logger = logger)
                      )
                    )
                  } else {
                    Left(
                      request.responseUnprocessableEntity(
                        s"Value for header '${Constants.Headers.FlowHost}' must start with http:// or https://"
                      )
                    )
                  }
                }

                case None => {
                  val name = serverNameOverride.getOrElse {
                    sys.error("Expected server name to be set")
                  }
                  findServerByName(name) match {
                    case None => {
                      Left(
                        request.responseUnprocessableEntity(
                          s"Invalid server name from Request Header '${Constants.Headers.FlowServer}'"
                        )
                      )
                    }

                    case Some(server) => {
                      Right(
                        Operation(
                          Route(
                            method = request.method,
                            path = path
                          ),
                          server = server
                        )
                      )
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  private[this] def lookup(name: String): ServerProxy = {
    proxies.getOrElse(name, sys.error(s"No proxy defined for the server with name[$name]"))
  }

  private[this] def findServerByName(name: String): Option[Server] = {
    index.config.servers.find(_.name == name)
  }

  private[this] def mustFindServerByName(name: String): Server = {
    findServerByName(name).getOrElse {
      sys.error(s"There is no server named '$name' in the current config: " + index.config.sources.map(_.uri))
    }
  }

  private[this] def invalidChannelMessage(channel: String): String = {
    channel.trim.toLowerCase match {
      case ":channel" => {
        s"Please replace ':channel' with your channel id"
      }
      case _ => {
        s"Not authorized to access channel '$channel' or the channel does not exist"
      }
    }
  }

  private[this] def invalidOrgMessage(organization: String): String = {
    organization.trim.toLowerCase match {
      case ":organization" => {
        s"Please replace ':organization' with your organization id"
      }
      case _ => {
        s"Not authorized to access organization '$organization' or the organization does not exist"
      }
    }
  }

  private[this] def invalidPartnerMessage(partner: String): String = {
    partner.trim.toLowerCase match {
      case ":partner" => {
        s"Please replace ':partner' with your partner id"
      }
      case _ => {
        s"Not authorized to access partner '$partner' or the partner does not exist"
      }
    }
  }

}
