package by.iodkowski.telegram.api.group

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.chat.ChatMemberStatus

/**
  * Represents a supergroup or channel with zero or more members (subscribers in the case of channels). From the point of view of the system, a channel is a special kind of a supergroup: only administrators can post and see the list of members, and posts from all administrators use the name and photo of the channel instead of individual names and profile photos. Unlike supergroups, channels can have an unlimited number of subscribers.
  *
  * @param id                Supergroup or channel identifier.
  * @param username          Username of the supergroup or channel; empty for private supergroups or channels.
  * @param date              Point in time (Unix timestamp) when the current user joined, or the point in time when the supergroup or channel was created, in case the user is not a member.
  * @param status            Status of the current user in the supergroup or channel; custom title will be always empty.
  * @param memberCount       Member count; 0 if unknown. Currently it is guaranteed to be known only if the supergroup or channel was found through SearchPublicChats.
  * @param hasLinkedChat     True, if the channel has a discussion group, or the supergroup is the designated discussion group for a channel.
  * @param hasLocation       True, if the supergroup is connected to a location, i.e. the supergroup is a location-based supergroup.
  * @param signMessages      True, if messages sent to the channel should contain information about the sender. This field is only applicable to channels.
  * @param isSlowModeEnabled True, if the slow mode is enabled in the supergroup.
  * @param isChannel         True, if the supergroup is a channel.
  * @param isVerified        True, if the supergroup or channel is verified.
  * @param restrictionReason If non-empty, contains a human-readable description of the reason why access to this supergroup or channel must be restricted.
  * @param isScam            True, if many users reported this supergroup as a scam.
  */
final case class Supergroup(
  id: Int,
  username: String,
  date: Int,
  status: ChatMemberStatus,
  memberCount: Int,
  hasLinkedChat: Boolean,
  hasLocation: Boolean,
  signMessages: Boolean,
  isSlowModeEnabled: Boolean,
  isChannel: Boolean,
  isVerified: Boolean,
  restrictionReason: String,
  isScam: Boolean
)

private[api] object Supergroup {
  def fromJava(o: TdApi.Supergroup): Supergroup =
    Supergroup(
      o.id,
      o.username,
      o.date,
      ChatMemberStatus.fromJava(o.status),
      o.memberCount,
      o.hasLinkedChat,
      o.hasLocation,
      o.signMessages,
      o.isSlowModeEnabled,
      o.isChannel,
      o.isVerified,
      o.restrictionReason,
      o.isScam
    )
}
