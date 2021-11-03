package auth

import io.flow.token.v0.interfaces.Client
import io.flow.token.v0.models._
import lib.{Constants, FlowAuth, ResolvedToken}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Queries token server to check if the specified token is a known
  * valid token.
  */
trait TokenAuth extends LoggingHelper {

  def tokenClient: Client

  def resolveToken(
    requestId: String,
    token: String
  )(
    implicit ec: ExecutionContext
  ): Future[Option[ResolvedToken]] = {
    if (Constants.StopWords.contains(token)) {
      // javascript sending in 'undefined' or 'null' as session id
      Future.successful(None)
    } else {
      doResolveToken(
        requestId = requestId,
        token = token
      )
    }
  }

  private[this] def doResolveToken(
    requestId: String,
    token: String
  )(
    implicit ec: ExecutionContext
  ): Future[Option[ResolvedToken]] = {
    tokenClient.tokens.postAuthentications(
      TokenAuthenticationForm(token = token),
      requestHeaders = FlowAuth.headersFromRequestId(requestId)
    ).map { tokenReference =>
      fromTokenReference(requestId, tokenReference)

    }.recover {
      case io.flow.token.v0.errors.UnitResponse(404) => {
        None
      }

      case ex: Throwable => {
        val msg = "Could not communicate with token server"
        log(requestId).error(msg, ex)
        throw new RuntimeException(msg, ex)
      }
    }
  }

  def fromTokenReference(requestId: String, token: TokenReference): Option[ResolvedToken] = {
    token match {
      case t: ChannelTokenReference => Some(
        ResolvedToken(
          requestId = requestId,
          userId = Some(t.user.id),
          channelId = Some(t.channel.id),
        )
      )

      case t: OrganizationTokenReference => Some(
        ResolvedToken(
          requestId = requestId,
          userId = Some(t.user.id),
          environment = Some(t.environment),
          organizationId = Some(t.organization.id)
        )
      )

      case t: PartnerTokenReference => Some(
        ResolvedToken(
          requestId = requestId,
          userId = Some(t.user.id),
          environment = Some(t.environment),
          partnerId = Some(t.partner.id)
        )
      )

      case TokenReferenceUndefinedType(other) => {
        log(requestId).withKeyValue("type", other).warn("TokenReferenceUndefinedType")
        None
      }
    }
  }
}