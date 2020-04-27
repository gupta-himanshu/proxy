package lib

import java.nio.charset.StandardCharsets

import io.flow.log.RollbarLogger
import io.flow.auth.v2.{JwtAuthData, SimpleJwtSaltProvider}
import javax.inject.Inject
import play.api.libs.json.JsObject

sealed trait Authorization

object Authorization {

  object Prefixes {
    val Basic: String = "basic"
    val Bearer: String = "bearer"
    val Session: String = "session"

    val all: Seq[String] = Seq(Basic, Bearer, Session)

  }

  /**
    * Indicates no auth credentials were present
    */
  case object NoCredentials extends Authorization

  /**
    * Indicates authorization header was present but was not a
    * recognized type (e.g. Basic, Bearer, Session)
    */
  case object Unrecognized extends Authorization

  /**
    * Indicates API token was presented as basic auth; but API token
    * was not valid.
    */
  case object InvalidApiToken extends Authorization

  /**
    * Indicates JWT Bearer data was presented as authorization
    * header; but data was not valid.
    */
  case class InvalidJwt(missing: Seq[String]) extends Authorization

  /**
    * Indicates JWT Bearer data was presented as authorization
    * header; but data was not valid.
    */
  case object InvalidBearer extends Authorization

  /**
    * Indicates valid API Token for a given user.
    */
  case class Token(token: String) extends Authorization

  /**
    * Indicates valid user ID was parsed from JWT token
    */
  case class User(id: String) extends Authorization

  /**
    * Indicates session id for a given request
    */
  case class Session(id: String) extends Authorization

  /**
    * Indicates customer number and session id for a given request
    */
  case class Customer(customer: String, session: String) extends Authorization

}

/**
  * Responsible for parsing the authorization header, returning a
  * specific Authorization that can be used to clearly identify
  * whether or not authorization succeeded, and if not why.
  */
class AuthorizationParser @Inject() (
  flowJwtAuthDataProvider: FlowJwtAuthDataProvider,
) {
  private[this] val flowJwtAuthData: JwtAuthData = flowJwtAuthDataProvider.instance

  /**
    * Parses the value from the authorization header, handling case
    * where no authorization was present
    */
  def parse(value: Option[String]): Authorization = {
    value match {
      case None => Authorization.NoCredentials
      case Some(value) => parse(value)
    }
  }

  /**
    * Parses the actual authorization header value. Acceptable types are:
    * - Basic - the API Token for the user
    * - Bearer (multiple JWT claim sets supported)
    *   - the JWT Token for the user that contains an id field representing the user id in the database
    *   - the JWT Token for the customer that contains a number field representing the customer number in the database
    *     and sessionId field representing the session id in the database
    */
  def parse(headerValue: String): Authorization = {
    headerValue.split(" ").toList match {
      case prefix :: value :: Nil => {
        prefix.toLowerCase.trim match {
          case Authorization.Prefixes.Basic => {
            new String(java.util.Base64.getDecoder.decode(value.getBytes(StandardCharsets.US_ASCII))).split(":").toList match {
              case Nil => Authorization.InvalidApiToken
              case token :: _ => Authorization.Token(token)
            }
          }

          case Authorization.Prefixes.Bearer => {
            flowJwtAuthData.decodeJson(value) match {
              case Left(_) => Authorization.InvalidBearer
              case Right(claims) => parseJwtToken(claims)
            }
          }

          case Authorization.Prefixes.Session => {
            Authorization.Session(value)
          }

          case _ => Authorization.Unrecognized
        }
      }

      case _ => Authorization.Unrecognized

    }
  }

  private[this] def parseJwtToken(claims: JsObject): Authorization =
    (claims \ "id").asOpt[String] match {
      case Some(userId) => Authorization.User(userId)
      case None => parseCustomerJwtToken(claims)
    }

  private[this] def parseCustomerJwtToken(claims: JsObject): Authorization =
    ((claims \ "customer").asOpt[String], (claims \ "session").asOpt[String]) match {
      case (Some(cn), Some(sid)) => Authorization.Customer(customer = cn, session = sid)
      case (None, Some(sid)) => Authorization.Session(id = sid)
      case (Some(_), _) => Authorization.InvalidJwt(Seq("customer", "session"))
      case _ => Authorization.InvalidBearer
    }

}
