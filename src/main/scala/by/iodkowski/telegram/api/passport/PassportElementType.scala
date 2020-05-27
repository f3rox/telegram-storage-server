package by.iodkowski.telegram.api.passport

import org.drinkless.tdlib.TdApi

/**
  * Contains the type of a Telegram Passport element.
  */
sealed abstract class PassportElementType extends Product with Serializable

/**
  * A Telegram Passport element containing the user's personal details.
  */
case object PassportElementTypePersonalDetails extends PassportElementType

/**
  * A Telegram Passport element containing the user's passport.
  */
case object PassportElementTypePassport extends PassportElementType

/**
  * A Telegram Passport element containing the user's driver license.
  */
case object PassportElementTypeDriverLicense extends PassportElementType

/**
  * A Telegram Passport element containing the user's identity card.
  */
case object PassportElementTypeIdentityCard extends PassportElementType

/**
  * A Telegram Passport element containing the user's internal passport.
  */
case object PassportElementTypeInternalPassport extends PassportElementType

/**
  * A Telegram Passport element containing the user's address.
  */
case object PassportElementTypeAddress extends PassportElementType

/**
  * A Telegram Passport element containing the user's utility bill.
  */
case object PassportElementTypeUtilityBill extends PassportElementType

/**
  * A Telegram Passport element containing the user's bank statement.
  */
case object PassportElementTypeBankStatement extends PassportElementType

/**
  * A Telegram Passport element containing the user's rental agreement.
  */
case object PassportElementTypeRentalAgreement extends PassportElementType

/**
  * A Telegram Passport element containing the registration page of the user's passport.
  */
case object PassportElementTypePassportRegistration extends PassportElementType

/**
  * A Telegram Passport element containing the user's temporary registration.
  */
case object PassportElementTypeTemporaryRegistration extends PassportElementType

/**
  * A Telegram Passport element containing the user's phone number.
  */
case object PassportElementTypePhoneNumber extends PassportElementType

/**
  * A Telegram Passport element containing the user's email address.
  */
case object PassportElementTypeEmailAddress extends PassportElementType

private[api] object PassportElementType {
  def fromJava(o: TdApi.PassportElementType): PassportElementType = o.getConstructor match {
    case TdApi.PassportElementTypePersonalDetails.CONSTRUCTOR       => PassportElementTypePersonalDetails
    case TdApi.PassportElementTypePassport.CONSTRUCTOR              => PassportElementTypePassport
    case TdApi.PassportElementTypeDriverLicense.CONSTRUCTOR         => PassportElementTypeDriverLicense
    case TdApi.PassportElementTypeIdentityCard.CONSTRUCTOR          => PassportElementTypeIdentityCard
    case TdApi.PassportElementTypeInternalPassport.CONSTRUCTOR      => PassportElementTypeInternalPassport
    case TdApi.PassportElementTypeAddress.CONSTRUCTOR               => PassportElementTypeAddress
    case TdApi.PassportElementTypeUtilityBill.CONSTRUCTOR           => PassportElementTypeUtilityBill
    case TdApi.PassportElementTypeBankStatement.CONSTRUCTOR         => PassportElementTypeBankStatement
    case TdApi.PassportElementTypeRentalAgreement.CONSTRUCTOR       => PassportElementTypeRentalAgreement
    case TdApi.PassportElementTypePassportRegistration.CONSTRUCTOR  => PassportElementTypePassportRegistration
    case TdApi.PassportElementTypeTemporaryRegistration.CONSTRUCTOR => PassportElementTypeTemporaryRegistration
    case TdApi.PassportElementTypePhoneNumber.CONSTRUCTOR           => PassportElementTypePhoneNumber
    case TdApi.PassportElementTypeEmailAddress.CONSTRUCTOR          => PassportElementTypeEmailAddress
  }
}
