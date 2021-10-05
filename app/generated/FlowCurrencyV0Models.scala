/**
 * Generated by API Builder - https://www.apibuilder.io
 * Service version: 0.10.59
 * apibuilder 0.15.33 app.apibuilder.io/flow/currency/latest/play_2_x_json
 */
package io.flow.currency.v0.models {

  /**
   * Represents an organization-specific currency conversion rate at a point in time.
   *
   * @param base The base currency's ISO 4217 3-character code as defined in
   *        https://api.flow.io/reference/currencies
   * @param target The target currency's ISO 4217 3-character code as defined in
   *        https://api.flow.io/reference/currencies
   * @param effectiveAt The time at which this rate went into effect. This will always be a timestamp <=
   *        now().
   * @param value The actual conversion rate from the base currency to target currency including
   *        any applicable margins.
   */
  final case class Rate(
    id: String,
    base: String,
    target: String,
    effectiveAt: _root_.org.joda.time.DateTime,
    value: BigDecimal
  )

  /**
   * Represents the parts of an organization rate that can be updated.
   *
   * @param base The base currency's ISO 4217 3-character code as defined in
   *        https://api.flow.io/reference/currencies
   * @param target The target currency's ISO 4217 3-character code as defined in
   *        https://api.flow.io/reference/currencies
   * @param effectiveAt The time at which this rate is effective.
   */
  final case class RateForm(
    base: String,
    target: String,
    effectiveAt: _root_.org.joda.time.DateTime
  )

  final case class RateVersion(
    id: String,
    timestamp: _root_.org.joda.time.DateTime,
    `type`: io.flow.common.v0.models.ChangeType,
    rate: io.flow.currency.v0.models.Rate
  )

}

package io.flow.currency.v0.models {

  package object json {
    import play.api.libs.json.__
    import play.api.libs.json.JsString
    import play.api.libs.json.Writes
    import play.api.libs.functional.syntax._
    import io.flow.common.v0.models.json._
    import io.flow.currency.v0.models.json._
    import io.flow.error.v0.models.json._
    import io.flow.permission.v0.models.json._

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

    implicit def jsonReadsCurrencyRate: play.api.libs.json.Reads[Rate] = {
      for {
        id <- (__ \ "id").read[String]
        base <- (__ \ "base").read[String]
        target <- (__ \ "target").read[String]
        effectiveAt <- (__ \ "effective_at").read[_root_.org.joda.time.DateTime]
        value <- (__ \ "value").read[BigDecimal]
      } yield Rate(id, base, target, effectiveAt, value)
    }

    def jsObjectRate(obj: io.flow.currency.v0.models.Rate): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "id" -> play.api.libs.json.JsString(obj.id),
        "base" -> play.api.libs.json.JsString(obj.base),
        "target" -> play.api.libs.json.JsString(obj.target),
        "effective_at" -> play.api.libs.json.JsString(_root_.org.joda.time.format.ISODateTimeFormat.dateTime.print(obj.effectiveAt)),
        "value" -> play.api.libs.json.JsNumber(obj.value)
      )
    }

    implicit def jsonWritesCurrencyRate: play.api.libs.json.Writes[Rate] = {
      new play.api.libs.json.Writes[io.flow.currency.v0.models.Rate] {
        def writes(obj: io.flow.currency.v0.models.Rate) = {
          jsObjectRate(obj)
        }
      }
    }

    implicit def jsonReadsCurrencyRateForm: play.api.libs.json.Reads[RateForm] = {
      for {
        base <- (__ \ "base").read[String]
        target <- (__ \ "target").read[String]
        effectiveAt <- (__ \ "effective_at").read[_root_.org.joda.time.DateTime]
      } yield RateForm(base, target, effectiveAt)
    }

    def jsObjectRateForm(obj: io.flow.currency.v0.models.RateForm): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "base" -> play.api.libs.json.JsString(obj.base),
        "target" -> play.api.libs.json.JsString(obj.target),
        "effective_at" -> play.api.libs.json.JsString(_root_.org.joda.time.format.ISODateTimeFormat.dateTime.print(obj.effectiveAt))
      )
    }

    implicit def jsonWritesCurrencyRateForm: play.api.libs.json.Writes[RateForm] = {
      new play.api.libs.json.Writes[io.flow.currency.v0.models.RateForm] {
        def writes(obj: io.flow.currency.v0.models.RateForm) = {
          jsObjectRateForm(obj)
        }
      }
    }

    implicit def jsonReadsCurrencyRateVersion: play.api.libs.json.Reads[RateVersion] = {
      for {
        id <- (__ \ "id").read[String]
        timestamp <- (__ \ "timestamp").read[_root_.org.joda.time.DateTime]
        `type` <- (__ \ "type").read[io.flow.common.v0.models.ChangeType]
        rate <- (__ \ "rate").read[io.flow.currency.v0.models.Rate]
      } yield RateVersion(id, timestamp, `type`, rate)
    }

    def jsObjectRateVersion(obj: io.flow.currency.v0.models.RateVersion): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "id" -> play.api.libs.json.JsString(obj.id),
        "timestamp" -> play.api.libs.json.JsString(_root_.org.joda.time.format.ISODateTimeFormat.dateTime.print(obj.timestamp)),
        "type" -> play.api.libs.json.JsString(obj.`type`.toString),
        "rate" -> jsObjectRate(obj.rate)
      )
    }

    implicit def jsonWritesCurrencyRateVersion: play.api.libs.json.Writes[RateVersion] = {
      new play.api.libs.json.Writes[io.flow.currency.v0.models.RateVersion] {
        def writes(obj: io.flow.currency.v0.models.RateVersion) = {
          jsObjectRateVersion(obj)
        }
      }
    }
  }
}

package io.flow.currency.v0 {

  object Bindables {

    import play.api.mvc.{PathBindable, QueryStringBindable}

    // import models directly for backwards compatibility with prior versions of the generator
    import Core._

    object Core {
      implicit def pathBindableDateTimeIso8601(implicit stringBinder: QueryStringBindable[String]): PathBindable[_root_.org.joda.time.DateTime] = ApibuilderPathBindable(ApibuilderTypes.dateTimeIso8601)
      implicit def queryStringBindableDateTimeIso8601(implicit stringBinder: QueryStringBindable[String]): QueryStringBindable[_root_.org.joda.time.DateTime] = ApibuilderQueryStringBindable(ApibuilderTypes.dateTimeIso8601)

      implicit def pathBindableDateIso8601(implicit stringBinder: QueryStringBindable[String]): PathBindable[_root_.org.joda.time.LocalDate] = ApibuilderPathBindable(ApibuilderTypes.dateIso8601)
      implicit def queryStringBindableDateIso8601(implicit stringBinder: QueryStringBindable[String]): QueryStringBindable[_root_.org.joda.time.LocalDate] = ApibuilderQueryStringBindable(ApibuilderTypes.dateIso8601)
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
