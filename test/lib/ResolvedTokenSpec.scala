package lib

import java.util.UUID

import helpers.BasePlaySpec
import io.flow.common.v0.models.{Environment, Role}
import org.joda.time.format.ISODateTimeFormat.dateTime

class ResolvedTokenSpec extends BasePlaySpec {

  private[this] val requestId = UUID.randomUUID.toString

  "map contains user_id" in {
    val d = ResolvedToken(
      requestId = requestId,
      userId = Some("5"),
      organizationId = None,
      channelId = None,
      partnerId = None,
      role = None,
      environment = None
    )
    d.toMap must equal(Map("request_id" -> requestId, "user_id" -> "5", "created_at" -> dateTime.print(d.createdAt)))
  }

  "map contains organization" in {
    val d2 = ResolvedToken(
      requestId = requestId,
      userId = Some("5"),
      organizationId = Some("flow"),
      channelId = None,
      partnerId = None,
      role = None,
      environment = None
    )
    d2.toMap must equal(Map("request_id" -> requestId, "user_id" -> "5", "created_at" -> dateTime.print(d2.createdAt), "organization" -> "flow"))
  }

  "map contains channel" in {
    val d2 = ResolvedToken(
      requestId = requestId,
      userId = Some("5"),
      organizationId = None,
      channelId = Some("shopify"),
      partnerId = None,
      role = None,
      environment = None
    )
    d2.toMap must equal(Map("request_id" -> requestId, "user_id" -> "5", "created_at" -> dateTime.print(d2.createdAt), "channel" -> "shopify"))
  }

  "map contains partner" in {
    val d3 = ResolvedToken(
      requestId = requestId,
      userId = Some("5"),
      organizationId = None,
      channelId = None,
      partnerId = Some("flow"),
      role = None,
      environment = None
    )
    d3.toMap must equal(Map("request_id" -> requestId, "user_id" -> "5", "created_at" -> dateTime.print(d3.createdAt), "partner" -> "flow"))
  }

  "map contains role" in {
    val d4 = ResolvedToken(
      requestId = requestId,
      userId = Some("5"),
      organizationId = Some("flow"),
      channelId = None,
      partnerId = None,
      role = Some(Role.Member),
      environment = None
    )

    d4.toMap must equal(
      Map(
        "request_id" -> requestId,
        "user_id" -> "5",
        "created_at" -> dateTime.print(d4.createdAt),
        "organization" -> "flow",
        "role" -> "member"
      )
    )
  }

  "map contains environment" in {
    val d5 = ResolvedToken(
      requestId = requestId,
      userId = Some("5"),
      organizationId = Some("flow"),
      channelId = None,
      partnerId = None,
      role = Some(Role.Member),
      environment = Some(Environment.Production)
    )

    d5.toMap must equal(
      Map(
        "request_id" -> requestId,
        "user_id" -> "5",
        "created_at" -> dateTime.print(d5.createdAt),
        "organization" -> "flow",
        "role" -> "member",
        "environment" -> "production"
      )
    )
  }

}
