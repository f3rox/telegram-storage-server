package by.iodkowski.telegram.api.user

import org.drinkless.tdlib.TdApi

/**
  * Contains full information about a user (except the full list of profile photos).
  *
  * @param isBlocked                       True, if the user is blacklisted by the current user.
  * @param canBeCalled                     True, if the user can be called.
  * @param hasPrivateCalls                 True, if the user can't be called due to their privacy settings.
  * @param needPhoneNumberPrivacyException True, if the current user needs to explicitly allow to share their phone number with the user when the method addContact is used.
  * @param bio                             A short user bio.
  * @param shareText                       For bots, the text that is included with the link when users share the bot.
  * @param groupInCommonCount              Number of group chats where both the other user and the current user are a member; 0 for the current user.
  * @param botInfo                         If the user is a bot, information about the bot; may be None.
  */
final case class UserFullInfo(
  isBlocked: Boolean,
  canBeCalled: Boolean,
  hasPrivateCalls: Boolean,
  needPhoneNumberPrivacyException: Boolean,
  bio: String,
  shareText: String,
  groupInCommonCount: Int,
  botInfo: Option[BotInfo]
)

private[api] object UserFullInfo {
  def fromJava(o: TdApi.UserFullInfo): UserFullInfo =
    UserFullInfo(
      o.isBlocked,
      o.canBeCalled,
      o.hasPrivateCalls,
      o.needPhoneNumberPrivacyException,
      o.bio,
      o.shareText,
      o.groupInCommonCount,
      Option(o.botInfo).map(BotInfo.fromJava)
    )
}
