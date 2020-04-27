package handlers

import io.flow.proxy.auth.v0.models.AuthData
import lib.{ProxyRequest, Route, Server}
import play.api.libs.ws.WSClient
import play.api.mvc.Result

import scala.concurrent.Future

trait Handler {

  def process(
    wsClient: WSClient,
    server: Server,
    request: ProxyRequest,
    route: Route,
    authData: AuthData
  ): Future[Result]

}
