package lib

import io.flow.auth.v2.{JwtAuthData, SimpleJwtSaltProvider}
import io.flow.log.RollbarLogger
import javax.inject.Inject

class FlowJwtAuthDataProvider @Inject()(
  config: Config,
  logger: RollbarLogger
) {
  val instance: JwtAuthData = JwtAuthData(
    saltProvider = SimpleJwtSaltProvider(config.jwtSalt),
    logger = logger,
  )
}
