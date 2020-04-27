package lib

import javax.inject.{Inject, Singleton}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim, JwtHeader}

object FlowAuth {

  def headersFromRequestId(requestId: String): Seq[(String, String)] = {
    Seq(
      Constants.Headers.FlowRequestId -> requestId
    )
  }

}

/**
  * Defines the data that goes into the flow auth set by the proxy server.
  */
final class FlowAuth @Inject () (
  config: Config,
) {

  def headers(token: ResolvedToken): Seq[(String, String)] = {
    FlowAuth.headersFromRequestId(token.requestId) ++ Seq(
      Constants.Headers.FlowAuth -> jwt(token)
    )
  }

  /**
    * Returns the string jwt token of the specified auth data.
    */
  def jwt(
    token: ResolvedToken
  ): String = {
    val claimsSet = JwtClaim() ++ (token.toMap.toSeq: _*)
    Jwt.encode(header, claimsSet, config.jwtSalt)
  }

}
