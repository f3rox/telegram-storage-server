package by.iodkowski.telegram.api.message

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.keyboard.{InlineKeyboardButton, KeyboardButton}

/**
  * Contains a description of a custom keyboard and actions that can be done with it to quickly reply to bots.
  */
sealed abstract class ReplyMarkup extends Product with Serializable

/**
  * Instructs clients to remove the keyboard once this message has been received. This kind of keyboard can't be received in an incoming message; instead, UpdateChatReplyMarkup with messageId == 0 will be sent.
  *
  * @param isPersonal True, if the keyboard is removed only for the mentioned users or the target user of a reply.
  */
final case class ReplyMarkupRemoveKeyboard(isPersonal: Boolean) extends ReplyMarkup

private[api] object ReplyMarkupRemoveKeyboard {
  def fromJava(o: TdApi.ReplyMarkupRemoveKeyboard): ReplyMarkupRemoveKeyboard = ReplyMarkupRemoveKeyboard(o.isPersonal)
}

/**
  * Instructs clients to force a reply to this message.
  *
  * @param isPersonal True, if a forced reply must automatically be shown to the current user. For outgoing messages, specify true to show the forced reply only for the mentioned users and for the target user of a reply.
  */
final case class ReplyMarkupForceReply(isPersonal: Boolean) extends ReplyMarkup

private[api] object ReplyMarkupForceReply {
  def fromJava(o: TdApi.ReplyMarkupForceReply): ReplyMarkupForceReply = ReplyMarkupForceReply(o.isPersonal)
}

/**
  * Contains a custom keyboard layout to quickly reply to bots.
  *
  * @param rows           A list of rows of bot keyboard buttons.
  * @param resizeKeyboard True, if the client needs to resize the keyboard vertically.
  * @param oneTime        True, if the client needs to hide the keyboard after use.
  * @param isPersonal     True, if the keyboard must automatically be shown to the current user. For outgoing messages, specify true to show the keyboard only for the mentioned users and for the target user of a reply.
  */
final case class ReplyMarkupShowKeyboard(
  rows: List[List[KeyboardButton]],
  resizeKeyboard: Boolean,
  oneTime: Boolean,
  isPersonal: Boolean
) extends ReplyMarkup

private[api] object ReplyMarkupShowKeyboard {
  def fromJava(o: TdApi.ReplyMarkupShowKeyboard): ReplyMarkupShowKeyboard =
    ReplyMarkupShowKeyboard(
      o.rows.map(_.map(KeyboardButton.fromJava).toList).toList,
      o.resizeKeyboard,
      o.oneTime,
      o.isPersonal
    )
}

/**
  * Contains an inline keyboard layout.
  *
  * @param rows A list of rows of inline keyboard buttons.
  */
final case class ReplyMarkupInlineKeyboard(rows: List[List[InlineKeyboardButton]]) extends ReplyMarkup

private[api] object ReplyMarkupInlineKeyboard {
  def fromJava(o: TdApi.ReplyMarkupInlineKeyboard): ReplyMarkupInlineKeyboard =
    ReplyMarkupInlineKeyboard(o.rows.map(_.map(InlineKeyboardButton.fromJava).toList).toList)
}

private[api] object ReplyMarkup {
  def fromJava(o: TdApi.ReplyMarkup): ReplyMarkup = o.getConstructor match {
    case TdApi.ReplyMarkupRemoveKeyboard.CONSTRUCTOR =>
      ReplyMarkupRemoveKeyboard.fromJava(o.asInstanceOf[TdApi.ReplyMarkupRemoveKeyboard])
    case TdApi.ReplyMarkupForceReply.CONSTRUCTOR =>
      ReplyMarkupForceReply.fromJava(o.asInstanceOf[TdApi.ReplyMarkupForceReply])
    case TdApi.ReplyMarkupShowKeyboard.CONSTRUCTOR =>
      ReplyMarkupShowKeyboard.fromJava(o.asInstanceOf[TdApi.ReplyMarkupShowKeyboard])
    case TdApi.ReplyMarkupInlineKeyboard.CONSTRUCTOR =>
      ReplyMarkupInlineKeyboard.fromJava(o.asInstanceOf[TdApi.ReplyMarkupInlineKeyboard])
  }
}
