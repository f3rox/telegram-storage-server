package by.iodkowski.telegram.api.group

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.chat.ChatLocation

/**
  * Contains full information about a supergroup or channel.
  *
  * @param description              Supergroup or channel description.
  * @param memberCount              Number of members in the supergroup or channel; 0 if unknown.
  * @param administratorCount       Number of privileged users in the supergroup or channel; 0 if unknown.
  * @param restrictedCount          Number of restricted users in the supergroup; 0 if unknown.
  * @param bannedCount              Number of users banned from chat; 0 if unknown.
  * @param linkedChatId             Chat identifier of a discussion group for the channel, or a channel, for which the supergroup is the designated discussion group; 0 if none or unknown.
  * @param slowModeDelay            Delay between consecutive sent messages for non-administrator supergroup members, in seconds.
  * @param slowModeDelayExpiresIn   Time left before next message can be sent in the supergroup, in seconds. An updateSupergroupFullInfo update is not triggered when value of this field changes, but both new and old values are non-zero.
  * @param canGetMembers            True, if members of the chat can be retrieved.
  * @param canSetUsername           True, if the chat username can be changed.
  * @param canSetStickerSet         True, if the supergroup sticker set can be changed.
  * @param canSetLocation           True, if the supergroup location can be changed.
  * @param canViewStatistics        True, if the channel statistics is available through getChatStatisticsUrl.
  * @param isAllHistoryAvailable    True, if new chat members will have access to old messages. In public or discussion groups and both public and private channels, old messages are always available, so this option affects only private supergroups without a linked chat. The value of this field is only available for chat administrators.
  * @param stickerSetId             Identifier of the supergroup sticker set; 0 if none.
  * @param location                 Location to which the supergroup is connected; may be None.
  * @param inviteLink               Invite link for this chat.
  * @param upgradedFromBasicGroupId Identifier of the basic group from which supergroup was upgraded; 0 if none.
  * @param upgradedFromMaxMessageId Identifier of the last message in the basic group from which supergroup was upgraded; 0 if none.
  */
final case class SupergroupFullInfo(
  description: String,
  memberCount: Int,
  administratorCount: Int,
  restrictedCount: Int,
  bannedCount: Int,
  linkedChatId: Long,
  slowModeDelay: Int,
  slowModeDelayExpiresIn: Double,
  canGetMembers: Boolean,
  canSetUsername: Boolean,
  canSetStickerSet: Boolean,
  canSetLocation: Boolean,
  canViewStatistics: Boolean,
  isAllHistoryAvailable: Boolean,
  stickerSetId: Long,
  location: Option[ChatLocation],
  inviteLink: String,
  upgradedFromBasicGroupId: Int,
  upgradedFromMaxMessageId: Long
)

private[api] object SupergroupFullInfo {
  def fromJava(o: TdApi.SupergroupFullInfo): SupergroupFullInfo =
    SupergroupFullInfo(
      o.description,
      o.memberCount,
      o.administratorCount,
      o.restrictedCount,
      o.bannedCount,
      o.linkedChatId,
      o.slowModeDelay,
      o.slowModeDelayExpiresIn,
      o.canGetMembers,
      o.canSetUsername,
      o.canSetStickerSet,
      o.canSetLocation,
      o.canViewStatistics,
      o.isAllHistoryAvailable,
      o.stickerSetId,
      Option(o.location).map(ChatLocation.fromJava),
      o.inviteLink,
      o.upgradedFromBasicGroupId,
      o.upgradedFromMaxMessageId
    )
}
