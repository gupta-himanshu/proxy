package auth

import io.flow.common.v0.models.Environment
import io.flow.play.util.AuthHeaders
import io.flow.util.Constants
import javax.inject.{Inject, Singleton}

@Singleton
class RequestHeadersUtil @Inject()(
  authHeaders: AuthHeaders
) {

  // TODO: Ideally remove this as we are hardcoding environment
  def organizationAsSystemUser(
    organizationId: String,
    requestId: String = AuthHeaders.generateRequestId("proxy")
  ): Seq[(String, String)] = {
    authHeaders.headers(
      AuthHeaders.organization(
        user = Constants.SystemUser,
        org = organizationId,
        environment = Environment.Production,
        requestId = requestId
      )
    )
  }

}
