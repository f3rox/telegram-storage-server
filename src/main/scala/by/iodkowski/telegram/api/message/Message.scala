package by.iodkowski.telegram.api.message

import org.drinkless.tdlib.TdApi

/**
  * Describes a message.
  *
  * @param id                      Message identifier, unique for the chat to which the message belongs.
  * @param senderUserId            Identifier of the user who sent the message; 0 if unknown. Currently, it is unknown for channel posts and for channel posts automatically forwarded to discussion group.
  * @param chatId                  Chat identifier.
  * @param sendingState            Information about the sending state of the message; may be None.
  * @param schedulingState         Information about the scheduling state of the message; may be None.
  * @param isOutgoing              True, if the message is outgoing.
  * @param canBeEdited             True, if the message can be edited. For live location and poll messages this fields shows whether editMessageLiveLocation or stopPoll can be used with this message by the client.
  * @param canBeForwarded          True, if the message can be forwarded.
  * @param canBeDeletedOnlyForSelf True, if the message can be deleted only for the current user while other users will continue to see it.
  * @param canBeDeletedForAllUsers True, if the message can be deleted for all users.
  * @param isChannelPost           True, if the message is a channel post. All messages to channels are channel posts, all other messages are not channel posts.
  * @param containsUnreadMention   True, if the message contains an unread mention for the current user.
  * @param date                    Point in time (Unix timestamp) when the message was sent.
  * @param editDate                Point in time (Unix timestamp) when the message was last edited.
  * @param forwardInfo             Information about the initial message sender; may be None.
  * @param replyToMessageId        If non-zero, the identifier of the message this message is replying to; can be the identifier of a deleted message.
  * @param ttl                     For self-destructing messages, the message's TTL (Time To Live), in seconds; 0 if none. TDLib will send updateDeleteMessages or updateMessageContent once the TTL expires.
  * @param ttlExpiresIn            Time left before the message expires, in seconds.
  * @param viaBotUserId            If non-zero, the user identifier of the bot through which this message was sent.
  * @param authorSignature         For channel posts, optional author signature.
  * @param views                   Number of times this message was viewed.
  * @param mediaAlbumId            Unique identifier of an album this message belongs to. Only photos and videos can be grouped together in albums.
  * @param restrictionReason       If non-empty, contains a human-readable description of the reason why access to this message must be restricted.
  * @param content                 Content of the message.
  * @param replyMarkup             Reply markup for the message; may be None.
  */
final case class Message(
  id: Long,
  senderUserId: Int,
  chatId: Long,
  sendingState: Option[MessageSendingState],
  schedulingState: Option[MessageSchedulingState],
  isOutgoing: Boolean,
  canBeEdited: Boolean,
  canBeForwarded: Boolean,
  canBeDeletedOnlyForSelf: Boolean,
  canBeDeletedForAllUsers: Boolean,
  isChannelPost: Boolean,
  containsUnreadMention: Boolean,
  date: Int,
  editDate: Int,
  forwardInfo: Option[MessageForwardInfo],
  replyToMessageId: Long,
  ttl: Int,
  ttlExpiresIn: Double,
  viaBotUserId: Int,
  authorSignature: String,
  views: Int,
  mediaAlbumId: Long,
  restrictionReason: String,
  content: MessageContent,
  replyMarkup: Option[ReplyMarkup]
)

private[api] object Message {
  def fromJava(o: TdApi.Message): Message =
    Message(
      o.id,
      o.senderUserId,
      o.chatId,
      Option(o.sendingState).map(MessageSendingState.fromJava),
      Option(o.schedulingState).map(MessageSchedulingState.fromJava),
      o.isOutgoing,
      o.canBeEdited,
      o.canBeForwarded,
      o.canBeDeletedOnlyForSelf,
      o.canBeDeletedForAllUsers,
      o.isChannelPost,
      o.containsUnreadMention,
      o.date,
      o.editDate,
      Option(o.forwardInfo).map(MessageForwardInfo.fromJava),
      o.replyToMessageId,
      o.ttl,
      o.ttlExpiresIn,
      o.viaBotUserId,
      o.authorSignature,
      o.views,
      o.mediaAlbumId,
      o.restrictionReason,
      MessageContent.fromJava(o.content),
      Option(o.replyMarkup).map(ReplyMarkup.fromJava)
    )
}
