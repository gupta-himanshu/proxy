package auth

import java.util.UUID
import helpers.BasePlaySpec
import io.flow.channel.v0.models.ChannelReference
import io.flow.token.v0.mock
import io.flow.common.v0.models.{Environment, OrganizationReference, UserReference}
import io.flow.log.RollbarLogger
import io.flow.token.v0.models._
import lib.ResolvedToken

object TokenMockClient extends mock.Client

class TokenAuthSpec extends BasePlaySpec {

  private[this] val tokenTestAuth = new TokenAuth with mock.Client {
    override def tokenClient = TokenMockClient
    override def logger: RollbarLogger = app.injector.instanceOf[RollbarLogger]
  }

  private[this] val requestId = UUID.randomUUID.toString

  "from org" in {
    val token = OrganizationTokenReference(
      id = "0",
      organization = OrganizationReference(id = "tst"),
      environment = Environment.Production,
      user = UserReference("5")
    )

    tokenTestAuth.fromTokenReference(requestId, token) must equal(
      Some(
        ResolvedToken(
          requestId = requestId,
          userId = Some("5"),
          organizationId = Some("tst"),
          channelId = None,
          partnerId = None,
          role = None,
          environment = Some(Environment.Production)
        )
      )
    )
  }

  "from channel" in {
    val token = ChannelTokenReference(
      id = "0",
      channel = ChannelReference(id = "tst"),
      user = UserReference("5")
    )

    tokenTestAuth.fromTokenReference(requestId, token) must equal(
      Some(
        ResolvedToken(
          requestId = requestId,
          userId = Some("5"),
          organizationId = None,
          channelId = Some("tst"),
          partnerId = None,
          role = None,
          environment = None,
        )
      )
    )
  }

  "from partner" in {
    val token = PartnerTokenReference(
      id = "0",
      partner = TokenPartnerReference(id = "foo"),
      environment = Environment.Production,
      user = UserReference("5")
    )

    tokenTestAuth.fromTokenReference(requestId, token) must equal(
      Some(
        ResolvedToken(
          requestId = requestId,
          userId = Some("5"),
          organizationId = None,
          channelId = None,
          partnerId = Some("foo"),
          role = None,
          environment = Some(Environment.Production)
        )
      )
    )
  }
}
