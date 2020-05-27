package by.iodkowski.telegram.api.language

import org.drinkless.tdlib.TdApi

/**
  * This class is an abstract base class.
  * Represents the value of a string in a language pack.
  */
sealed abstract class LanguagePackStringValue extends Product with Serializable

/**
  * An ordinary language pack string.
  *
  * @param value String value.
  */
final case class LanguagePackStringValueOrdinary(value: String) extends LanguagePackStringValue

private[api] object LanguagePackStringValueOrdinary {
  def fromJava(o: TdApi.LanguagePackStringValueOrdinary): LanguagePackStringValueOrdinary =
    LanguagePackStringValueOrdinary(o.value)
}

/**
  * A language pack string which has different forms based on the number of some object it mentions. See https://www.unicode.org/cldr/charts/latest/supplemental/language_plural_rules.html for more info.
  *
  * @param zeroValue  Value for zero objects.
  * @param oneValue   Value for one object.
  * @param twoValue   Value for two objects.
  * @param fewValue   Value for few objects.
  * @param manyValue  Value for many objects.
  * @param otherValue Default value.
  */
final case class LanguagePackStringValuePluralized(
  zeroValue: String,
  oneValue: String,
  twoValue: String,
  fewValue: String,
  manyValue: String,
  otherValue: String
) extends LanguagePackStringValue

private[api] object LanguagePackStringValuePluralized {
  def fromJava(o: TdApi.LanguagePackStringValuePluralized): LanguagePackStringValuePluralized =
    LanguagePackStringValuePluralized(o.zeroValue, o.oneValue, o.twoValue, o.fewValue, o.manyValue, o.otherValue)
}

/**
  * A deleted language pack string, the value should be taken from the built-in english language pack.
  */
case object LanguagePackStringValueDeleted extends LanguagePackStringValue

private[api] object LanguagePackStringValue {
  def fromJava(o: TdApi.LanguagePackStringValue): LanguagePackStringValue = o.getConstructor match {
    case TdApi.LanguagePackStringValueOrdinary.CONSTRUCTOR =>
      LanguagePackStringValueOrdinary.fromJava(o.asInstanceOf[TdApi.LanguagePackStringValueOrdinary])
    case TdApi.LanguagePackStringValuePluralized.CONSTRUCTOR =>
      LanguagePackStringValuePluralized.fromJava(o.asInstanceOf[TdApi.LanguagePackStringValuePluralized])
    case TdApi.LanguagePackStringValueDeleted.CONSTRUCTOR => LanguagePackStringValueDeleted
  }
}
