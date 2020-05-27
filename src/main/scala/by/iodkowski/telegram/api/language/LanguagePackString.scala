package by.iodkowski.telegram.api.language

import org.drinkless.tdlib.TdApi

/**
  * Represents one language pack string.
  *
  * @param key   String key.
  * @param value String value.
  */
final case class LanguagePackString(key: String, value: LanguagePackStringValue)

private[api] object LanguagePackString {
  def fromJava(o: TdApi.LanguagePackString): LanguagePackString =
    LanguagePackString(o.key, LanguagePackStringValue.fromJava(o.value))
}
