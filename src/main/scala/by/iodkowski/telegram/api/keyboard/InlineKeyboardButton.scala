package by.iodkowski.telegram.api.keyboard

import org.drinkless.tdlib.TdApi

/**
  * Represents a single button in an inline keyboard.
  *
  * @param text Text of the button.
  * @param type Type of the button.
  */
final case class InlineKeyboardButton(text: String, `type`: InlineKeyboardButtonType)

private[api] object InlineKeyboardButton {
  def fromJava(o: TdApi.InlineKeyboardButton): InlineKeyboardButton =
    InlineKeyboardButton(o.text, InlineKeyboardButtonType.fromJava(o.`type`))
}
