package by.iodkowski.telegram.api.auth

import by.iodkowski.telegram.api.TermsOfService
import org.drinkless.tdlib.TdApi

/**
  * Represents the current authorization state of the client.
  */
sealed abstract class AuthorizationState extends Product with Serializable

/**
  * TDLib needs TdlibParameters for initialization.
  */
case object AuthorizationStateWaitTdlibParameters extends AuthorizationState

/**
  * TDLib needs an encryption key to decrypt the local database.
  *
  * @param isEncrypted True, if the database is currently encrypted.
  */
final case class AuthorizationStateWaitEncryptionKey(isEncrypted: Boolean) extends AuthorizationState

object AuthorizationStateWaitEncryptionKey {
  private[api] def fromJava(o: TdApi.AuthorizationStateWaitEncryptionKey): AuthorizationStateWaitEncryptionKey =
    AuthorizationStateWaitEncryptionKey(o.isEncrypted)
}

/**
  * TDLib needs the user's phone number to authorize. Call `setAuthenticationPhoneNumber` to provide the phone number, or use `requestQrCodeAuthentication`, or `checkAuthenticationBotToken` for other authentication options.
  */
case object AuthorizationStateWaitPhoneNumber extends AuthorizationState

/**
  * TDLib needs the user's authentication code to authorize.
  *
  * @param codeInfo Information about the authorization code that was sent.
  */
final case class AuthorizationStateWaitCode(codeInfo: AuthenticationCodeInfo) extends AuthorizationState

object AuthorizationStateWaitCode {
  private[api] def fromJava(o: TdApi.AuthorizationStateWaitCode): AuthorizationStateWaitCode =
    AuthorizationStateWaitCode(AuthenticationCodeInfo.fromJava(o.codeInfo))
}

/**
  * The user needs to confirm authorization on another logged in device by scanning a QR code with the provided link.
  *
  * @param link A tg:// URL for the QR code. The link will be updated frequently.
  */
final case class AuthorizationStateWaitOtherDeviceConfirmation(link: String) extends AuthorizationState

object AuthorizationStateWaitOtherDeviceConfirmation {
  private[api] def fromJava(
    o: TdApi.AuthorizationStateWaitOtherDeviceConfirmation
  ): AuthorizationStateWaitOtherDeviceConfirmation =
    AuthorizationStateWaitOtherDeviceConfirmation(o.link)
}

/**
  * The user is unregistered and need to accept terms of service and enter their first name and last name to finish registration.
  *
  * @param termsOfService Telegram terms of service.
  */
final case class AuthorizationStateWaitRegistration(termsOfService: TermsOfService) extends AuthorizationState

object AuthorizationStateWaitRegistration {
  private[api] def fromJava(o: TdApi.AuthorizationStateWaitRegistration): AuthorizationStateWaitRegistration =
    AuthorizationStateWaitRegistration(TermsOfService.fromJava(o.termsOfService))
}

/**
  * The user has been authorized, but needs to enter a password to start using the application.
  *
  * @param passwordHint                Hint for the password; may be empty.
  * @param hasRecoveryEmailAddress     True, if a recovery email address has been set up.
  * @param recoveryEmailAddressPattern Pattern of the email address to which the recovery email was sent; empty until a recovery email has been sent.
  */
final case class AuthorizationStateWaitPassword(
  passwordHint: String,
  hasRecoveryEmailAddress: Boolean,
  recoveryEmailAddressPattern: String
) extends AuthorizationState

object AuthorizationStateWaitPassword {
  private[api] def fromJava(o: TdApi.AuthorizationStateWaitPassword): AuthorizationStateWaitPassword =
    AuthorizationStateWaitPassword(o.passwordHint, o.hasRecoveryEmailAddress, o.recoveryEmailAddressPattern)
}

/**
  * The user has been successfully authorized. TDLib is now ready to answer queries.
  */
case object AuthorizationStateReady extends AuthorizationState

/**
  * The user is currently logging out.
  */
case object AuthorizationStateLoggingOut extends AuthorizationState

/**
  * TDLib is closing, all subsequent queries will be answered with the error 500. Note that closing TDLib can take a while. All resources will be freed only after authorizationStateClosed has been received.
  */
case object AuthorizationStateClosing extends AuthorizationState

/**
  * TDLib client is in its final state. All databases are closed and all resources are released. No other updates will be received after this. All queries will be responded to with error code 500. To continue working, one should create a new instance of the TDLib client.
  */
case object AuthorizationStateClosed extends AuthorizationState

object AuthorizationState {
  private[api] def fromJava(o: TdApi.AuthorizationState): AuthorizationState =
    o.getConstructor match {
      case TdApi.AuthorizationStateReady.CONSTRUCTOR               => AuthorizationStateReady
      case TdApi.AuthorizationStateClosed.CONSTRUCTOR              => AuthorizationStateClosed
      case TdApi.AuthorizationStateClosing.CONSTRUCTOR             => AuthorizationStateClosing
      case TdApi.AuthorizationStateLoggingOut.CONSTRUCTOR          => AuthorizationStateLoggingOut
      case TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR     => AuthorizationStateWaitPhoneNumber
      case TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR => AuthorizationStateWaitTdlibParameters
      case TdApi.AuthorizationStateWaitCode.CONSTRUCTOR =>
        AuthorizationStateWaitCode.fromJava(o.asInstanceOf[TdApi.AuthorizationStateWaitCode])
      case TdApi.AuthorizationStateWaitEncryptionKey.CONSTRUCTOR =>
        AuthorizationStateWaitEncryptionKey.fromJava(o.asInstanceOf[TdApi.AuthorizationStateWaitEncryptionKey])
      case TdApi.AuthorizationStateWaitOtherDeviceConfirmation.CONSTRUCTOR =>
        AuthorizationStateWaitOtherDeviceConfirmation
          .fromJava(o.asInstanceOf[TdApi.AuthorizationStateWaitOtherDeviceConfirmation])
      case TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR =>
        AuthorizationStateWaitPassword.fromJava(o.asInstanceOf[TdApi.AuthorizationStateWaitPassword])
      case TdApi.AuthorizationStateWaitRegistration.CONSTRUCTOR =>
        AuthorizationStateWaitRegistration.fromJava(o.asInstanceOf[TdApi.AuthorizationStateWaitRegistration])
    }
}
