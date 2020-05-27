package by.iodkowski.telegram.api.keyboard

import org.drinkless.tdlib.TdApi

/**
  * Represents a single button in a bot keyboard.
  *
  * @param text Text of the button.
  * @param type Type of the button.
  */
final case class KeyboardButton(text: String, `type`: KeyboardButtonType)

private[api] object KeyboardButton {
  def fromJava(o: TdApi.KeyboardButton): KeyboardButton = KeyboardButton(o.text, KeyboardButtonType.fromJava(o.`type`))
}
