package by.iodkowski.telegram.api.auth

import org.drinkless.tdlib.TdApi

/**
  * Information about the authentication code that was sent.
  *
  * @param phoneNumber A phone number that is being authenticated.
  * @param type        Describes the way the code was sent to the user.
  * @param nextType    Describes the way the next code will be sent to the user; may be None.
  * @param timeout     Timeout before the code should be re-sent, in seconds.
  */
final case class AuthenticationCodeInfo(
  phoneNumber: String,
  `type`: AuthenticationCodeType,
  nextType: Option[AuthenticationCodeType],
  timeout: Int
)

private[api] object AuthenticationCodeInfo {
  def fromJava(o: TdApi.AuthenticationCodeInfo): AuthenticationCodeInfo =
    AuthenticationCodeInfo(
      o.phoneNumber,
      AuthenticationCodeType.fromJava(o.`type`),
      Option(o.nextType).map(AuthenticationCodeType.fromJava),
      o.timeout
    )
}
