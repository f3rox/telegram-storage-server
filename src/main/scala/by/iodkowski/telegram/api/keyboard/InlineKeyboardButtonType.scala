package by.iodkowski.telegram.api.keyboard

import org.drinkless.tdlib.TdApi

/**
  * Describes the type of an inline keyboard button.
  */
sealed abstract class InlineKeyboardButtonType extends Product with Serializable

/**
  * A button that opens a specified URL.
  *
  * @param url HTTP or tg:// URL to open.
  */
final case class InlineKeyboardButtonTypeUrl(url: String) extends InlineKeyboardButtonType

private[api] object InlineKeyboardButtonTypeUrl {
  def fromJava(o: TdApi.InlineKeyboardButtonTypeUrl): InlineKeyboardButtonTypeUrl = InlineKeyboardButtonTypeUrl(o.url)
}

/**
  * A button that opens a specified URL and automatically logs in in current user if they allowed to do that.
  *
  * @param url         An HTTP URL to open.
  * @param id          Unique button identifier.
  * @param forwardText If non-empty, new text of the button in forwarded messages.
  */
final case class InlineKeyboardButtonTypeLoginUrl(url: String, id: Int, forwardText: String)
    extends InlineKeyboardButtonType

private[api] object InlineKeyboardButtonTypeLoginUrl {
  def fromJava(o: TdApi.InlineKeyboardButtonTypeLoginUrl): InlineKeyboardButtonTypeLoginUrl =
    InlineKeyboardButtonTypeLoginUrl(o.url, o.id, o.forwardText)
}

/**
  * A button that sends a special callback query to a bot.
  *
  * @param data Data to be sent to the bot via a callback query.
  */
final case class InlineKeyboardButtonTypeCallback(data: List[Byte]) extends InlineKeyboardButtonType

private[api] object InlineKeyboardButtonTypeCallback {
  def fromJava(o: TdApi.InlineKeyboardButtonTypeCallback): InlineKeyboardButtonTypeCallback =
    InlineKeyboardButtonTypeCallback(o.data.toList)
}

/**
  * A button with a game that sends a special callback query to a bot. This button must be in the first column and row of the keyboard and can be attached only to a message with content of the type messageGame.
  */
case object InlineKeyboardButtonTypeCallbackGame extends InlineKeyboardButtonType

/**
  * A button that forces an inline query to the bot to be inserted in the input field.
  *
  * @param query         Inline query to be sent to the bot.
  * @param inCurrentChat True, if the inline query should be sent from the current chat.
  */
final case class InlineKeyboardButtonTypeSwitchInline(query: String, inCurrentChat: Boolean)
    extends InlineKeyboardButtonType

private[api] object InlineKeyboardButtonTypeSwitchInline {
  def fromJava(o: TdApi.InlineKeyboardButtonTypeSwitchInline): InlineKeyboardButtonTypeSwitchInline =
    InlineKeyboardButtonTypeSwitchInline(o.query, o.inCurrentChat)
}

/**
  * A button to buy something. This button must be in the first column and row of the keyboard and can be attached only to a message with content of the type messageInvoice.
  */
case object InlineKeyboardButtonTypeBuy extends InlineKeyboardButtonType

private[api] object InlineKeyboardButtonType {
  def fromJava(o: TdApi.InlineKeyboardButtonType): InlineKeyboardButtonType = o.getConstructor match {
    case TdApi.InlineKeyboardButtonTypeCallbackGame.CONSTRUCTOR => InlineKeyboardButtonTypeCallbackGame
    case TdApi.InlineKeyboardButtonTypeBuy.CONSTRUCTOR          => InlineKeyboardButtonTypeBuy
    case TdApi.InlineKeyboardButtonTypeUrl.CONSTRUCTOR =>
      InlineKeyboardButtonTypeUrl.fromJava(o.asInstanceOf[TdApi.InlineKeyboardButtonTypeUrl])
    case TdApi.InlineKeyboardButtonTypeLoginUrl.CONSTRUCTOR =>
      InlineKeyboardButtonTypeLoginUrl.fromJava(o.asInstanceOf[TdApi.InlineKeyboardButtonTypeLoginUrl])
    case TdApi.InlineKeyboardButtonTypeCallback.CONSTRUCTOR =>
      InlineKeyboardButtonTypeCallback.fromJava(o.asInstanceOf[TdApi.InlineKeyboardButtonTypeCallback])
    case TdApi.InlineKeyboardButtonTypeSwitchInline.CONSTRUCTOR =>
      InlineKeyboardButtonTypeSwitchInline.fromJava(o.asInstanceOf[TdApi.InlineKeyboardButtonTypeSwitchInline])
  }
}
