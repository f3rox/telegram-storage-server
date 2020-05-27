package by.iodkowski.telegram.api.auth

import org.drinkless.tdlib.TdApi

/**
  * Contains settings for the authentication of the user's phone number.
  *
  * @param allowFlashCall       Pass true if the authentication code may be sent via flash call to the specified phone number.
  * @param isCurrentPhoneNumber Pass true if the authenticated phone number is used on the current device.
  * @param allowSmsRetrieverApi For official applications only. True, if the app can use Android SMS Retriever API (requires Google Play Services &gt;= 10.2) to automatically receive the authentication code from the SMS. See https://developers.google.com/identity/sms-retriever/ for more details.
  */
final case class PhoneNumberAuthenticationSettings(
  allowFlashCall: Boolean,
  isCurrentPhoneNumber: Boolean,
  allowSmsRetrieverApi: Boolean
) {
  def toJava: TdApi.PhoneNumberAuthenticationSettings =
    new TdApi.PhoneNumberAuthenticationSettings(allowFlashCall, isCurrentPhoneNumber, allowSmsRetrieverApi)
}
