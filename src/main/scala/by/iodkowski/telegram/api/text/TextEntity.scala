package by.iodkowski.telegram.api.text

import org.drinkless.tdlib.TdApi

/**
  * Represents a part of the text that needs to be formatted in some unusual way.
  *
  * @param offset Offset of the entity in UTF-16 code units.
  * @param length Length of the entity, in UTF-16 code units.
  * @param type   Type of the entity.
  */
final case class TextEntity(offset: Int, length: Int, `type`: TextEntityType)

private[api] object TextEntity {
  def fromJava(o: TdApi.TextEntity): TextEntity =
    TextEntity(o.offset, o.length, TextEntityType.fromJava(o.`type`))
}
