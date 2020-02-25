/**
 * Generated by API Builder - https://www.apibuilder.io
 * Service version: 0.7.58
 * apibuilder 0.14.93 app.apibuilder.io/flow/reference/latest/play_2_x_json
 */
package io.flow.reference.v0.models {

  /**
   * Partner that actually takes a shipment between places (ex: FedEx, DHL, SF
   * Express)
   */
  final case class Carrier(
    id: String,
    name: String,
    trackingUrl: String
  )

  /**
   * Specific service rendered by the carrier (fedex ground saturday, ups overnight
   * weekend, etc)
   */
  final case class CarrierService(
    id: String,
    carrier: io.flow.reference.v0.models.Carrier,
    name: String
  )

  /**
   * ISO 3166 country codes. Note Flow APIs will accept either the 2 or 3 character
   * country code, but internally we normalize data and store as the 3 character,
   * upper case ISO code. See https://api.flow.io/reference/countries
   *
   * @param iso31662 ISO 3166 2-character country code. See https://api.flow.io/reference/countries
   * @param iso31663 ISO 3166 3-character country code. See https://api.flow.io/reference/countries
   * @param languages A list of the languages spoken in this country
   * @param defaultCurrency ISO 4217 3-character currency code. See https://api.flow.io/reference/currencies
   * @param defaultLanguage ISO 639 2-character language code. See https://api.flow.io/reference/languages
   * @param timezones A list of canonical timezone IDs for the country. See
   *        http://joda-time.sourceforge.net/timezones.html
   * @param defaultDeliveredDuty Default delivered duty value. See https://en.wikipedia.org/wiki/Incoterms for
   *        more information
   */
  final case class Country(
    name: String,
    iso31662: String,
    iso31663: String,
    languages: Seq[String],
    measurementSystem: String,
    defaultCurrency: _root_.scala.Option[String] = None,
    defaultLanguage: _root_.scala.Option[String] = None,
    timezones: Seq[String],
    defaultDeliveredDuty: _root_.scala.Option[String] = None
  )

  /**
   * ISO 4217 3-character currency code. See https://api.flow.io/reference/currencies
   *
   * @param numberDecimals The number of decimal places used by the given currency. For example, USD has 2
   *        decimals while JPY has 0.
   * @param defaultLocale The locale id of the default locale to use when rendering this currency
   */
  final case class Currency(
    name: String,
    iso42173: String,
    numberDecimals: Int,
    symbols: _root_.scala.Option[io.flow.reference.v0.models.CurrencySymbols] = None,
    defaultLocale: _root_.scala.Option[String] = None
  )

  /**
   * Defines one or more symbols representing this currency
   */
  final case class CurrencySymbols(
    primary: String,
    narrow: _root_.scala.Option[String] = None
  )

  /**
   * ISO 639 2-character language code. See https://api.flow.io/reference/languages
   */
  final case class Language(
    name: String,
    iso6392: String
  )

  /**
   * Locales defines standard conventions for presentation of content. See
   * https://api.flow.io/reference/locales
   *
   * @param country ISO 3166 3 country code
   * @param language ISO 639 2 language code
   */
  final case class Locale(
    id: String,
    name: String,
    country: String,
    language: String,
    numbers: io.flow.reference.v0.models.LocaleNumbers
  )

  /**
   * Number formats defined for a given locale
   *
   * @param decimal Decimal separator
   * @param group Group separator (e.g. 1,000 have a group separator of ',')
   */
  final case class LocaleNumbers(
    decimal: String,
    group: String
  )

  /**
   * Localized translation of a given province/region/country
   *
   * @param name Text translated to the appropriate locale
   */
  final case class LocalizedTranslation(
    locale: io.flow.reference.v0.models.Locale,
    name: String
  )

  /**
   * Represents a single payment method - e.g VISA or Paypal - and any associated
   * metadata required for processing
   *
   * @param regions List of region ids in which this payment method is available
   */
  final case class PaymentMethod(
    id: String,
    `type`: io.flow.reference.v0.models.PaymentMethodType,
    name: String,
    images: io.flow.reference.v0.models.PaymentMethodImages,
    regions: Seq[String]
  )

  final case class PaymentMethodImage(
    url: String,
    width: Int,
    height: Int
  )

  final case class PaymentMethodImages(
    small: io.flow.reference.v0.models.PaymentMethodImage,
    medium: io.flow.reference.v0.models.PaymentMethodImage,
    large: io.flow.reference.v0.models.PaymentMethodImage
  )

  /**
   * A subdivision/province/state within a country. These conform to the ISO 3166-2
   * standard for country subdivisions. See https://api.flow.io/reference/provinces
   *
   * @param country ISO 3166 3 code of the country for this subdivision
   */
  final case class Province(
    id: String,
    iso31662: String,
    name: String,
    country: String,
    provinceType: io.flow.reference.v0.models.ProvinceType,
    translations: _root_.scala.Option[Seq[io.flow.reference.v0.models.LocalizedTranslation]] = None
  )

  /**
   * A region represents a geographic area of the world. Regions can be countries,
   * continents or other political areas (like the Eurozone). See
   * https://api.flow.io/reference/regions
   *
   * @param countries A list of the countries as ISO 3166 3 codes in this region
   * @param currencies A list of the currencies as ISO 4217 3 codes in this region
   * @param languages A list of the languages as ISO 639 2 codes spoken in this region
   * @param measurementSystems A list of the measurement systems in use in this region (metric or imperial)
   * @param timezones A list of canonical timezone IDs for the region. See
   *        http://joda-time.sourceforge.net/timezones.html
   */
  final case class Region(
    id: String,
    name: String,
    countries: Seq[String],
    currencies: Seq[String],
    languages: Seq[String],
    measurementSystems: Seq[String],
    timezones: Seq[String]
  )

  /**
   * Time zone data is provided by the public IANA time zone database. See
   * http://www.iana.org/time-zones
   *
   * @param offset Minutes offset from GMT
   */
  final case class Timezone(
    name: String,
    description: String,
    offset: Int
  )

  /**
   * The payment method type defines at a high level different user experiences that
   * are required to accept payment of this type. By enabling a payment method type,
   * you are specifying that you have completed the integration and all payment
   * methods of this type become available for offer to your users.
   */
  sealed trait PaymentMethodType extends _root_.scala.Product with _root_.scala.Serializable

  object PaymentMethodType {

    /**
     * Represents all form of card payment (e.g. credit, debit, etc.)
     */
    case object Card extends PaymentMethodType { override def toString = "card" }
    /**
     * Represents the most common form of alternative payment methods which require
     * some degree of integration online (e.g. a redirect) to complete payment.
     */
    case object Online extends PaymentMethodType { override def toString = "online" }
    /**
     * Offline payment method types represent payments like Cash On Delivery which
     * require offline collection
     */
    case object Offline extends PaymentMethodType { override def toString = "offline" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    final case class UNDEFINED(override val toString: String) extends PaymentMethodType

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all: scala.List[PaymentMethodType] = scala.List(Card, Online, Offline)

    private[this]
    val byName: Map[String, PaymentMethodType] = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): PaymentMethodType = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[PaymentMethodType] = byName.get(value.toLowerCase)

  }

  /**
   * Local terminology for province
   */
  sealed trait ProvinceType extends _root_.scala.Product with _root_.scala.Serializable

  object ProvinceType {

    case object City extends ProvinceType { override def toString = "city" }
    case object Dependency extends ProvinceType { override def toString = "dependency" }
    case object District extends ProvinceType { override def toString = "district" }
    case object Emirate extends ProvinceType { override def toString = "emirate" }
    case object Entity extends ProvinceType { override def toString = "entity" }
    case object Municipality extends ProvinceType { override def toString = "municipality" }
    case object OutlyingArea extends ProvinceType { override def toString = "outlying_area" }
    case object Parish extends ProvinceType { override def toString = "parish" }
    case object Province extends ProvinceType { override def toString = "province" }
    case object State extends ProvinceType { override def toString = "state" }
    case object Territory extends ProvinceType { override def toString = "territory" }
    case object Other extends ProvinceType { override def toString = "other" }

    /**
     * UNDEFINED captures values that are sent either in error or
     * that were added by the server after this library was
     * generated. We want to make it easy and obvious for users of
     * this library to handle this case gracefully.
     *
     * We use all CAPS for the variable name to avoid collisions
     * with the camel cased values above.
     */
    final case class UNDEFINED(override val toString: String) extends ProvinceType

    /**
     * all returns a list of all the valid, known values. We use
     * lower case to avoid collisions with the camel cased values
     * above.
     */
    val all: scala.List[ProvinceType] = scala.List(City, Dependency, District, Emirate, Entity, Municipality, OutlyingArea, Parish, Province, State, Territory, Other)

    private[this]
    val byName: Map[String, ProvinceType] = all.map(x => x.toString.toLowerCase -> x).toMap

    def apply(value: String): ProvinceType = fromString(value).getOrElse(UNDEFINED(value))

    def fromString(value: String): _root_.scala.Option[ProvinceType] = byName.get(value.toLowerCase)

  }

}

package io.flow.reference.v0.models {

  package object json {
    import play.api.libs.json.__
    import play.api.libs.json.JsString
    import play.api.libs.json.Writes
    import play.api.libs.functional.syntax._
    import io.flow.reference.v0.models.json._

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

    implicit val jsonReadsReferencePaymentMethodType = new play.api.libs.json.Reads[io.flow.reference.v0.models.PaymentMethodType] {
      def reads(js: play.api.libs.json.JsValue): play.api.libs.json.JsResult[io.flow.reference.v0.models.PaymentMethodType] = {
        js match {
          case v: play.api.libs.json.JsString => play.api.libs.json.JsSuccess(io.flow.reference.v0.models.PaymentMethodType(v.value))
          case _ => {
            (js \ "value").validate[String] match {
              case play.api.libs.json.JsSuccess(v, _) => play.api.libs.json.JsSuccess(io.flow.reference.v0.models.PaymentMethodType(v))
              case err: play.api.libs.json.JsError =>
                (js \ "payment_method_type").validate[String] match {
                  case play.api.libs.json.JsSuccess(v, _) => play.api.libs.json.JsSuccess(io.flow.reference.v0.models.PaymentMethodType(v))
                  case err: play.api.libs.json.JsError => err
                }
            }
          }
        }
      }
    }

    def jsonWritesReferencePaymentMethodType(obj: io.flow.reference.v0.models.PaymentMethodType) = {
      play.api.libs.json.JsString(obj.toString)
    }

    def jsObjectPaymentMethodType(obj: io.flow.reference.v0.models.PaymentMethodType) = {
      play.api.libs.json.Json.obj("value" -> play.api.libs.json.JsString(obj.toString))
    }

    implicit def jsonWritesReferencePaymentMethodType: play.api.libs.json.Writes[PaymentMethodType] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.PaymentMethodType] {
        def writes(obj: io.flow.reference.v0.models.PaymentMethodType) = {
          jsonWritesReferencePaymentMethodType(obj)
        }
      }
    }

    implicit val jsonReadsReferenceProvinceType = new play.api.libs.json.Reads[io.flow.reference.v0.models.ProvinceType] {
      def reads(js: play.api.libs.json.JsValue): play.api.libs.json.JsResult[io.flow.reference.v0.models.ProvinceType] = {
        js match {
          case v: play.api.libs.json.JsString => play.api.libs.json.JsSuccess(io.flow.reference.v0.models.ProvinceType(v.value))
          case _ => {
            (js \ "value").validate[String] match {
              case play.api.libs.json.JsSuccess(v, _) => play.api.libs.json.JsSuccess(io.flow.reference.v0.models.ProvinceType(v))
              case err: play.api.libs.json.JsError =>
                (js \ "province_type").validate[String] match {
                  case play.api.libs.json.JsSuccess(v, _) => play.api.libs.json.JsSuccess(io.flow.reference.v0.models.ProvinceType(v))
                  case err: play.api.libs.json.JsError => err
                }
            }
          }
        }
      }
    }

    def jsonWritesReferenceProvinceType(obj: io.flow.reference.v0.models.ProvinceType) = {
      play.api.libs.json.JsString(obj.toString)
    }

    def jsObjectProvinceType(obj: io.flow.reference.v0.models.ProvinceType) = {
      play.api.libs.json.Json.obj("value" -> play.api.libs.json.JsString(obj.toString))
    }

    implicit def jsonWritesReferenceProvinceType: play.api.libs.json.Writes[ProvinceType] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.ProvinceType] {
        def writes(obj: io.flow.reference.v0.models.ProvinceType) = {
          jsonWritesReferenceProvinceType(obj)
        }
      }
    }

    implicit def jsonReadsReferenceCarrier: play.api.libs.json.Reads[Carrier] = {
      for {
        id <- (__ \ "id").read[String]
        name <- (__ \ "name").read[String]
        trackingUrl <- (__ \ "tracking_url").read[String]
      } yield Carrier(id, name, trackingUrl)
    }

    def jsObjectCarrier(obj: io.flow.reference.v0.models.Carrier): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "id" -> play.api.libs.json.JsString(obj.id),
        "name" -> play.api.libs.json.JsString(obj.name),
        "tracking_url" -> play.api.libs.json.JsString(obj.trackingUrl)
      )
    }

    implicit def jsonWritesReferenceCarrier: play.api.libs.json.Writes[Carrier] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.Carrier] {
        def writes(obj: io.flow.reference.v0.models.Carrier) = {
          jsObjectCarrier(obj)
        }
      }
    }

    implicit def jsonReadsReferenceCarrierService: play.api.libs.json.Reads[CarrierService] = {
      for {
        id <- (__ \ "id").read[String]
        carrier <- (__ \ "carrier").read[io.flow.reference.v0.models.Carrier]
        name <- (__ \ "name").read[String]
      } yield CarrierService(id, carrier, name)
    }

    def jsObjectCarrierService(obj: io.flow.reference.v0.models.CarrierService): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "id" -> play.api.libs.json.JsString(obj.id),
        "carrier" -> jsObjectCarrier(obj.carrier),
        "name" -> play.api.libs.json.JsString(obj.name)
      )
    }

    implicit def jsonWritesReferenceCarrierService: play.api.libs.json.Writes[CarrierService] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.CarrierService] {
        def writes(obj: io.flow.reference.v0.models.CarrierService) = {
          jsObjectCarrierService(obj)
        }
      }
    }

    implicit def jsonReadsReferenceCountry: play.api.libs.json.Reads[Country] = {
      for {
        name <- (__ \ "name").read[String]
        iso31662 <- (__ \ "iso_3166_2").read[String]
        iso31663 <- (__ \ "iso_3166_3").read[String]
        languages <- (__ \ "languages").read[Seq[String]]
        measurementSystem <- (__ \ "measurement_system").read[String]
        defaultCurrency <- (__ \ "default_currency").readNullable[String]
        defaultLanguage <- (__ \ "default_language").readNullable[String]
        timezones <- (__ \ "timezones").read[Seq[String]]
        defaultDeliveredDuty <- (__ \ "default_delivered_duty").readNullable[String]
      } yield Country(name, iso31662, iso31663, languages, measurementSystem, defaultCurrency, defaultLanguage, timezones, defaultDeliveredDuty)
    }

    def jsObjectCountry(obj: io.flow.reference.v0.models.Country): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "name" -> play.api.libs.json.JsString(obj.name),
        "iso_3166_2" -> play.api.libs.json.JsString(obj.iso31662),
        "iso_3166_3" -> play.api.libs.json.JsString(obj.iso31663),
        "languages" -> play.api.libs.json.Json.toJson(obj.languages),
        "measurement_system" -> play.api.libs.json.JsString(obj.measurementSystem),
        "timezones" -> play.api.libs.json.Json.toJson(obj.timezones)
      ) ++ (obj.defaultCurrency match {
        case None => play.api.libs.json.Json.obj()
        case Some(x) => play.api.libs.json.Json.obj("default_currency" -> play.api.libs.json.JsString(x))
      }) ++
      (obj.defaultLanguage match {
        case None => play.api.libs.json.Json.obj()
        case Some(x) => play.api.libs.json.Json.obj("default_language" -> play.api.libs.json.JsString(x))
      }) ++
      (obj.defaultDeliveredDuty match {
        case None => play.api.libs.json.Json.obj()
        case Some(x) => play.api.libs.json.Json.obj("default_delivered_duty" -> play.api.libs.json.JsString(x))
      })
    }

    implicit def jsonWritesReferenceCountry: play.api.libs.json.Writes[Country] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.Country] {
        def writes(obj: io.flow.reference.v0.models.Country) = {
          jsObjectCountry(obj)
        }
      }
    }

    implicit def jsonReadsReferenceCurrency: play.api.libs.json.Reads[Currency] = {
      for {
        name <- (__ \ "name").read[String]
        iso42173 <- (__ \ "iso_4217_3").read[String]
        numberDecimals <- (__ \ "number_decimals").read[Int]
        symbols <- (__ \ "symbols").readNullable[io.flow.reference.v0.models.CurrencySymbols]
        defaultLocale <- (__ \ "default_locale").readNullable[String]
      } yield Currency(name, iso42173, numberDecimals, symbols, defaultLocale)
    }

    def jsObjectCurrency(obj: io.flow.reference.v0.models.Currency): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "name" -> play.api.libs.json.JsString(obj.name),
        "iso_4217_3" -> play.api.libs.json.JsString(obj.iso42173),
        "number_decimals" -> play.api.libs.json.JsNumber(obj.numberDecimals)
      ) ++ (obj.symbols match {
        case None => play.api.libs.json.Json.obj()
        case Some(x) => play.api.libs.json.Json.obj("symbols" -> jsObjectCurrencySymbols(x))
      }) ++
      (obj.defaultLocale match {
        case None => play.api.libs.json.Json.obj()
        case Some(x) => play.api.libs.json.Json.obj("default_locale" -> play.api.libs.json.JsString(x))
      })
    }

    implicit def jsonWritesReferenceCurrency: play.api.libs.json.Writes[Currency] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.Currency] {
        def writes(obj: io.flow.reference.v0.models.Currency) = {
          jsObjectCurrency(obj)
        }
      }
    }

    implicit def jsonReadsReferenceCurrencySymbols: play.api.libs.json.Reads[CurrencySymbols] = {
      for {
        primary <- (__ \ "primary").read[String]
        narrow <- (__ \ "narrow").readNullable[String]
      } yield CurrencySymbols(primary, narrow)
    }

    def jsObjectCurrencySymbols(obj: io.flow.reference.v0.models.CurrencySymbols): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "primary" -> play.api.libs.json.JsString(obj.primary)
      ) ++ (obj.narrow match {
        case None => play.api.libs.json.Json.obj()
        case Some(x) => play.api.libs.json.Json.obj("narrow" -> play.api.libs.json.JsString(x))
      })
    }

    implicit def jsonWritesReferenceCurrencySymbols: play.api.libs.json.Writes[CurrencySymbols] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.CurrencySymbols] {
        def writes(obj: io.flow.reference.v0.models.CurrencySymbols) = {
          jsObjectCurrencySymbols(obj)
        }
      }
    }

    implicit def jsonReadsReferenceLanguage: play.api.libs.json.Reads[Language] = {
      for {
        name <- (__ \ "name").read[String]
        iso6392 <- (__ \ "iso_639_2").read[String]
      } yield Language(name, iso6392)
    }

    def jsObjectLanguage(obj: io.flow.reference.v0.models.Language): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "name" -> play.api.libs.json.JsString(obj.name),
        "iso_639_2" -> play.api.libs.json.JsString(obj.iso6392)
      )
    }

    implicit def jsonWritesReferenceLanguage: play.api.libs.json.Writes[Language] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.Language] {
        def writes(obj: io.flow.reference.v0.models.Language) = {
          jsObjectLanguage(obj)
        }
      }
    }

    implicit def jsonReadsReferenceLocale: play.api.libs.json.Reads[Locale] = {
      for {
        id <- (__ \ "id").read[String]
        name <- (__ \ "name").read[String]
        country <- (__ \ "country").read[String]
        language <- (__ \ "language").read[String]
        numbers <- (__ \ "numbers").read[io.flow.reference.v0.models.LocaleNumbers]
      } yield Locale(id, name, country, language, numbers)
    }

    def jsObjectLocale(obj: io.flow.reference.v0.models.Locale): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "id" -> play.api.libs.json.JsString(obj.id),
        "name" -> play.api.libs.json.JsString(obj.name),
        "country" -> play.api.libs.json.JsString(obj.country),
        "language" -> play.api.libs.json.JsString(obj.language),
        "numbers" -> jsObjectLocaleNumbers(obj.numbers)
      )
    }

    implicit def jsonWritesReferenceLocale: play.api.libs.json.Writes[Locale] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.Locale] {
        def writes(obj: io.flow.reference.v0.models.Locale) = {
          jsObjectLocale(obj)
        }
      }
    }

    implicit def jsonReadsReferenceLocaleNumbers: play.api.libs.json.Reads[LocaleNumbers] = {
      for {
        decimal <- (__ \ "decimal").read[String]
        group <- (__ \ "group").read[String]
      } yield LocaleNumbers(decimal, group)
    }

    def jsObjectLocaleNumbers(obj: io.flow.reference.v0.models.LocaleNumbers): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "decimal" -> play.api.libs.json.JsString(obj.decimal),
        "group" -> play.api.libs.json.JsString(obj.group)
      )
    }

    implicit def jsonWritesReferenceLocaleNumbers: play.api.libs.json.Writes[LocaleNumbers] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.LocaleNumbers] {
        def writes(obj: io.flow.reference.v0.models.LocaleNumbers) = {
          jsObjectLocaleNumbers(obj)
        }
      }
    }

    implicit def jsonReadsReferenceLocalizedTranslation: play.api.libs.json.Reads[LocalizedTranslation] = {
      for {
        locale <- (__ \ "locale").read[io.flow.reference.v0.models.Locale]
        name <- (__ \ "name").read[String]
      } yield LocalizedTranslation(locale, name)
    }

    def jsObjectLocalizedTranslation(obj: io.flow.reference.v0.models.LocalizedTranslation): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "locale" -> jsObjectLocale(obj.locale),
        "name" -> play.api.libs.json.JsString(obj.name)
      )
    }

    implicit def jsonWritesReferenceLocalizedTranslation: play.api.libs.json.Writes[LocalizedTranslation] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.LocalizedTranslation] {
        def writes(obj: io.flow.reference.v0.models.LocalizedTranslation) = {
          jsObjectLocalizedTranslation(obj)
        }
      }
    }

    implicit def jsonReadsReferencePaymentMethod: play.api.libs.json.Reads[PaymentMethod] = {
      for {
        id <- (__ \ "id").read[String]
        `type` <- (__ \ "type").read[io.flow.reference.v0.models.PaymentMethodType]
        name <- (__ \ "name").read[String]
        images <- (__ \ "images").read[io.flow.reference.v0.models.PaymentMethodImages]
        regions <- (__ \ "regions").read[Seq[String]]
      } yield PaymentMethod(id, `type`, name, images, regions)
    }

    def jsObjectPaymentMethod(obj: io.flow.reference.v0.models.PaymentMethod): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "id" -> play.api.libs.json.JsString(obj.id),
        "type" -> play.api.libs.json.JsString(obj.`type`.toString),
        "name" -> play.api.libs.json.JsString(obj.name),
        "images" -> jsObjectPaymentMethodImages(obj.images),
        "regions" -> play.api.libs.json.Json.toJson(obj.regions)
      )
    }

    implicit def jsonWritesReferencePaymentMethod: play.api.libs.json.Writes[PaymentMethod] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.PaymentMethod] {
        def writes(obj: io.flow.reference.v0.models.PaymentMethod) = {
          jsObjectPaymentMethod(obj)
        }
      }
    }

    implicit def jsonReadsReferencePaymentMethodImage: play.api.libs.json.Reads[PaymentMethodImage] = {
      for {
        url <- (__ \ "url").read[String]
        width <- (__ \ "width").read[Int]
        height <- (__ \ "height").read[Int]
      } yield PaymentMethodImage(url, width, height)
    }

    def jsObjectPaymentMethodImage(obj: io.flow.reference.v0.models.PaymentMethodImage): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "url" -> play.api.libs.json.JsString(obj.url),
        "width" -> play.api.libs.json.JsNumber(obj.width),
        "height" -> play.api.libs.json.JsNumber(obj.height)
      )
    }

    implicit def jsonWritesReferencePaymentMethodImage: play.api.libs.json.Writes[PaymentMethodImage] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.PaymentMethodImage] {
        def writes(obj: io.flow.reference.v0.models.PaymentMethodImage) = {
          jsObjectPaymentMethodImage(obj)
        }
      }
    }

    implicit def jsonReadsReferencePaymentMethodImages: play.api.libs.json.Reads[PaymentMethodImages] = {
      for {
        small <- (__ \ "small").read[io.flow.reference.v0.models.PaymentMethodImage]
        medium <- (__ \ "medium").read[io.flow.reference.v0.models.PaymentMethodImage]
        large <- (__ \ "large").read[io.flow.reference.v0.models.PaymentMethodImage]
      } yield PaymentMethodImages(small, medium, large)
    }

    def jsObjectPaymentMethodImages(obj: io.flow.reference.v0.models.PaymentMethodImages): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "small" -> jsObjectPaymentMethodImage(obj.small),
        "medium" -> jsObjectPaymentMethodImage(obj.medium),
        "large" -> jsObjectPaymentMethodImage(obj.large)
      )
    }

    implicit def jsonWritesReferencePaymentMethodImages: play.api.libs.json.Writes[PaymentMethodImages] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.PaymentMethodImages] {
        def writes(obj: io.flow.reference.v0.models.PaymentMethodImages) = {
          jsObjectPaymentMethodImages(obj)
        }
      }
    }

    implicit def jsonReadsReferenceProvince: play.api.libs.json.Reads[Province] = {
      for {
        id <- (__ \ "id").read[String]
        iso31662 <- (__ \ "iso_3166_2").read[String]
        name <- (__ \ "name").read[String]
        country <- (__ \ "country").read[String]
        provinceType <- (__ \ "province_type").read[io.flow.reference.v0.models.ProvinceType]
        translations <- (__ \ "translations").readNullable[Seq[io.flow.reference.v0.models.LocalizedTranslation]]
      } yield Province(id, iso31662, name, country, provinceType, translations)
    }

    def jsObjectProvince(obj: io.flow.reference.v0.models.Province): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "id" -> play.api.libs.json.JsString(obj.id),
        "iso_3166_2" -> play.api.libs.json.JsString(obj.iso31662),
        "name" -> play.api.libs.json.JsString(obj.name),
        "country" -> play.api.libs.json.JsString(obj.country),
        "province_type" -> play.api.libs.json.JsString(obj.provinceType.toString)
      ) ++ (obj.translations match {
        case None => play.api.libs.json.Json.obj()
        case Some(x) => play.api.libs.json.Json.obj("translations" -> play.api.libs.json.Json.toJson(x))
      })
    }

    implicit def jsonWritesReferenceProvince: play.api.libs.json.Writes[Province] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.Province] {
        def writes(obj: io.flow.reference.v0.models.Province) = {
          jsObjectProvince(obj)
        }
      }
    }

    implicit def jsonReadsReferenceRegion: play.api.libs.json.Reads[Region] = {
      for {
        id <- (__ \ "id").read[String]
        name <- (__ \ "name").read[String]
        countries <- (__ \ "countries").read[Seq[String]]
        currencies <- (__ \ "currencies").read[Seq[String]]
        languages <- (__ \ "languages").read[Seq[String]]
        measurementSystems <- (__ \ "measurement_systems").read[Seq[String]]
        timezones <- (__ \ "timezones").read[Seq[String]]
      } yield Region(id, name, countries, currencies, languages, measurementSystems, timezones)
    }

    def jsObjectRegion(obj: io.flow.reference.v0.models.Region): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "id" -> play.api.libs.json.JsString(obj.id),
        "name" -> play.api.libs.json.JsString(obj.name),
        "countries" -> play.api.libs.json.Json.toJson(obj.countries),
        "currencies" -> play.api.libs.json.Json.toJson(obj.currencies),
        "languages" -> play.api.libs.json.Json.toJson(obj.languages),
        "measurement_systems" -> play.api.libs.json.Json.toJson(obj.measurementSystems),
        "timezones" -> play.api.libs.json.Json.toJson(obj.timezones)
      )
    }

    implicit def jsonWritesReferenceRegion: play.api.libs.json.Writes[Region] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.Region] {
        def writes(obj: io.flow.reference.v0.models.Region) = {
          jsObjectRegion(obj)
        }
      }
    }

    implicit def jsonReadsReferenceTimezone: play.api.libs.json.Reads[Timezone] = {
      for {
        name <- (__ \ "name").read[String]
        description <- (__ \ "description").read[String]
        offset <- (__ \ "offset").read[Int]
      } yield Timezone(name, description, offset)
    }

    def jsObjectTimezone(obj: io.flow.reference.v0.models.Timezone): play.api.libs.json.JsObject = {
      play.api.libs.json.Json.obj(
        "name" -> play.api.libs.json.JsString(obj.name),
        "description" -> play.api.libs.json.JsString(obj.description),
        "offset" -> play.api.libs.json.JsNumber(obj.offset)
      )
    }

    implicit def jsonWritesReferenceTimezone: play.api.libs.json.Writes[Timezone] = {
      new play.api.libs.json.Writes[io.flow.reference.v0.models.Timezone] {
        def writes(obj: io.flow.reference.v0.models.Timezone) = {
          jsObjectTimezone(obj)
        }
      }
    }
  }
}

package io.flow.reference.v0 {

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
      import io.flow.reference.v0.models._

      val paymentMethodTypeConverter: ApibuilderTypeConverter[io.flow.reference.v0.models.PaymentMethodType] = new ApibuilderTypeConverter[io.flow.reference.v0.models.PaymentMethodType] {
        override def convert(value: String): io.flow.reference.v0.models.PaymentMethodType = io.flow.reference.v0.models.PaymentMethodType(value)
        override def convert(value: io.flow.reference.v0.models.PaymentMethodType): String = value.toString
        override def example: io.flow.reference.v0.models.PaymentMethodType = io.flow.reference.v0.models.PaymentMethodType.Card
        override def validValues: Seq[io.flow.reference.v0.models.PaymentMethodType] = io.flow.reference.v0.models.PaymentMethodType.all
      }
      implicit def pathBindablePaymentMethodType(implicit stringBinder: QueryStringBindable[String]): PathBindable[io.flow.reference.v0.models.PaymentMethodType] = ApibuilderPathBindable(paymentMethodTypeConverter)
      implicit def queryStringBindablePaymentMethodType(implicit stringBinder: QueryStringBindable[String]): QueryStringBindable[io.flow.reference.v0.models.PaymentMethodType] = ApibuilderQueryStringBindable(paymentMethodTypeConverter)

      val provinceTypeConverter: ApibuilderTypeConverter[io.flow.reference.v0.models.ProvinceType] = new ApibuilderTypeConverter[io.flow.reference.v0.models.ProvinceType] {
        override def convert(value: String): io.flow.reference.v0.models.ProvinceType = io.flow.reference.v0.models.ProvinceType(value)
        override def convert(value: io.flow.reference.v0.models.ProvinceType): String = value.toString
        override def example: io.flow.reference.v0.models.ProvinceType = io.flow.reference.v0.models.ProvinceType.City
        override def validValues: Seq[io.flow.reference.v0.models.ProvinceType] = io.flow.reference.v0.models.ProvinceType.all
      }
      implicit def pathBindableProvinceType(implicit stringBinder: QueryStringBindable[String]): PathBindable[io.flow.reference.v0.models.ProvinceType] = ApibuilderPathBindable(provinceTypeConverter)
      implicit def queryStringBindableProvinceType(implicit stringBinder: QueryStringBindable[String]): QueryStringBindable[io.flow.reference.v0.models.ProvinceType] = ApibuilderQueryStringBindable(provinceTypeConverter)
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
