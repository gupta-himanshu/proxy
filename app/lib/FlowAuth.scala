package lib

import javax.inject.{Inject, Singleton}
import pdi.jwt.{Jwt, JwtAlgorithm, JwtClaim, JwtHeader}

object FlowAuth {

  def headersForRequestId(requestId: String): Seq[(String, String)] = {
    Seq(
      Constants.Headers.FlowRequestId -> requestId
    )
  }

}

/**
  * Defines the data that goes into the flow auth set by the proxy server.
  */
@Singleton
final class FlowAuth @Inject () (
  flowJwtAuthDataProvider: FlowJwtAuthDataProvider,
) {
  private[this] val header = JwtHeader(JwtAlgorithm.HS256)

  def jwt(token: LegacyToken): String = {
    val claimsSet = JwtClaim() ++ (token.toMap.toSeq: _*)
    Jwt.encode(header, claimsSet, flowJwtAuthDataProvider.instance.saltProvider.preferredSalt)
  }

}
