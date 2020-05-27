package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.message.{DraftMessage, Message}
import by.iodkowski.telegram.api.notification.ChatNotificationSettings

/**
  * A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
  *
  * @param id                         Chat unique identifier.
  * @param type                       Type of the chat.
  * @param chatList                   A chat list to which the chat belongs; may be None.
  * @param title                      Chat title.
  * @param photo                      Chat photo; may be None.
  * @param permissions                Actions that non-administrator chat members are allowed to take in the chat.
  * @param lastMessage                Last message in the chat; may be None.
  * @param order                      Descending parameter by which chats are sorted in the main chat list. If the order number of two chats is the same, they must be sorted in descending order by ID. If 0, the position of the chat in the list is undetermined.
  * @param isPinned                   True, if the chat is pinned.
  * @param isMarkedAsUnread           True, if the chat is marked as unread.
  * @param isSponsored                True, if the chat is sponsored by the user's MTProxy server.
  * @param hasScheduledMessages       True, if the chat has scheduled messages.
  * @param canBeDeletedOnlyForSelf    True, if the chat messages can be deleted only for the current user while other users will continue to see the messages.
  * @param canBeDeletedForAllUsers    True, if the chat messages can be deleted for all users.
  * @param canBeReported              True, if the chat can be reported to Telegram moderators through reportChat.
  * @param defaultDisableNotification Default value of the disableNotification parameter, used when a message is sent to the chat.
  * @param unreadCount                Number of unread messages in the chat.
  * @param lastReadInboxMessageId     Identifier of the last read incoming message.
  * @param lastReadOutboxMessageId    Identifier of the last read outgoing message.
  * @param unreadMentionCount         Number of unread messages with a mention/reply in the chat.
  * @param notificationSettings       Notification settings for this chat.
  * @param actionBar                  Describes actions which should be possible to do through a chat action bar; may be None.
  * @param pinnedMessageId            Identifier of the pinned message in the chat; 0 if none.
  * @param replyMarkupMessageId       Identifier of the message from which reply markup needs to be used; 0 if there is no default custom reply markup in the chat.
  * @param draftMessage               A draft of a message in the chat; may be None.
  * @param clientData                 Contains client-specific data associated with the chat. (For example, the chat position or local chat notification settings can be stored here.) Persistent if the message database is used.
  */
final case class Chat(
  id: Long,
  `type`: ChatType,
  chatList: Option[ChatList],
  title: String,
  photo: Option[ChatPhoto],
  permissions: ChatPermissions,
  lastMessage: Option[Message],
  order: Long,
  isPinned: Boolean,
  isMarkedAsUnread: Boolean,
  isSponsored: Boolean,
  hasScheduledMessages: Boolean,
  canBeDeletedOnlyForSelf: Boolean,
  canBeDeletedForAllUsers: Boolean,
  canBeReported: Boolean,
  defaultDisableNotification: Boolean,
  unreadCount: Int,
  lastReadInboxMessageId: Long,
  lastReadOutboxMessageId: Long,
  unreadMentionCount: Int,
  notificationSettings: ChatNotificationSettings,
  actionBar: Option[ChatActionBar],
  pinnedMessageId: Long,
  replyMarkupMessageId: Long,
  draftMessage: Option[DraftMessage],
  clientData: String
)

private[api] object Chat {
  def fromJava(o: TdApi.Chat): Chat =
    Chat(
      o.id,
      ChatType.fromJava(o.`type`),
      Option(o.chatList).map(ChatList.fromJava),
      o.title,
      Option(o.photo).map(ChatPhoto.fromJava),
      ChatPermissions.fromJava(o.permissions),
      Option(o.lastMessage).map(Message.fromJava),
      o.order,
      o.isPinned,
      o.isMarkedAsUnread,
      o.isSponsored,
      o.hasScheduledMessages,
      o.canBeDeletedOnlyForSelf,
      o.canBeDeletedForAllUsers,
      o.canBeReported,
      o.defaultDisableNotification,
      o.unreadCount,
      o.lastReadInboxMessageId,
      o.lastReadOutboxMessageId,
      o.unreadMentionCount,
      ChatNotificationSettings.fromJava(o.notificationSettings),
      Option(o.actionBar).map(ChatActionBar.fromJava),
      o.pinnedMessageId,
      o.replyMarkupMessageId,
      Option(o.draftMessage).map(DraftMessage.fromJava),
      o.clientData
    )
}
