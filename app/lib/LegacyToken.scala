package lib

import io.flow.common.v0.models.{Environment, Role}
import io.flow.proxy.auth.v0.models.AuthData
import org.joda.time.format.ISODateTimeFormat.dateTime

// This data is being replaced by AuthData model.
// plan is to remove after all services are deployed and able to read
// the new AuthData class
case class LegacyToken(authData: AuthData, role: Option[Role], environment: Option[Environment] = None) {

  def toMap: Map[String, String] = {
    Map(
      "request_id" -> Some(authData.requestId),
      "user_id" -> authData.userId,
      "created_at" -> Some(dateTime.print(authData.createdAt)),
      "session" -> authData.sessionId,
      "organization" -> authData.organizationId,
      "partner" -> authData.partnerId,
      "role" -> role.map(_.toString),
      "environment" -> environment.map(_.toString),
      "customer" -> authData.customerNumber
    ).flatMap { case (key, value) => value.map { v => key -> v } }
  }

}
