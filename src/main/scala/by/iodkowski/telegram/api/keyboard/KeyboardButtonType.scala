package by.iodkowski.telegram.api.keyboard

import org.drinkless.tdlib.TdApi

/**
  * Describes a keyboard button type.
  */
sealed abstract class KeyboardButtonType extends Product with Serializable

/**
  * A simple button, with text that should be sent when the button is pressed.
  */
case object KeyboardButtonTypeText extends KeyboardButtonType

/**
  * A button that sends the user's phone number when pressed; available only in private chats.
  */
case object KeyboardButtonTypeRequestPhoneNumber extends KeyboardButtonType

/**
  * A button that sends the user's location when pressed; available only in private chats.
  */
case object KeyboardButtonTypeRequestLocation extends KeyboardButtonType

/**
  * A button that allows the user to create and send a poll when pressed; available only in private chats.
  *
  * @param forceRegular If true, only regular polls must be allowed to create.
  * @param forceQuiz    If true, only polls in quiz mode must be allowed to create.
  */
final case class KeyboardButtonTypeRequestPoll(forceRegular: Boolean, forceQuiz: Boolean) extends KeyboardButtonType

private[api] object KeyboardButtonTypeRequestPoll {
  def fromJava(o: TdApi.KeyboardButtonTypeRequestPoll): KeyboardButtonTypeRequestPoll =
    KeyboardButtonTypeRequestPoll(o.forceRegular, o.forceQuiz)
}

private[api] object KeyboardButtonType {
  def fromJava(o: TdApi.KeyboardButtonType): KeyboardButtonType = o.getConstructor match {
    case TdApi.KeyboardButtonTypeText.CONSTRUCTOR               => KeyboardButtonTypeText
    case TdApi.KeyboardButtonTypeRequestPhoneNumber.CONSTRUCTOR => KeyboardButtonTypeRequestPhoneNumber
    case TdApi.KeyboardButtonTypeRequestLocation.CONSTRUCTOR    => KeyboardButtonTypeRequestLocation
    case TdApi.KeyboardButtonTypeRequestPoll.CONSTRUCTOR =>
      KeyboardButtonTypeRequestPoll.fromJava(o.asInstanceOf[TdApi.KeyboardButtonTypeRequestPoll])
  }
}
