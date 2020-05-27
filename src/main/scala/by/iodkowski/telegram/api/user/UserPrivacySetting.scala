package by.iodkowski.telegram.api.user

import org.drinkless.tdlib.TdApi

/**
  * Describes available user privacy settings.
  */
sealed abstract class UserPrivacySetting extends Product with Serializable

/**
  * A privacy setting for managing whether the user's online status is visible.
  */
case object UserPrivacySettingShowStatus extends UserPrivacySetting

/**
  * A privacy setting for managing whether the user's profile photo is visible.
  */
case object UserPrivacySettingShowProfilePhoto extends UserPrivacySetting

/**
  * A privacy setting for managing whether a link to the user's account is included in forwarded messages.
  */
case object UserPrivacySettingShowLinkInForwardedMessages extends UserPrivacySetting

/**
  * A privacy setting for managing whether the user's phone number is visible.
  */
case object UserPrivacySettingShowPhoneNumber extends UserPrivacySetting

/**
  * A privacy setting for managing whether the user can be invited to chats.
  */
case object UserPrivacySettingAllowChatInvites extends UserPrivacySetting

/**
  * A privacy setting for managing whether the user can be called.
  */
case object UserPrivacySettingAllowCalls extends UserPrivacySetting

/**
  * A privacy setting for managing whether peer-to-peer connections can be used for calls.
  */
case object UserPrivacySettingAllowPeerToPeerCalls extends UserPrivacySetting

/**
  * A privacy setting for managing whether the user can be found by their phone number. Checked only if the phone number is not known to the other user. Can be set only to &quot;Allow contacts&quot; or &quot;Allow all&quot;.
  */
case object UserPrivacySettingAllowFindingByPhoneNumber extends UserPrivacySetting

private[api] object UserPrivacySetting {
  def fromJava(o: TdApi.UserPrivacySetting): UserPrivacySetting = o.getConstructor match {
    case TdApi.UserPrivacySettingShowStatus.CONSTRUCTOR                => UserPrivacySettingShowStatus
    case TdApi.UserPrivacySettingShowProfilePhoto.CONSTRUCTOR          => UserPrivacySettingShowProfilePhoto
    case TdApi.UserPrivacySettingShowPhoneNumber.CONSTRUCTOR           => UserPrivacySettingShowPhoneNumber
    case TdApi.UserPrivacySettingAllowChatInvites.CONSTRUCTOR          => UserPrivacySettingAllowChatInvites
    case TdApi.UserPrivacySettingAllowCalls.CONSTRUCTOR                => UserPrivacySettingAllowCalls
    case TdApi.UserPrivacySettingAllowPeerToPeerCalls.CONSTRUCTOR      => UserPrivacySettingAllowPeerToPeerCalls
    case TdApi.UserPrivacySettingAllowFindingByPhoneNumber.CONSTRUCTOR => UserPrivacySettingAllowFindingByPhoneNumber
    case TdApi.UserPrivacySettingShowLinkInForwardedMessages.CONSTRUCTOR =>
      UserPrivacySettingShowLinkInForwardedMessages
  }
}
