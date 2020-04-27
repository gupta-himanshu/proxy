package auth

import helpers.BasePlaySpec
import io.flow.proxy.auth.v0.models.AuthData
import lib.{Config, FlowAuth, LegacyToken}
import org.joda.time.DateTime
import org.scalatest.TryValues
import pdi.jwt.{JwtAlgorithm, JwtJson}

class FlowAuthSpec extends BasePlaySpec with TryValues {

  private val flowAuth = app.injector.instanceOf[FlowAuth]
  private val jwtKey = app.injector.instanceOf[Config].jwtSalt

  private[this] def makeLegacyToken(): LegacyToken = {
    LegacyToken(
      AuthData(
        requestId = createTestId(),
        createdAt = DateTime.now,
        sessionId = Some(createTestId()),
      ),
      environment = None,
      role = None,
    )
  }

  "FlowAuth" should {

    "create jwt" in {
      val token = makeLegacyToken()
      val jwt = flowAuth.jwt(token)

      val decoded = JwtJson.decodeJson(token = jwt, key = jwtKey, algorithms = JwtAlgorithm.allHmac).success.value

      (decoded \ "request_id").as[String] mustBe "123"
      (decoded \ "session").as[String] mustBe "abc"
    }

  }

}
