package lib

import io.flow.proxy.auth.v0.models.AuthData
import javax.inject.Inject

/**
  * Defines the data that goes into the flow auth set by the proxy server.
  */
final class FlowAuthV2 @Inject ()(
  flowJwtAuthDataProvider: FlowJwtAuthDataProvider,
) {

  def jwt(authData: AuthData): String = {
    flowJwtAuthDataProvider.instance.encodeAuthData(authData)
  }

}
