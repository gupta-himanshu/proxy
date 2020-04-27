package auth

import io.flow.proxy.auth.v0.models.AuthData
import io.flow.token.v0.interfaces.Client
import io.flow.token.v0.models._
import lib.{Constants, FlowAuth}
import org.joda.time.DateTime

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
  ): Future[Option[AuthData]] = {
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
  ): Future[Option[AuthData]] = {
    tokenClient.tokens.postAuthentications(
      TokenAuthenticationForm(token = token),
      requestHeaders = FlowAuth.headersForRequestId(requestId)
    ).map { tokenReference =>
      fromTokenReference(requestId, tokenReference)
    }.recoverWith {
      case io.flow.token.v0.errors.UnitResponse(404) => {
        Future.successful(None)
      }

      case ex: Throwable => {
        val msg = "Could not communicate with token server"
        log(requestId).error(msg, ex)
        Future.failed(ex)
      }
    }
  }

  def fromTokenReference(requestId: String, token: TokenReference): Option[AuthData] = {
    def base(userId: String) = AuthData(
      requestId = requestId,
      createdAt = DateTime.now,
      userId = Some(userId),
    )

    token match {
      case t: OrganizationTokenReference => Some(
        base(t.user.id).copy(
          organizationId = Some(t.organization.id)
        )
      )

      case t: PartnerTokenReference => Some(
        base(t.user.id).copy(
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
