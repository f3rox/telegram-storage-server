package by.iodkowski.telegram.api.text

import org.drinkless.tdlib.TdApi

/**
  * A text with some entities.
  *
  * @param text     The text.
  * @param entities Entities contained in the text. Entities can be nested, but must not mutually intersect with each other. Pre, Code and PreCode entities can't contain other entities. Bold, Italic, Underline and Strikethrough entities can contain and to be contained in all other entities. All other entities can't contain each other.
  */
final case class FormattedText(text: String, entities: List[TextEntity])

private[api] object FormattedText {
  def fromJava(o: TdApi.FormattedText): FormattedText =
    FormattedText(o.text, o.entities.map(TextEntity.fromJava).toList)
}
