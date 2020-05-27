package by.iodkowski.telegram.api.user

import org.drinkless.tdlib.TdApi

/**
  * Represents a user.
  *
  * @param id                User identifier.
  * @param firstName         First name of the user.
  * @param lastName          Last name of the user.
  * @param username          Username of the user.
  * @param phoneNumber       Phone number of the user.
  * @param status            Current online status of the user.
  * @param profilePhoto      Profile photo of the user; may be None.
  * @param isContact         The user is a contact of the current user.
  * @param isMutualContact   The user is a contact of the current user and the current user is a contact of the user.
  * @param isVerified        True, if the user is verified.
  * @param isSupport         True, if the user is Telegram support account.
  * @param restrictionReason If non-empty, it contains a human-readable description of the reason why access to this user must be restricted.
  * @param isScam            True, if many users reported this user as a scam.
  * @param haveAccess        If false, the user is inaccessible, and the only information known about the user is inside this class. It can't be passed to any method except GetUser.
  * @param type              Type of the user.
  * @param languageCode      IETF language tag of the user's language; only available to bots.
  */
final case class User(
  id: Int,
  firstName: String,
  lastName: String,
  username: String,
  phoneNumber: String,
  status: UserStatus,
  profilePhoto: Option[ProfilePhoto],
  isContact: Boolean,
  isMutualContact: Boolean,
  isVerified: Boolean,
  isSupport: Boolean,
  restrictionReason: String,
  isScam: Boolean,
  haveAccess: Boolean,
  `type`: UserType,
  languageCode: String
)

private[api] object User {
  def fromJava(o: TdApi.User): User =
    User(
      o.id,
      o.firstName,
      o.lastName,
      o.username,
      o.phoneNumber,
      UserStatus.fromJava(o.status),
      Option(o.profilePhoto).map(ProfilePhoto.fromJava),
      o.isContact,
      o.isMutualContact,
      o.isVerified,
      o.isSupport,
      o.restrictionReason,
      o.isScam,
      o.haveAccess,
      UserType.fromJava(o.`type`),
      o.languageCode
    )
}
