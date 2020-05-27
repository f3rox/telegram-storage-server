package by.iodkowski.telegram.api.text

import org.drinkless.tdlib.TdApi

/**
  * Represents a part of the text which must be formatted differently.
  */
sealed abstract class TextEntityType extends Product with Serializable

/**
  * A mention of a user by their username.
  */
case object TextEntityTypeMention extends TextEntityType

/**
  * A hashtag text, beginning with &quot;#&quot;.
  */
case object TextEntityTypeHashtag extends TextEntityType

/**
  * A cashtag text, beginning with &quot;$&quot; and consisting of capital english letters (i.e. &quot;$USD&quot;).
  */
case object TextEntityTypeCashtag extends TextEntityType

/**
  * A bot command, beginning with &quot;/&quot;. This shouldn't be highlighted if there are no bots in the chat.
  */
case object TextEntityTypeBotCommand extends TextEntityType

/**
  * An HTTP URL.
  */
case object TextEntityTypeUrl extends TextEntityType

/**
  * An email address.
  */
case object TextEntityTypeEmailAddress extends TextEntityType

/**
  * A phone number.
  */
case object TextEntityTypePhoneNumber extends TextEntityType

/**
  * A bold text.
  */
case object TextEntityTypeBold extends TextEntityType

/**
  * An italic text.
  */
case object TextEntityTypeItalic extends TextEntityType

/**
  * An underlined text.
  */
case object TextEntityTypeUnderline extends TextEntityType

/**
  * A strikethrough text.
  */
case object TextEntityTypeStrikethrough extends TextEntityType

/**
  * Text that must be formatted as if inside a code HTML tag.
  */
case object TextEntityTypeCode extends TextEntityType

/**
  * Text that must be formatted as if inside a pre HTML tag.
  */
case object TextEntityTypePre extends TextEntityType

/**
  * Text that must be formatted as if inside pre, and code HTML tags.
  *
  * @param language Programming language of the code; as defined by the sender.
  */
final case class TextEntityTypePreCode(language: String) extends TextEntityType

private[api] object TextEntityTypePreCode {
  def fromJava(o: TdApi.TextEntityTypePreCode): TextEntityTypePreCode = TextEntityTypePreCode(o.language)
}

/**
  * A text description shown instead of a raw URL.
  */
final case class TextEntityTypeTextUrl(url: String) extends TextEntityType

private[api] object TextEntityTypeTextUrl {
  def fromJava(o: TdApi.TextEntityTypeTextUrl): TextEntityTypeTextUrl = TextEntityTypeTextUrl(o.url)
}

/**
  * A text shows instead of a raw mention of the user (e.g., when the user has no username).
  *
  * @param userId Identifier of the mentioned user.
  */
final case class TextEntityTypeMentionName(userId: Int) extends TextEntityType

private[api] object TextEntityTypeMentionName {
  def fromJava(o: TdApi.TextEntityTypeMentionName): TextEntityTypeMentionName = TextEntityTypeMentionName(o.userId)
}

private[api] object TextEntityType {
  def fromJava(o: TdApi.TextEntityType): TextEntityType =
    o.getConstructor match {
      case TdApi.TextEntityTypeBold.CONSTRUCTOR          => TextEntityTypeBold
      case TdApi.TextEntityTypeBotCommand.CONSTRUCTOR    => TextEntityTypeBotCommand
      case TdApi.TextEntityTypeCashtag.CONSTRUCTOR       => TextEntityTypeCashtag
      case TdApi.TextEntityTypeCode.CONSTRUCTOR          => TextEntityTypeCode
      case TdApi.TextEntityTypeEmailAddress.CONSTRUCTOR  => TextEntityTypeEmailAddress
      case TdApi.TextEntityTypeHashtag.CONSTRUCTOR       => TextEntityTypeHashtag
      case TdApi.TextEntityTypeItalic.CONSTRUCTOR        => TextEntityTypeItalic
      case TdApi.TextEntityTypeMention.CONSTRUCTOR       => TextEntityTypeMention
      case TdApi.TextEntityTypePhoneNumber.CONSTRUCTOR   => TextEntityTypePhoneNumber
      case TdApi.TextEntityTypePre.CONSTRUCTOR           => TextEntityTypePre
      case TdApi.TextEntityTypeStrikethrough.CONSTRUCTOR => TextEntityTypeStrikethrough
      case TdApi.TextEntityTypeUnderline.CONSTRUCTOR     => TextEntityTypeUnderline
      case TdApi.TextEntityTypeUrl.CONSTRUCTOR           => TextEntityTypeUrl
      case TdApi.TextEntityTypeMentionName.CONSTRUCTOR =>
        TextEntityTypeMentionName.fromJava(o.asInstanceOf[TdApi.TextEntityTypeMentionName])
      case TdApi.TextEntityTypePreCode.CONSTRUCTOR =>
        TextEntityTypePreCode.fromJava(o.asInstanceOf[TdApi.TextEntityTypePreCode])
      case TdApi.TextEntityTypeTextUrl.CONSTRUCTOR =>
        TextEntityTypeTextUrl.fromJava(o.asInstanceOf[TdApi.TextEntityTypeTextUrl])
    }
}
