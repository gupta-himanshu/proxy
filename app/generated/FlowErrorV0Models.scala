/**
 * Generated by API Builder - https://www.apibuilder.io
 * Service version: 0.10.59
 * apibuilder 0.15.33 app.apibuilder.io/flow/error/latest/play_2_x_json
 */
package io.flow.error.v0.models {

  /**
   * An error of some type has occurred. The most common error will be validation on
   * input. See messages for details.
   *
   * @param code Generic errors will always have a code set to 'generic_error'
   */
  final case class GenericError(
    code: io.flow.error.v0.models.GenericErrorCode = io.flow.error.v0.models.GenericErrorCode.GenericError,
    messages: Seq[String]
  )

  sealed trait GenericErrorCode extends _root_.scala.Product with _root_.scala.Serializable

  object GenericErrorCode {

    /**
     * Generic errors are the default type. The accompanying message will provide
     * details on the failure.
     */
    case object GenericError extends GenericErrorCode { override def toString = "generic_error" }
    /**
     * A client error has occurred. This represents a misconfiguration of the client
     */
    case object ClientError extends GenericErrorCode { override def toString = "client_error" }
    /**
     * A server error has occurred. The Flow tech team is automatically notified of all
     * server errors
     */
    case object ServerError extends GenericErrorCode { override def toString = "server_error" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    final case class UNDEFINED(override val toString: String) extends GenericErrorCode

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all: scala.List[GenericErrorCode] = scala.List(GenericError, ClientError, ServerError)

    private[this]
    val byName: Map[String, GenericErrorCode] = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): GenericErrorCode = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[GenericErrorCode] = byName.get(value.toLowerCase)

  }

}

package io.flow.error.v0.models {

  package object json {
    import play.api.libs.json.__
    import play.api.libs.json.JsString
    import play.api.libs.json.Writes
    import play.api.libs.functional.syntax._
    import io.flow.error.v0.models.json._

    private[v0] implicit val jsonReadsUUID = __.read[String].map { str =>
      _root_.java.util.UUID.fromString(str)
    }

    private[v0] implicit val jsonWritesUUID = new Writes[_root_.java.util.UUID] {
      def writes(x: _root_.java.util.UUID) = JsString(x.toString)
    }

    private[v0] implicit val jsonReadsJodaDateTime = __.read[String].map { str =>
      _root_.org.joda.time.format.ISODateTimeFormat.dateTimeParser.parseDateTime(str)
    }

    private[v0] implicit val jsonWritesJodaDateTime = new Writes[_root_.org.joda.time.DateTime] {
      def writes(x: _root_.org.joda.time.DateTime) = {
        JsString(_root_.org.joda.time.format.ISODateTimeFormat.dateTime.print(x))
      }
    }

    private[v0] implicit val jsonReadsJodaLocalDate = __.read[String].map { str =>
      _root_.org.joda.time.format.ISODateTimeFormat.dateTimeParser.parseLocalDate(str)
    }

    private[v0] implicit val jsonWritesJodaLocalDate = new Writes[_root_.org.joda.time.LocalDate] {
      def writes(x: _root_.org.joda.time.LocalDate) = {
        JsString(_root_.org.joda.time.format.ISODateTimeFormat.date.print(x))
      }
    }

    implicit val jsonReadsErrorGenericErrorCode = new play.api.libs.json.Reads[io.flow.error.v0.models.GenericErrorCode] {
      def reads(js: play.api.libs.json.JsValue): play.api.libs.json.JsResult[io.flow.error.v0.models.GenericErrorCode] = {
        js match {
          case v: play.api.libs.json.JsString => play.api.libs.json.JsSuccess(io.flow.error.v0.models.GenericErrorCode(v.value))
          case _ => {
            (js \ "value").validate[String] match {
              case play.api.libs.json.JsSuccess(v, _) => play.api.libs.json.JsSuccess(io.flow.error.v0.models.GenericErrorCode(v))
              case err: play.api.libs.json.JsError =>
                (js \ "generic_error_code").validate[String] match {
                  case play.api.libs.json.JsSuccess(v, _) => play.api.libs.json.JsSuccess(io.flow.error.v0.models.GenericErrorCode(v))
                  case err: play.api.libs.json.JsError => err
                }
            }
          }
        }
      }
    }

    def jsonWritesErrorGenericErrorCode(obj: io.flow.error.v0.models.GenericErrorCode) = {
      play.api.libs.json.JsString(obj.toString)
    }

    def jsObjectGenericErrorCode(obj: io.flow.error.v0.models.GenericErrorCode) = {
      play.api.libs.json.Json.obj("value" -> play.api.libs.json.JsString(obj.toString))
    }

    implicit def jsonWritesErrorGenericErrorCode: play.api.libs.json.Writes[GenericErrorCode] = {
      new play.api.libs.json.Writes[io.flow.error.v0.models.GenericErrorCode] {
        def writes(obj: io.flow.error.v0.models.GenericErrorCode) = {
          jsonWritesErrorGenericErrorCode(obj)
        }
      }
    }

    implicit def jsonReadsErrorGenericError: play.api.libs.json.Reads[GenericError] = {
      for {
        code <- (__ \ "code").read[io.flow.error.v0.models.GenericErrorCode]
        messages <- (__ \ "messages").read[Seq[String]]
      } yield GenericError(code, messages)
    }

    def jsObjectGenericError(obj: io.flow.error.v0.models.GenericError): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "code" -> play.api.libs.json.JsString(obj.code.toString),
        "messages" -> play.api.libs.json.Json.toJson(obj.messages)
      )
    }

    implicit def jsonWritesErrorGenericError: play.api.libs.json.Writes[GenericError] = {
      new play.api.libs.json.Writes[io.flow.error.v0.models.GenericError] {
        def writes(obj: io.flow.error.v0.models.GenericError) = {
          jsObjectGenericError(obj)
        }
      }
    }
  }
}

package io.flow.error.v0 {

  object Bindables {

    import play.api.mvc.{PathBindable, QueryStringBindable}

    // import models directly for backwards compatibility with prior versions of the generator
    import Core._
    import Models._

    object Core {
      implicit def pathBindableDateTimeIso8601(implicit stringBinder: QueryStringBindable[String]): PathBindable[_root_.org.joda.time.DateTime] = ApibuilderPathBindable(ApibuilderTypes.dateTimeIso8601)
      implicit def queryStringBindableDateTimeIso8601(implicit stringBinder: QueryStringBindable[String]): QueryStringBindable[_root_.org.joda.time.DateTime] = ApibuilderQueryStringBindable(ApibuilderTypes.dateTimeIso8601)

      implicit def pathBindableDateIso8601(implicit stringBinder: QueryStringBindable[String]): PathBindable[_root_.org.joda.time.LocalDate] = ApibuilderPathBindable(ApibuilderTypes.dateIso8601)
      implicit def queryStringBindableDateIso8601(implicit stringBinder: QueryStringBindable[String]): QueryStringBindable[_root_.org.joda.time.LocalDate] = ApibuilderQueryStringBindable(ApibuilderTypes.dateIso8601)
    }

    object Models {
      import io.flow.error.v0.models._

      val genericErrorCodeConverter: ApibuilderTypeConverter[io.flow.error.v0.models.GenericErrorCode] = new ApibuilderTypeConverter[io.flow.error.v0.models.GenericErrorCode] {
        override def convert(value: String): io.flow.error.v0.models.GenericErrorCode = io.flow.error.v0.models.GenericErrorCode(value)
        override def convert(value: io.flow.error.v0.models.GenericErrorCode): String = value.toString
        override def example: io.flow.error.v0.models.GenericErrorCode = io.flow.error.v0.models.GenericErrorCode.GenericError
        override def validValues: Seq[io.flow.error.v0.models.GenericErrorCode] = io.flow.error.v0.models.GenericErrorCode.all
      }
      implicit def pathBindableGenericErrorCode(implicit stringBinder: QueryStringBindable[String]): PathBindable[io.flow.error.v0.models.GenericErrorCode] = ApibuilderPathBindable(genericErrorCodeConverter)
      implicit def queryStringBindableGenericErrorCode(implicit stringBinder: QueryStringBindable[String]): QueryStringBindable[io.flow.error.v0.models.GenericErrorCode] = ApibuilderQueryStringBindable(genericErrorCodeConverter)
    }

    trait ApibuilderTypeConverter[T] {

      def convert(value: String): T

      def convert(value: T): String

      def example: T

      def validValues: Seq[T] = Nil

      def errorMessage(key: String, value: String, ex: java.lang.Exception): String = {
        val base = s"Invalid value '$value' for parameter '$key'. "
        validValues.toList match {
          case Nil => base + "Ex: " + convert(example)
          case values => base + ". Valid values are: " + values.mkString("'", "', '", "'")
        }
      }
    }

    object ApibuilderTypes {
      val dateTimeIso8601: ApibuilderTypeConverter[_root_.org.joda.time.DateTime] = new ApibuilderTypeConverter[_root_.org.joda.time.DateTime] {
        override def convert(value: String): _root_.org.joda.time.DateTime = _root_.org.joda.time.format.ISODateTimeFormat.dateTimeParser.parseDateTime(value)
        override def convert(value: _root_.org.joda.time.DateTime): String = _root_.org.joda.time.format.ISODateTimeFormat.dateTime.print(value)
        override def example: _root_.org.joda.time.DateTime = _root_.org.joda.time.DateTime.now
      }

      val dateIso8601: ApibuilderTypeConverter[_root_.org.joda.time.LocalDate] = new ApibuilderTypeConverter[_root_.org.joda.time.LocalDate] {
        override def convert(value: String): _root_.org.joda.time.LocalDate = _root_.org.joda.time.format.ISODateTimeFormat.dateTimeParser.parseLocalDate(value)
        override def convert(value: _root_.org.joda.time.LocalDate): String = _root_.org.joda.time.format.ISODateTimeFormat.date.print(value)
        override def example: _root_.org.joda.time.LocalDate = _root_.org.joda.time.LocalDate.now
      }
    }

    final case class ApibuilderQueryStringBindable[T](
      converters: ApibuilderTypeConverter[T]
    ) extends QueryStringBindable[T] {

      override def bind(key: String, params: Map[String, Seq[String]]): _root_.scala.Option[_root_.scala.Either[String, T]] = {
        params.getOrElse(key, Nil).headOption.map { v =>
          try {
            Right(
              converters.convert(v)
            )
          } catch {
            case ex: java.lang.Exception => Left(
              converters.errorMessage(key, v, ex)
            )
          }
        }
      }

      override def unbind(key: String, value: T): String = {
        s"$key=${converters.convert(value)}"
      }
    }

    final case class ApibuilderPathBindable[T](
      converters: ApibuilderTypeConverter[T]
    ) extends PathBindable[T] {

      override def bind(key: String, value: String): _root_.scala.Either[String, T] = {
        try {
          Right(
            converters.convert(value)
          )
        } catch {
          case ex: java.lang.Exception => Left(
            converters.errorMessage(key, value, ex)
          )
        }
      }

      override def unbind(key: String, value: T): String = {
        converters.convert(value)
      }
    }

  }

}
