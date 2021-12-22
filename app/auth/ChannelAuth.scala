package auth

import io.flow.channel.v0.interfaces.Client
import io.flow.channel.v0.models.ChannelAuthorizationForm
import lib.{Constants, FlowAuth, ResolvedToken}

import scala.annotation.nowarn
import scala.concurrent.{ExecutionContext, Future}

/**
  * Queries organization server to authorize this user for this
  * channel.
  */
trait ChannelAuth extends LoggingHelper {

  def channelClient: Client
  def flowAuth: FlowAuth

  def authorizeChannel(
    token: ResolvedToken,
    channel: String
  )(
    implicit ec: ExecutionContext
  ): Future[Option[ResolvedToken]] = {
    if (Constants.StopWords.contains(channel)) {
      // javascript sending in 'undefined' or 'null' as session id
      Future.successful(None)
    } else {
      doAuthorizeChannel(
        token = token,
        channel = channel
      )
    }
  }

  @nowarn("msg=deprecated")
  private[this] def doAuthorizeChannel(
    token: ResolvedToken,
    channel: String
  )(
    implicit ec: ExecutionContext
  ): Future[Option[ResolvedToken]] = {
    val authFuture = token.channelId match {
      case Some(_) => {
        channelClient.channelAuthorizations.post(
          ChannelAuthorizationForm(
            channelId = channel
          ),
          requestHeaders = flowAuth.headers(token)
        )
      }

      case None => Future.failed(new Exception("Channel Id does not exist."))
    }

    authFuture.map { _ =>
      Some(
        token.copy(
          channelId = token.channelId,
          environment = token.environment
        )
      )
    }.recover {
      case io.flow.organization.v0.errors.UnitResponse(code) if code == 401 => None

      case io.flow.organization.v0.errors.UnitResponse(code) => {
        log(token.requestId).
          withKeyValue("http_status_code", code).
          warn("Unexpected HTTP Status Code during channel token authorization - request will NOT be authorized")
        None
      }

      case ex: Throwable => {
        log(token.requestId).
          withKeyValue("url", channelClient.baseUrl).
          warn("Error communicating with organization server", ex)
        sys.error("Error communicating with organization server")
      }
    }
  }
}
