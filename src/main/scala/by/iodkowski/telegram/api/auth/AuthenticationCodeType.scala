package by.iodkowski.telegram.api.auth

import org.drinkless.tdlib.TdApi

/**
  * Provides information about the method by which an authentication code is delivered to the user.
  */
sealed abstract class AuthenticationCodeType extends Product with Serializable

/**
  * An authentication code is delivered via a private Telegram message, which can be viewed in another client.
  *
  * @param length Length of the code.
  */
final case class AuthenticationCodeTypeTelegramMessage(length: Int) extends AuthenticationCodeType

private[api] object AuthenticationCodeTypeTelegramMessage {
  def fromJava(o: TdApi.AuthenticationCodeTypeTelegramMessage): AuthenticationCodeTypeTelegramMessage =
    AuthenticationCodeTypeTelegramMessage(o.length)
}

/**
  * An authentication code is delivered via an SMS message to the specified phone number.
  *
  * @param length Length of the code.
  */
final case class AuthenticationCodeTypeSms(length: Int) extends AuthenticationCodeType

private[api] object AuthenticationCodeTypeSms {
  def fromJava(o: TdApi.AuthenticationCodeTypeSms): AuthenticationCodeTypeSms = AuthenticationCodeTypeSms(o.length)
}

/**
  * An authentication code is delivered via a phone call to the specified phone number.
  *
  * @param length Length of the code.
  */
final case class AuthenticationCodeTypeCall(length: Int) extends AuthenticationCodeType

private[api] object AuthenticationCodeTypeCall {
  def fromJava(o: TdApi.AuthenticationCodeTypeCall): AuthenticationCodeTypeCall = AuthenticationCodeTypeCall(o.length)
}

/**
  * An authentication code is delivered by an immediately cancelled call to the specified phone number. The number from which the call was made is the code.
  *
  * @param pattern Pattern of the phone number from which the call will be made.
  */
final case class AuthenticationCodeTypeFlashCall(pattern: String) extends AuthenticationCodeType

private[api] object AuthenticationCodeTypeFlashCall {
  def fromJava(o: TdApi.AuthenticationCodeTypeFlashCall): AuthenticationCodeTypeFlashCall =
    AuthenticationCodeTypeFlashCall(o.pattern)
}

private[api] object AuthenticationCodeType {
  def fromJava(o: TdApi.AuthenticationCodeType): AuthenticationCodeType =
    o.getConstructor match {
      case TdApi.AuthenticationCodeTypeCall.CONSTRUCTOR =>
        AuthenticationCodeTypeCall.fromJava(o.asInstanceOf[TdApi.AuthenticationCodeTypeCall])
      case TdApi.AuthenticationCodeTypeFlashCall.CONSTRUCTOR =>
        AuthenticationCodeTypeFlashCall.fromJava(o.asInstanceOf[TdApi.AuthenticationCodeTypeFlashCall])
      case TdApi.AuthenticationCodeTypeSms.CONSTRUCTOR =>
        AuthenticationCodeTypeSms.fromJava(o.asInstanceOf[TdApi.AuthenticationCodeTypeSms])
      case TdApi.AuthenticationCodeTypeTelegramMessage.CONSTRUCTOR =>
        AuthenticationCodeTypeTelegramMessage.fromJava(o.asInstanceOf[TdApi.AuthenticationCodeTypeTelegramMessage])
    }
}
