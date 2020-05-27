package by.iodkowski.telegram.api

import by.iodkowski.telegram.api.auth.AuthorizationState
import by.iodkowski.telegram.api.call.Call
import by.iodkowski.telegram.api.chat._
import by.iodkowski.telegram.api.file.File
import by.iodkowski.telegram.api.file.background.Background
import by.iodkowski.telegram.api.file.sticker.StickerSets
import by.iodkowski.telegram.api.group._
import by.iodkowski.telegram.api.language.LanguagePackString
import by.iodkowski.telegram.api.message._
import by.iodkowski.telegram.api.notification._
import by.iodkowski.telegram.api.poll.Poll
import by.iodkowski.telegram.api.user._
import org.drinkless.tdlib.TdApi

/**
  * Contains notifications about data changes.
  */
sealed abstract class Update extends Product with Serializable

/**
  * The user authorization state has changed.
  *
  * @param authorizationState New authorization state.
  */
final case class UpdateAuthorizationState(authorizationState: AuthorizationState) extends Update

private[api] object UpdateAuthorizationState {
  def fromJava(o: TdApi.UpdateAuthorizationState): UpdateAuthorizationState =
    UpdateAuthorizationState(AuthorizationState.fromJava(o.authorizationState))
}

/**
  * A new message was received; can also be an outgoing message.
  *
  * @param message The new message.
  */
final case class UpdateNewMessage(message: Message) extends Update

private[api] object UpdateNewMessage {
  def fromJava(o: TdApi.UpdateNewMessage): UpdateNewMessage = UpdateNewMessage(Message.fromJava(o.message))
}

/**
  * A request to send a message has reached the Telegram server. This doesn't mean that the message will be sent successfully or even that the send message request will be processed. This update will be sent only if the option &quot;use_quick_ack&quot; is set to true. This update may be sent multiple times for the same message.
  *
  * @param chatId    The chat identifier of the sent message.
  * @param messageId A temporary message identifier.
  */
final case class UpdateMessageSendAcknowledged(chatId: Long, messageId: Long) extends Update

private[api] object UpdateMessageSendAcknowledged {
  def fromJava(o: TdApi.UpdateMessageSendAcknowledged): UpdateMessageSendAcknowledged =
    UpdateMessageSendAcknowledged(o.chatId, o.messageId)
}

/**
  * A message has been successfully sent.
  *
  * @param message      Information about the sent message. Usually only the message identifier, date, and content are changed, but almost all other fields can also change.
  * @param oldMessageId The previous temporary message identifier.
  */
final case class UpdateMessageSendSucceeded(message: Message, oldMessageId: Long) extends Update

private[api] object UpdateMessageSendSucceeded {
  def fromJava(o: TdApi.UpdateMessageSendSucceeded): UpdateMessageSendSucceeded =
    UpdateMessageSendSucceeded(Message.fromJava(o.message), o.oldMessageId)
}

/**
  * A message failed to send. Be aware that some messages being sent can be irrecoverably deleted, in which case updateDeleteMessages will be received instead of this update.
  *
  * @param message      Contains information about the message which failed to send.
  * @param oldMessageId The previous temporary message identifier.
  * @param errorCode    An error code.
  * @param errorMessage Error message.
  */
final case class UpdateMessageSendFailed(message: Message, oldMessageId: Long, errorCode: Int, errorMessage: String)
    extends Update

private[api] object UpdateMessageSendFailed {
  def fromJava(o: TdApi.UpdateMessageSendFailed): UpdateMessageSendFailed =
    UpdateMessageSendFailed(Message.fromJava(o.message), o.oldMessageId, o.errorCode, o.errorMessage)
}

/**
  * The message content has changed.
  *
  * @param chatId     Chat identifier.
  * @param messageId  Message identifier.
  * @param newContent New message content.
  */
final case class UpdateMessageContent(chatId: Long, messageId: Long, newContent: MessageContent) extends Update

private[api] object UpdateMessageContent {
  def fromJava(o: TdApi.UpdateMessageContent): UpdateMessageContent =
    UpdateMessageContent(o.chatId, o.messageId, MessageContent.fromJava(o.newContent))
}

/**
  * A message was edited. Changes in the message content will come in a separate updateMessageContent.
  *
  * @param chatId      Chat identifier.
  * @param messageId   Message identifier.
  * @param editDate    Point in time (Unix timestamp) when the message was edited.
  * @param replyMarkup New message reply markup; may be None.
  */
final case class UpdateMessageEdited(chatId: Long, messageId: Long, editDate: Int, replyMarkup: Option[ReplyMarkup])
    extends Update

private[api] object UpdateMessageEdited {
  def fromJava(o: TdApi.UpdateMessageEdited): UpdateMessageEdited =
    UpdateMessageEdited(o.chatId, o.messageId, o.editDate, Option(o.replyMarkup).map(ReplyMarkup.fromJava))
}

/**
  * The view count of the message has changed.
  *
  * @param chatId    Chat identifier.
  * @param messageId Message identifier.
  * @param views     New value of the view count.
  */
final case class UpdateMessageViews(chatId: Long, messageId: Long, views: Int) extends Update

private[api] object UpdateMessageViews {
  def fromJava(o: TdApi.UpdateMessageViews): UpdateMessageViews = UpdateMessageViews(o.chatId, o.messageId, o.views)
}

/**
  * The message content was opened. Updates voice note messages to &quot;listened&quot;, video note messages to &quot;viewed&quot; and starts the TTL timer for self-destructing messages.
  *
  * @param chatId    Chat identifier.
  * @param messageId Message identifier.
  */
final case class UpdateMessageContentOpened(chatId: Long, messageId: Long) extends Update

private[api] object UpdateMessageContentOpened {
  def fromJava(o: TdApi.UpdateMessageContentOpened): UpdateMessageContentOpened =
    UpdateMessageContentOpened(o.chatId, o.messageId)
}

/**
  * A message with an unread mention was read.
  *
  * @param chatId             Chat identifier.
  * @param messageId          Message identifier.
  * @param unreadMentionCount The new number of unread mention messages left in the chat.
  */
final case class UpdateMessageMentionRead(chatId: Long, messageId: Long, unreadMentionCount: Int) extends Update

private[api] object UpdateMessageMentionRead {
  def fromJava(o: TdApi.UpdateMessageMentionRead): UpdateMessageMentionRead =
    UpdateMessageMentionRead(o.chatId, o.messageId, o.unreadMentionCount)
}

/**
  * A message with a live location was viewed. When the update is received, the client is supposed to update the live location.
  *
  * @param chatId    Identifier of the chat with the live location message.
  * @param messageId Identifier of the message with live location.
  */
final case class UpdateMessageLiveLocationViewed(chatId: Long, messageId: Long) extends Update

private[api] object UpdateMessageLiveLocationViewed {
  def fromJava(o: TdApi.UpdateMessageLiveLocationViewed): UpdateMessageLiveLocationViewed =
    UpdateMessageLiveLocationViewed(o.chatId, o.messageId)
}

/**
  * A new chat has been loaded/created. This update is guaranteed to come before the chat identifier is returned to the client. The chat field changes will be reported through separate updates.
  *
  * @param chat The chat.
  */
final case class UpdateNewChat(chat: Chat) extends Update

private[api] object UpdateNewChat {
  def fromJava(o: TdApi.UpdateNewChat): UpdateNewChat = UpdateNewChat(Chat.fromJava(o.chat))
}

/**
  * The list to which the chat belongs was changed. This update is guaranteed to be sent only when chat.order == 0 and the current or the new chat list is null.
  *
  * @param chatId   Chat identifier.
  * @param chatList The new chat's chat list; may be None.
  */
final case class UpdateChatChatList(chatId: Long, chatList: Option[ChatList]) extends Update

private[api] object UpdateChatChatList {
  def fromJava(o: TdApi.UpdateChatChatList): UpdateChatChatList =
    UpdateChatChatList(o.chatId, Option(o.chatList).map(ChatList.fromJava))
}

/**
  * The title of a chat was changed.
  *
  * @param chatId Chat identifier.
  * @param title  The new chat title.
  */
final case class UpdateChatTitle(chatId: Long, title: String) extends Update

private[api] object UpdateChatTitle {
  def fromJava(o: TdApi.UpdateChatTitle): UpdateChatTitle = UpdateChatTitle(o.chatId, o.title)
}

/**
  * A chat photo was changed.
  *
  * @param chatId Chat identifier.
  * @param photo  The new chat photo; may be None.
  */
final case class UpdateChatPhoto(chatId: Long, photo: Option[ChatPhoto]) extends Update

private[api] object UpdateChatPhoto {
  def fromJava(o: TdApi.UpdateChatPhoto): UpdateChatPhoto =
    UpdateChatPhoto(o.chatId, Option(o.photo).map(ChatPhoto.fromJava))
}

/**
  * Chat permissions was changed.
  *
  * @param chatId      Chat identifier.
  * @param permissions The new chat permissions.
  */
final case class UpdateChatPermissions(chatId: Long, permissions: ChatPermissions) extends Update

private[api] object UpdateChatPermissions {
  def fromJava(o: TdApi.UpdateChatPermissions): UpdateChatPermissions =
    UpdateChatPermissions(o.chatId, ChatPermissions.fromJava(o.permissions))
}

/**
  * The last message of a chat was changed. If lastMessage is null, then the last message in the chat became unknown. Some new unknown messages might be added to the chat in this case.
  *
  * @param chatId      Chat identifier.
  * @param lastMessage The new last message in the chat; may be None.
  * @param order       New value of the chat order.
  */
final case class UpdateChatLastMessage(chatId: Long, lastMessage: Option[Message], order: Long) extends Update

private[api] object UpdateChatLastMessage {
  def fromJava(o: TdApi.UpdateChatLastMessage): UpdateChatLastMessage =
    UpdateChatLastMessage(o.chatId, Option(o.lastMessage).map(Message.fromJava), o.order)
}

/**
  * The order of the chat in the chat list has changed. Instead of this update updateChatLastMessage, updateChatIsPinned, updateChatDraftMessage, or updateChatIsSponsored might be sent.
  *
  * @param chatId Chat identifier.
  * @param order  New value of the order.
  */
final case class UpdateChatOrder(chatId: Long, order: Long) extends Update

private[api] object UpdateChatOrder {
  def fromJava(o: TdApi.UpdateChatOrder): UpdateChatOrder = UpdateChatOrder(o.chatId, o.order)
}

/**
  * A chat was pinned or unpinned.
  *
  * @param chatId   Chat identifier.
  * @param isPinned New value of isPinned.
  * @param order    New value of the chat order.
  */
final case class UpdateChatIsPinned(chatId: Long, isPinned: Boolean, order: Long) extends Update

private[api] object UpdateChatIsPinned {
  def fromJava(o: TdApi.UpdateChatIsPinned): UpdateChatIsPinned = UpdateChatIsPinned(o.chatId, o.isPinned, o.order)
}

/**
  * A chat was marked as unread or was read.
  *
  * @param chatId           Chat identifier.
  * @param isMarkedAsUnread New value of isMarkedAsUnread.
  */
final case class UpdateChatIsMarkedAsUnread(chatId: Long, isMarkedAsUnread: Boolean) extends Update

private[api] object UpdateChatIsMarkedAsUnread {
  def fromJava(o: TdApi.UpdateChatIsMarkedAsUnread): UpdateChatIsMarkedAsUnread =
    UpdateChatIsMarkedAsUnread(o.chatId, o.isMarkedAsUnread)
}

/**
  * A chat's isSponsored field has changed.
  *
  * @param chatId      Chat identifier.
  * @param isSponsored New value of isSponsored.
  * @param order       New value of chat order.
  */
final case class UpdateChatIsSponsored(chatId: Long, isSponsored: Boolean, order: Long) extends Update

private[api] object UpdateChatIsSponsored {
  def fromJava(o: TdApi.UpdateChatIsSponsored): UpdateChatIsSponsored =
    UpdateChatIsSponsored(o.chatId, o.isSponsored, o.order)
}

/**
  * A chat's hasScheduledMessages field has changed.
  *
  * @param chatId               Chat identifier.
  * @param hasScheduledMessages New value of hasScheduledMessages.
  */
final case class UpdateChatHasScheduledMessages(chatId: Long, hasScheduledMessages: Boolean) extends Update

private[api] object UpdateChatHasScheduledMessages {
  def fromJava(o: TdApi.UpdateChatHasScheduledMessages): UpdateChatHasScheduledMessages =
    UpdateChatHasScheduledMessages(o.chatId, o.hasScheduledMessages)
}

/**
  * The value of the default disableNotification parameter, used when a message is sent to the chat, was changed.
  *
  * @param chatId                     Chat identifier.
  * @param defaultDisableNotification The new defaultDisableNotification value.
  */
final case class UpdateChatDefaultDisableNotification(chatId: Long, defaultDisableNotification: Boolean) extends Update

private[api] object UpdateChatDefaultDisableNotification {
  def fromJava(o: TdApi.UpdateChatDefaultDisableNotification): UpdateChatDefaultDisableNotification =
    UpdateChatDefaultDisableNotification(o.chatId, o.defaultDisableNotification)
}

/**
  * Incoming messages were read or number of unread messages has been changed.
  *
  * @param chatId                 Chat identifier.
  * @param lastReadInboxMessageId Identifier of the last read incoming message.
  * @param unreadCount            The number of unread messages left in the chat.
  */
final case class UpdateChatReadInbox(chatId: Long, lastReadInboxMessageId: Long, unreadCount: Int) extends Update

private[api] object UpdateChatReadInbox {
  def fromJava(o: TdApi.UpdateChatReadInbox): UpdateChatReadInbox =
    UpdateChatReadInbox(o.chatId, o.lastReadInboxMessageId, o.unreadCount)
}

/**
  * Outgoing messages were read.
  *
  * @param chatId                  Chat identifier.
  * @param lastReadOutboxMessageId Identifier of last read outgoing message.
  */
final case class UpdateChatReadOutbox(chatId: Long, lastReadOutboxMessageId: Long) extends Update

private[api] object UpdateChatReadOutbox {
  def fromJava(o: TdApi.UpdateChatReadOutbox): UpdateChatReadOutbox =
    UpdateChatReadOutbox(o.chatId, o.lastReadOutboxMessageId)
}

/**
  * The chat unreadMentionCount has changed.
  *
  * @param chatId             Chat identifier.
  * @param unreadMentionCount The number of unread mention messages left in the chat.
  */
final case class UpdateChatUnreadMentionCount(chatId: Long, unreadMentionCount: Int) extends Update

private[api] object UpdateChatUnreadMentionCount {
  def fromJava(o: TdApi.UpdateChatUnreadMentionCount): UpdateChatUnreadMentionCount =
    UpdateChatUnreadMentionCount(o.chatId, o.unreadMentionCount)
}

/**
  * Notification settings for a chat were changed.
  *
  * @param chatId               Chat identifier.
  * @param notificationSettings The new notification settings.
  */
final case class UpdateChatNotificationSettings(chatId: Long, notificationSettings: ChatNotificationSettings)
    extends Update

private[api] object UpdateChatNotificationSettings {
  def fromJava(o: TdApi.UpdateChatNotificationSettings): UpdateChatNotificationSettings =
    UpdateChatNotificationSettings(o.chatId, ChatNotificationSettings.fromJava(o.notificationSettings))
}

/**
  * Notification settings for some type of chats were updated.
  *
  * @param scope                Types of chats for which notification settings were updated.
  * @param notificationSettings The new notification settings.
  */
final case class UpdateScopeNotificationSettings(
  scope: NotificationSettingsScope,
  notificationSettings: ScopeNotificationSettings
) extends Update

private[api] object UpdateScopeNotificationSettings {
  def fromJava(o: TdApi.UpdateScopeNotificationSettings): UpdateScopeNotificationSettings =
    UpdateScopeNotificationSettings(
      NotificationSettingsScope.fromJava(o.scope),
      ScopeNotificationSettings.fromJava(o.notificationSettings)
    )
}

/**
  * The chat action bar was changed.
  *
  * @param chatId    Chat identifier.
  * @param actionBar The new value of the action bar; may be None.
  */
final case class UpdateChatActionBar(chatId: Long, actionBar: Option[ChatActionBar]) extends Update

private[api] object UpdateChatActionBar {
  def fromJava(o: TdApi.UpdateChatActionBar): UpdateChatActionBar =
    UpdateChatActionBar(o.chatId, Option(o.actionBar).map(ChatActionBar.fromJava))
}

/**
  * The chat pinned message was changed.
  *
  * @param chatId          Chat identifier.
  * @param pinnedMessageId The new identifier of the pinned message; 0 if there is no pinned message in the chat.
  */
final case class UpdateChatPinnedMessage(chatId: Long, pinnedMessageId: Long) extends Update

private[api] object UpdateChatPinnedMessage {
  def fromJava(o: TdApi.UpdateChatPinnedMessage): UpdateChatPinnedMessage =
    UpdateChatPinnedMessage(o.chatId, o.pinnedMessageId)
}

/**
  * The default chat reply markup was changed. Can occur because new messages with reply markup were received or because an old reply markup was hidden by the user.
  *
  * @param chatId               Chat identifier.
  * @param replyMarkupMessageId Identifier of the message from which reply markup needs to be used; 0 if there is no default custom reply markup in the chat.
  */
final case class UpdateChatReplyMarkup(chatId: Long, replyMarkupMessageId: Long) extends Update

private[api] object UpdateChatReplyMarkup {
  def fromJava(o: TdApi.UpdateChatReplyMarkup): UpdateChatReplyMarkup =
    UpdateChatReplyMarkup(o.chatId, o.replyMarkupMessageId)
}

/**
  * A chat draft has changed. Be aware that the update may come in the currently opened chat but with old content of the draft. If the user has changed the content of the draft, this update shouldn't be applied.
  *
  * @param chatId       Chat identifier.
  * @param draftMessage The new draft message; may be None.
  * @param order        New value of the chat order.
  */
final case class UpdateChatDraftMessage(chatId: Long, draftMessage: Option[DraftMessage], order: Long) extends Update

private[api] object UpdateChatDraftMessage {
  def fromJava(o: TdApi.UpdateChatDraftMessage): UpdateChatDraftMessage =
    UpdateChatDraftMessage(o.chatId, Option(o.draftMessage).map(DraftMessage.fromJava), o.order)
}

/**
  * The number of online group members has changed. This update with non-zero count is sent only for currently opened chats. There is no guarantee that it will be sent just after the count has changed.
  *
  * @param chatId            Identifier of the chat.
  * @param onlineMemberCount New number of online members in the chat, or 0 if unknown.
  */
final case class UpdateChatOnlineMemberCount(chatId: Long, onlineMemberCount: Int) extends Update

private[api] object UpdateChatOnlineMemberCount {
  def fromJava(o: TdApi.UpdateChatOnlineMemberCount): UpdateChatOnlineMemberCount =
    UpdateChatOnlineMemberCount(o.chatId, o.onlineMemberCount)
}

/**
  * A notification was changed.
  *
  * @param notificationGroupId Unique notification group identifier.
  * @param notification        Changed notification.
  */
final case class UpdateNotification(notificationGroupId: Int, notification: Notification) extends Update

private[api] object UpdateNotification {
  def fromJava(o: TdApi.UpdateNotification): UpdateNotification =
    UpdateNotification(o.notificationGroupId, Notification.fromJava(o.notification))
}

/**
  * A list of active notifications in a notification group has changed.
  *
  * @param notificationGroupId        Unique notification group identifier.
  * @param type                       New type of the notification group.
  * @param chatId                     Identifier of a chat to which all notifications in the group belong.
  * @param notificationSettingsChatId Chat identifier, which notification settings must be applied to the added notifications.
  * @param isSilent                   True, if the notifications should be shown without sound.
  * @param totalCount                 Total number of unread notifications in the group, can be bigger than number of active notifications.
  * @param addedNotifications         List of added group notifications, sorted by notification ID.
  * @param removedNotificationIds     Identifiers of removed group notifications, sorted by notification ID.
  */
final case class UpdateNotificationGroup(
  notificationGroupId: Int,
  `type`: NotificationGroupType,
  chatId: Long,
  notificationSettingsChatId: Long,
  isSilent: Boolean,
  totalCount: Int,
  addedNotifications: List[Notification],
  removedNotificationIds: List[Int]
) extends Update

private[api] object UpdateNotificationGroup {
  def fromJava(o: TdApi.UpdateNotificationGroup): UpdateNotificationGroup =
    UpdateNotificationGroup(
      o.notificationGroupId,
      NotificationGroupType.fromJava(o.`type`),
      o.chatId,
      o.notificationSettingsChatId,
      o.isSilent,
      o.totalCount,
      o.addedNotifications.map(Notification.fromJava).toList,
      o.removedNotificationIds.toList
    )
}

/**
  * Contains active notifications that was shown on previous application launches. This update is sent only if the message database is used. In that case it comes once before any updateNotification and updateNotificationGroup update.
  *
  * @param groups Lists of active notification groups.
  */
final case class UpdateActiveNotifications(groups: List[NotificationGroup]) extends Update

private[api] object UpdateActiveNotifications {
  def fromJava(o: TdApi.UpdateActiveNotifications): UpdateActiveNotifications =
    UpdateActiveNotifications(o.groups.map(NotificationGroup.fromJava).toList)
}

/**
  * Describes whether there are some pending notification updates. Can be used to prevent application from killing, while there are some pending notifications.
  *
  * @param haveDelayedNotifications    True, if there are some delayed notification updates, which will be sent soon.
  * @param haveUnreceivedNotifications True, if there can be some yet unreceived notifications, which are being fetched from the server.
  */
final case class UpdateHavePendingNotifications(haveDelayedNotifications: Boolean, haveUnreceivedNotifications: Boolean)
    extends Update

private[api] object UpdateHavePendingNotifications {
  def fromJava(o: TdApi.UpdateHavePendingNotifications): UpdateHavePendingNotifications =
    UpdateHavePendingNotifications(o.haveDelayedNotifications, o.haveUnreceivedNotifications)
}

/**
  * Some messages were deleted.
  *
  * @param chatId      Chat identifier.
  * @param messageIds  Identifiers of the deleted messages.
  * @param isPermanent True, if the messages are permanently deleted by a user (as opposed to just becoming inaccessible).
  * @param fromCache   True, if the messages are deleted only from the cache and can possibly be retrieved again in the future.
  */
final case class UpdateDeleteMessages(chatId: Long, messageIds: List[Long], isPermanent: Boolean, fromCache: Boolean)
    extends Update

private[api] object UpdateDeleteMessages {
  def fromJava(o: TdApi.UpdateDeleteMessages): UpdateDeleteMessages =
    UpdateDeleteMessages(o.chatId, o.messageIds.toList, o.isPermanent, o.fromCache)
}

/**
  * User activity in the chat has changed.
  *
  * @param chatId Chat identifier.
  * @param userId Identifier of a user performing an action.
  * @param action The action description.
  */
final case class UpdateUserChatAction(chatId: Long, userId: Int, action: ChatAction) extends Update

private[api] object UpdateUserChatAction {
  def fromJava(o: TdApi.UpdateUserChatAction): UpdateUserChatAction =
    UpdateUserChatAction(o.chatId, o.userId, ChatAction.fromJava(o.action))
}

/**
  * The user went online or offline.
  *
  * @param userId User identifier.
  * @param status New status of the user.
  */
final case class UpdateUserStatus(userId: Int, status: UserStatus) extends Update

private[api] object UpdateUserStatus {
  def fromJava(o: TdApi.UpdateUserStatus): UpdateUserStatus = UpdateUserStatus(o.userId, UserStatus.fromJava(o.status))
}

/**
  * Some data of a user has changed. This update is guaranteed to come before the user identifier is returned to the client.
  *
  * @param user New data about the user.
  */
final case class UpdateUser(user: User) extends Update

private[api] object UpdateUser {
  def fromJava(o: TdApi.UpdateUser): UpdateUser = UpdateUser(User.fromJava(o.user))
}

/**
  * Some data of a basic group has changed. This update is guaranteed to come before the basic group identifier is returned to the client.
  *
  * @param basicGroup New data about the group.
  */
final case class UpdateBasicGroup(basicGroup: BasicGroup) extends Update

private[api] object UpdateBasicGroup {
  def fromJava(o: TdApi.UpdateBasicGroup): UpdateBasicGroup = UpdateBasicGroup(BasicGroup.fromJava(o.basicGroup))
}

/**
  * Some data of a supergroup or a channel has changed. This update is guaranteed to come before the supergroup identifier is returned to the client.
  *
  * @param supergroup New data about the supergroup.
  */
final case class UpdateSupergroup(supergroup: Supergroup) extends Update

private[api] object UpdateSupergroup {
  def fromJava(o: TdApi.UpdateSupergroup): UpdateSupergroup = UpdateSupergroup(Supergroup.fromJava(o.supergroup))
}

/**
  * Some data of a secret chat has changed. This update is guaranteed to come before the secret chat identifier is returned to the client.
  *
  * @param secretChat New data about the secret chat.
  */
final case class UpdateSecretChat(secretChat: SecretChat) extends Update

private[api] object UpdateSecretChat {
  def fromJava(o: TdApi.UpdateSecretChat): UpdateSecretChat = UpdateSecretChat(SecretChat.fromJava(o.secretChat))
}

/**
  * Some data from userFullInfo has been changed.
  *
  * @param userId       User identifier.
  * @param userFullInfo New full information about the user.
  */
final case class UpdateUserFullInfo(userId: Int, userFullInfo: UserFullInfo) extends Update

private[api] object UpdateUserFullInfo {
  def fromJava(o: TdApi.UpdateUserFullInfo): UpdateUserFullInfo =
    UpdateUserFullInfo(o.userId, UserFullInfo.fromJava(o.userFullInfo))
}

/**
  * Some data from basicGroupFullInfo has been changed.
  *
  * @param basicGroupId       Identifier of a basic group.
  * @param basicGroupFullInfo New full information about the group.
  */
final case class UpdateBasicGroupFullInfo(basicGroupId: Int, basicGroupFullInfo: BasicGroupFullInfo) extends Update

private[api] object UpdateBasicGroupFullInfo {
  def fromJava(o: TdApi.UpdateBasicGroupFullInfo): UpdateBasicGroupFullInfo =
    UpdateBasicGroupFullInfo(o.basicGroupId, BasicGroupFullInfo.fromJava(o.basicGroupFullInfo))
}

/**
  * Some data from supergroupFullInfo has been changed.
  *
  * @param supergroupId       Identifier of the supergroup or channel.
  * @param supergroupFullInfo New full information about the supergroup.
  */
final case class UpdateSupergroupFullInfo(supergroupId: Int, supergroupFullInfo: SupergroupFullInfo) extends Update

private[api] object UpdateSupergroupFullInfo {
  def fromJava(o: TdApi.UpdateSupergroupFullInfo): UpdateSupergroupFullInfo =
    UpdateSupergroupFullInfo(o.supergroupId, SupergroupFullInfo.fromJava(o.supergroupFullInfo))
}

/**
  * Service notification from the server. Upon receiving this the client must show a popup with the content of the notification.
  *
  * @param type    Notification type. If type begins with &quot;AUTH_KEY_DROP_&quot;, then two buttons &quot;Cancel&quot; and &quot;Log out&quot; should be shown under notification; if user presses the second, all local data should be destroyed using Destroy method.
  * @param content Notification content.
  */
final case class UpdateServiceNotification(`type`: String, content: MessageContent) extends Update

private[api] object UpdateServiceNotification {
  def fromJava(o: TdApi.UpdateServiceNotification): UpdateServiceNotification =
    UpdateServiceNotification(o.`type`, MessageContent.fromJava(o.content))
}

/**
  * Information about a file was updated.
  *
  * @param file New data about the file.
  */
final case class UpdateFile(file: File) extends Update

private[api] object UpdateFile {
  def fromJava(o: TdApi.UpdateFile): UpdateFile = UpdateFile(File.fromJava(o.file))
}

/**
  * The file generation process needs to be started by the client.
  *
  * @param generationId    Unique identifier for the generation process.
  * @param originalPath    The path to a file from which a new file is generated; may be empty.
  * @param destinationPath The path to a file that should be created and where the new file should be generated.
  * @param conversion      String specifying the conversion applied to the original file. If conversion is &quot;#url#&quot; than originalPath contains an HTTP/HTTPS URL of a file, which should be downloaded by the client.
  */
final case class UpdateFileGenerationStart(
  generationId: Long,
  originalPath: String,
  destinationPath: String,
  conversion: String
) extends Update

private[api] object UpdateFileGenerationStart {
  def fromJava(o: TdApi.UpdateFileGenerationStart): UpdateFileGenerationStart =
    UpdateFileGenerationStart(o.generationId, o.originalPath, o.destinationPath, o.conversion)
}

/**
  * File generation is no longer needed.
  *
  * @param generationId Unique identifier for the generation process.
  */
final case class UpdateFileGenerationStop(generationId: Long) extends Update

private[api] object UpdateFileGenerationStop {
  def fromJava(o: TdApi.UpdateFileGenerationStop): UpdateFileGenerationStop = UpdateFileGenerationStop(o.generationId)
}

/**
  * New call was created or information about a call was updated.
  *
  * @param call New data about a call.
  */
final case class UpdateCall(call: Call) extends Update

private[api] object UpdateCall {
  def fromJava(o: TdApi.UpdateCall): UpdateCall = UpdateCall(Call.fromJava(o.call))
}

/**
  * Some privacy setting rules have been changed.
  *
  * @param setting The privacy setting.
  * @param rules   New privacy rules.
  */
final case class UpdateUserPrivacySettingRules(setting: UserPrivacySetting, rules: UserPrivacySettingRules)
    extends Update

private[api] object UpdateUserPrivacySettingRules {
  def fromJava(o: TdApi.UpdateUserPrivacySettingRules): UpdateUserPrivacySettingRules =
    UpdateUserPrivacySettingRules(UserPrivacySetting.fromJava(o.setting), UserPrivacySettingRules.fromJava(o.rules))
}

/**
  * Number of unread messages in a chat list has changed. This update is sent only if the message database is used.
  *
  * @param chatList           The chat list with changed number of unread messages.
  * @param unreadCount        Total number of unread messages.
  * @param unreadUnmutedCount Total number of unread messages in unmuted chats.
  */
final case class UpdateUnreadMessageCount(chatList: ChatList, unreadCount: Int, unreadUnmutedCount: Int) extends Update

private[api] object UpdateUnreadMessageCount {
  def fromJava(o: TdApi.UpdateUnreadMessageCount): UpdateUnreadMessageCount =
    UpdateUnreadMessageCount(ChatList.fromJava(o.chatList), o.unreadCount, o.unreadUnmutedCount)
}

/**
  * Number of unread chats, i.e. with unread messages or marked as unread, has changed. This update is sent only if the message database is used.
  *
  * @param chatList                   The chat list with changed number of unread messages.
  * @param totalCount                 Approximate total number of chats in the chat list.
  * @param unreadCount                Total number of unread chats.
  * @param unreadUnmutedCount         Total number of unread unmuted chats.
  * @param markedAsUnreadCount        Total number of chats marked as unread.
  * @param markedAsUnreadUnmutedCount Total number of unmuted chats marked as unread.
  */
final case class UpdateUnreadChatCount(
  chatList: ChatList,
  totalCount: Int,
  unreadCount: Int,
  unreadUnmutedCount: Int,
  markedAsUnreadCount: Int,
  markedAsUnreadUnmutedCount: Int
) extends Update

private[api] object UpdateUnreadChatCount {
  def fromJava(o: TdApi.UpdateUnreadChatCount): UpdateUnreadChatCount =
    UpdateUnreadChatCount(
      ChatList.fromJava(o.chatList),
      o.totalCount,
      o.unreadCount,
      o.unreadUnmutedCount,
      o.markedAsUnreadCount,
      o.markedAsUnreadUnmutedCount
    )
}

/**
  * An option changed its value.
  *
  * @param name  The option name.
  * @param value The new option value.
  */
final case class UpdateOption(name: String, value: OptionValue) extends Update

private[api] object UpdateOption {
  def fromJava(o: TdApi.UpdateOption): UpdateOption = UpdateOption(o.name, OptionValue.fromJava(o.value))
}

/**
  * The list of installed sticker sets was updated.
  *
  * @param isMasks       True, if the list of installed mask sticker sets was updated.
  * @param stickerSetIds The new list of installed ordinary sticker sets.
  */
final case class UpdateInstalledStickerSets(isMasks: Boolean, stickerSetIds: List[Long]) extends Update

private[api] object UpdateInstalledStickerSets {
  def fromJava(o: TdApi.UpdateInstalledStickerSets): UpdateInstalledStickerSets =
    UpdateInstalledStickerSets(o.isMasks, o.stickerSetIds.toList)
}

/**
  * The list of trending sticker sets was updated or some of them were viewed.
  *
  * @param stickerSets The new list of trending sticker sets.
  */
final case class UpdateTrendingStickerSets(stickerSets: StickerSets) extends Update

private[api] object UpdateTrendingStickerSets {
  def fromJava(o: TdApi.UpdateTrendingStickerSets): UpdateTrendingStickerSets =
    UpdateTrendingStickerSets(StickerSets.fromJava(o.stickerSets))
}

/**
  * The list of recently used stickers was updated.
  *
  * @param isAttached True, if the list of stickers attached to photo or video files was updated, otherwise the list of sent stickers is updated.
  * @param stickerIds The new list of file identifiers of recently used stickers.
  */
final case class UpdateRecentStickers(isAttached: Boolean, stickerIds: List[Int]) extends Update

private[api] object UpdateRecentStickers {
  def fromJava(o: TdApi.UpdateRecentStickers): UpdateRecentStickers =
    UpdateRecentStickers(o.isAttached, o.stickerIds.toList)
}

/**
  * The list of favorite stickers was updated.
  *
  * @param stickerIds The new list of file identifiers of favorite stickers.
  */
final case class UpdateFavoriteStickers(stickerIds: List[Int]) extends Update

private[api] object UpdateFavoriteStickers {
  def fromJava(o: TdApi.UpdateFavoriteStickers): UpdateFavoriteStickers = UpdateFavoriteStickers(o.stickerIds.toList)
}

/**
  * The list of saved animations was updated.
  *
  * @param animationIds The new list of file identifiers of saved animations.
  */
final case class UpdateSavedAnimations(animationIds: List[Int]) extends Update

private[api] object UpdateSavedAnimations {
  def fromJava(o: TdApi.UpdateSavedAnimations): UpdateSavedAnimations = UpdateSavedAnimations(o.animationIds.toList)
}

/**
  * The selected background has changed.
  *
  * @param forDarkTheme True, if background for dark theme has changed.
  * @param background   The new selected background; may be None.
  */
final case class UpdateSelectedBackground(forDarkTheme: Boolean, background: Option[Background]) extends Update

private[api] object UpdateSelectedBackground {
  def fromJava(o: TdApi.UpdateSelectedBackground): UpdateSelectedBackground =
    UpdateSelectedBackground(o.forDarkTheme, Option(o.background).map(Background.fromJava))
}

/**
  * Some language pack strings have been updated.
  *
  * @param localizationTarget Localization target to which the language pack belongs.
  * @param languagePackId     Identifier of the updated language pack.
  * @param strings            List of changed language pack strings.
  */
final case class UpdateLanguagePackStrings(
  localizationTarget: String,
  languagePackId: String,
  strings: List[LanguagePackString]
) extends Update

private[api] object UpdateLanguagePackStrings {
  def fromJava(o: TdApi.UpdateLanguagePackStrings): UpdateLanguagePackStrings =
    UpdateLanguagePackStrings(o.localizationTarget, o.languagePackId, o.strings.map(LanguagePackString.fromJava).toList)
}

/**
  * The connection state has changed.
  *
  * @param state The new connection state.
  */
final case class UpdateConnectionState(state: ConnectionState) extends Update

private[api] object UpdateConnectionState {
  def fromJava(o: TdApi.UpdateConnectionState): UpdateConnectionState =
    UpdateConnectionState(ConnectionState.fromJava(o.state))
}

/**
  * New terms of service must be accepted by the user. If the terms of service are declined, then the deleteAccount method should be called with the reason &quot;Decline ToS update&quot;.
  *
  * @param termsOfServiceId Identifier of the terms of service.
  * @param termsOfService   The new terms of service.
  */
final case class UpdateTermsOfService(termsOfServiceId: String, termsOfService: TermsOfService) extends Update

private[api] object UpdateTermsOfService {
  def fromJava(o: TdApi.UpdateTermsOfService): UpdateTermsOfService =
    UpdateTermsOfService(o.termsOfServiceId, TermsOfService.fromJava(o.termsOfService))
}

/**
  * List of users nearby has changed. The update is sent only 60 seconds after a successful searchChatsNearby request.
  *
  * @param usersNearby The new list of users nearby.
  */
final case class UpdateUsersNearby(usersNearby: List[ChatNearby]) extends Update

private[api] object UpdateUsersNearby {
  def fromJava(o: TdApi.UpdateUsersNearby): UpdateUsersNearby =
    UpdateUsersNearby(o.usersNearby.map(ChatNearby.fromJava).toList)
}

/**
  * A new incoming inline query; for bots only.
  *
  * @param id           Unique query identifier.
  * @param senderUserId Identifier of the user who sent the query.
  * @param userLocation User location, provided by the client; may be None.
  * @param query        Text of the query.
  * @param offset       Offset of the first entry to return.
  */
final case class UpdateNewInlineQuery(
  id: Long,
  senderUserId: Int,
  userLocation: Option[Location],
  query: String,
  offset: String
) extends Update

private[api] object UpdateNewInlineQuery {
  def fromJava(o: TdApi.UpdateNewInlineQuery): UpdateNewInlineQuery =
    UpdateNewInlineQuery(o.id, o.senderUserId, Option(o.userLocation).map(Location.fromJava), o.query, o.offset)
}

/**
  * The user has chosen a result of an inline query; for bots only.
  *
  * @param senderUserId    Identifier of the user who sent the query.
  * @param userLocation    User location, provided by the client; may be None.
  * @param query           Text of the query.
  * @param resultId        Identifier of the chosen result.
  * @param inlineMessageId Identifier of the sent inline message, if known.
  */
final case class UpdateNewChosenInlineResult(
  senderUserId: Int,
  userLocation: Option[Location],
  query: String,
  resultId: String,
  inlineMessageId: String
) extends Update

private[api] object UpdateNewChosenInlineResult {
  def fromJava(o: TdApi.UpdateNewChosenInlineResult): UpdateNewChosenInlineResult =
    UpdateNewChosenInlineResult(
      o.senderUserId,
      Option(o.userLocation).map(Location.fromJava),
      o.query,
      o.resultId,
      o.inlineMessageId
    )
}

/**
  * A new incoming callback query; for bots only.
  *
  * @param id           Unique query identifier.
  * @param senderUserId Identifier of the user who sent the query.
  * @param chatId       Identifier of the chat where the query was sent.
  * @param messageId    Identifier of the message, from which the query originated.
  * @param chatInstance Identifier that uniquely corresponds to the chat to which the message was sent.
  * @param payload      Query payload.
  */
final case class UpdateNewCallbackQuery(
  id: Long,
  senderUserId: Int,
  chatId: Long,
  messageId: Long,
  chatInstance: Long,
  payload: CallbackQueryPayload
) extends Update

private[api] object UpdateNewCallbackQuery {
  def fromJava(o: TdApi.UpdateNewCallbackQuery): UpdateNewCallbackQuery =
    UpdateNewCallbackQuery(
      o.id,
      o.senderUserId,
      o.chatId,
      o.messageId,
      o.chatInstance,
      CallbackQueryPayload.fromJava(o.payload)
    )
}

/**
  * A new incoming callback query from a message sent via a bot; for bots only.
  *
  * @param id              Unique query identifier.
  * @param senderUserId    Identifier of the user who sent the query.
  * @param inlineMessageId Identifier of the inline message, from which the query originated.
  * @param chatInstance    An identifier uniquely corresponding to the chat a message was sent to.
  * @param payload         Query payload.
  */
final case class UpdateNewInlineCallbackQuery(
  id: Long,
  senderUserId: Int,
  inlineMessageId: String,
  chatInstance: Long,
  payload: CallbackQueryPayload
) extends Update

private[api] object UpdateNewInlineCallbackQuery {
  def fromJava(o: TdApi.UpdateNewInlineCallbackQuery): UpdateNewInlineCallbackQuery =
    UpdateNewInlineCallbackQuery(
      o.id,
      o.senderUserId,
      o.inlineMessageId,
      o.chatInstance,
      CallbackQueryPayload.fromJava(o.payload)
    )
}

/**
  * A new incoming shipping query; for bots only. Only for invoices with flexible price.
  *
  * @param id              Unique query identifier.
  * @param senderUserId    Identifier of the user who sent the query.
  * @param invoicePayload  Invoice payload.
  * @param shippingAddress User shipping address.
  */
final case class UpdateNewShippingQuery(
  id: Long,
  senderUserId: Int,
  invoicePayload: String,
  shippingAddress: Address
) extends Update

private[api] object UpdateNewShippingQuery {
  def fromJava(o: TdApi.UpdateNewShippingQuery): UpdateNewShippingQuery =
    UpdateNewShippingQuery(o.id, o.senderUserId, o.invoicePayload, Address.fromJava(o.shippingAddress))
}

/**
  * A new incoming pre-checkout query; for bots only. Contains full information about a checkout.
  *
  * @param id               Unique query identifier.
  * @param senderUserId     Identifier of the user who sent the query.
  * @param currency         Currency for the product price.
  * @param totalAmount      Total price for the product, in the minimal quantity of the currency.
  * @param invoicePayload   Invoice payload.
  * @param shippingOptionId Identifier of a shipping option chosen by the user; may be empty if not applicable.
  * @param orderInfo        Information about the order; may be None.
  */
final case class UpdateNewPreCheckoutQuery(
  id: Long,
  senderUserId: Int,
  currency: String,
  totalAmount: Long,
  invoicePayload: List[Byte],
  shippingOptionId: String,
  orderInfo: Option[OrderInfo]
) extends Update

private[api] object UpdateNewPreCheckoutQuery {
  def fromJava(o: TdApi.UpdateNewPreCheckoutQuery): UpdateNewPreCheckoutQuery =
    UpdateNewPreCheckoutQuery(
      o.id,
      o.senderUserId,
      o.currency,
      o.totalAmount,
      o.invoicePayload.toList,
      o.shippingOptionId,
      Option(o.orderInfo).map(OrderInfo.fromJava)
    )
}

/**
  * A new incoming event; for bots only.
  *
  * @param event A JSON-serialized event.
  */
final case class UpdateNewCustomEvent(event: String) extends Update

private[api] object UpdateNewCustomEvent {
  def fromJava(o: TdApi.UpdateNewCustomEvent): UpdateNewCustomEvent = UpdateNewCustomEvent(o.event)
}

/**
  * A new incoming query; for bots only.
  *
  * @param id      The query identifier.
  * @param data    JSON-serialized query data.
  * @param timeout Query timeout.
  */
final case class UpdateNewCustomQuery(id: Long, data: String, timeout: Int) extends Update

private[api] object UpdateNewCustomQuery {
  def fromJava(o: TdApi.UpdateNewCustomQuery): UpdateNewCustomQuery = UpdateNewCustomQuery(o.id, o.data, o.timeout)
}

/**
  * A poll was updated; for bots only.
  *
  * @param poll New data about the poll.
  */
final case class UpdatePoll(poll: Poll) extends Update

private[api] object UpdatePoll {
  def fromJava(o: TdApi.UpdatePoll): UpdatePoll = UpdatePoll(Poll.fromJava(o.poll))
}

/**
  * A user changed the answer to a poll; for bots only.
  *
  * @param pollId    Unique poll identifier.
  * @param userId    The user, who changed the answer to the poll.
  * @param optionIds 0-based identifiers of answer options, chosen by the user.
  */
final case class UpdatePollAnswer(pollId: Long, userId: Int, optionIds: List[Int]) extends Update

private[api] object UpdatePollAnswer {
  def fromJava(o: TdApi.UpdatePollAnswer): UpdatePollAnswer = UpdatePollAnswer(o.pollId, o.userId, o.optionIds.toList)
}

private[api] object Update {
  def fromJava(o: TdApi.Update): Update = o.getConstructor match {
    case TdApi.UpdateUserStatus.CONSTRUCTOR   => UpdateUserStatus.fromJava(o.asInstanceOf[TdApi.UpdateUserStatus])
    case TdApi.UpdateUser.CONSTRUCTOR         => UpdateUser.fromJava(o.asInstanceOf[TdApi.UpdateUser])
    case TdApi.UpdateChatOrder.CONSTRUCTOR    => UpdateChatOrder.fromJava(o.asInstanceOf[TdApi.UpdateChatOrder])
    case TdApi.UpdateNotification.CONSTRUCTOR => UpdateNotification.fromJava(o.asInstanceOf[TdApi.UpdateNotification])
    case TdApi.UpdateChatIsPinned.CONSTRUCTOR => UpdateChatIsPinned.fromJava(o.asInstanceOf[TdApi.UpdateChatIsPinned])
    case TdApi.UpdateBasicGroup.CONSTRUCTOR   => UpdateBasicGroup.fromJava(o.asInstanceOf[TdApi.UpdateBasicGroup])
    case TdApi.UpdateSupergroup.CONSTRUCTOR   => UpdateSupergroup.fromJava(o.asInstanceOf[TdApi.UpdateSupergroup])
    case TdApi.UpdateSecretChat.CONSTRUCTOR   => UpdateSecretChat.fromJava(o.asInstanceOf[TdApi.UpdateSecretChat])
    case TdApi.UpdateUserFullInfo.CONSTRUCTOR => UpdateUserFullInfo.fromJava(o.asInstanceOf[TdApi.UpdateUserFullInfo])
    case TdApi.UpdatePoll.CONSTRUCTOR         => UpdatePoll.fromJava(o.asInstanceOf[TdApi.UpdatePoll])
    case TdApi.UpdateMessageViews.CONSTRUCTOR => UpdateMessageViews.fromJava(o.asInstanceOf[TdApi.UpdateMessageViews])
    case TdApi.UpdateNewChat.CONSTRUCTOR      => UpdateNewChat.fromJava(o.asInstanceOf[TdApi.UpdateNewChat])
    case TdApi.UpdateChatChatList.CONSTRUCTOR => UpdateChatChatList.fromJava(o.asInstanceOf[TdApi.UpdateChatChatList])
    case TdApi.UpdateChatTitle.CONSTRUCTOR    => UpdateChatTitle.fromJava(o.asInstanceOf[TdApi.UpdateChatTitle])
    case TdApi.UpdateChatPhoto.CONSTRUCTOR    => UpdateChatPhoto.fromJava(o.asInstanceOf[TdApi.UpdateChatPhoto])
    case TdApi.UpdateOption.CONSTRUCTOR       => UpdateOption.fromJava(o.asInstanceOf[TdApi.UpdateOption])
    case TdApi.UpdateNewMessage.CONSTRUCTOR   => UpdateNewMessage.fromJava(o.asInstanceOf[TdApi.UpdateNewMessage])
    case TdApi.UpdatePollAnswer.CONSTRUCTOR   => UpdatePollAnswer.fromJava(o.asInstanceOf[TdApi.UpdatePollAnswer])
    case TdApi.UpdateFile.CONSTRUCTOR         => UpdateFile.fromJava(o.asInstanceOf[TdApi.UpdateFile])
    case TdApi.UpdateUsersNearby.CONSTRUCTOR  => UpdateUsersNearby.fromJava(o.asInstanceOf[TdApi.UpdateUsersNearby])
    case TdApi.UpdateCall.CONSTRUCTOR         => UpdateCall.fromJava(o.asInstanceOf[TdApi.UpdateCall])
    case TdApi.UpdateAuthorizationState.CONSTRUCTOR =>
      UpdateAuthorizationState.fromJava(o.asInstanceOf[TdApi.UpdateAuthorizationState])
    case TdApi.UpdateMessageSendAcknowledged.CONSTRUCTOR =>
      UpdateMessageSendAcknowledged.fromJava(o.asInstanceOf[TdApi.UpdateMessageSendAcknowledged])
    case TdApi.UpdateMessageSendSucceeded.CONSTRUCTOR =>
      UpdateMessageSendSucceeded.fromJava(o.asInstanceOf[TdApi.UpdateMessageSendSucceeded])
    case TdApi.UpdateMessageSendFailed.CONSTRUCTOR =>
      UpdateMessageSendFailed.fromJava(o.asInstanceOf[TdApi.UpdateMessageSendFailed])
    case TdApi.UpdateMessageContent.CONSTRUCTOR =>
      UpdateMessageContent.fromJava(o.asInstanceOf[TdApi.UpdateMessageContent])
    case TdApi.UpdateMessageEdited.CONSTRUCTOR =>
      UpdateMessageEdited.fromJava(o.asInstanceOf[TdApi.UpdateMessageEdited])
    case TdApi.UpdateMessageContentOpened.CONSTRUCTOR =>
      UpdateMessageContentOpened.fromJava(o.asInstanceOf[TdApi.UpdateMessageContentOpened])
    case TdApi.UpdateMessageMentionRead.CONSTRUCTOR =>
      UpdateMessageMentionRead.fromJava(o.asInstanceOf[TdApi.UpdateMessageMentionRead])
    case TdApi.UpdateMessageLiveLocationViewed.CONSTRUCTOR =>
      UpdateMessageLiveLocationViewed.fromJava(o.asInstanceOf[TdApi.UpdateMessageLiveLocationViewed])
    case TdApi.UpdateChatPermissions.CONSTRUCTOR =>
      UpdateChatPermissions.fromJava(o.asInstanceOf[TdApi.UpdateChatPermissions])
    case TdApi.UpdateChatLastMessage.CONSTRUCTOR =>
      UpdateChatLastMessage.fromJava(o.asInstanceOf[TdApi.UpdateChatLastMessage])
    case TdApi.UpdateChatIsMarkedAsUnread.CONSTRUCTOR =>
      UpdateChatIsMarkedAsUnread.fromJava(o.asInstanceOf[TdApi.UpdateChatIsMarkedAsUnread])
    case TdApi.UpdateChatIsSponsored.CONSTRUCTOR =>
      UpdateChatIsSponsored.fromJava(o.asInstanceOf[TdApi.UpdateChatIsSponsored])
    case TdApi.UpdateChatHasScheduledMessages.CONSTRUCTOR =>
      UpdateChatHasScheduledMessages.fromJava(o.asInstanceOf[TdApi.UpdateChatHasScheduledMessages])
    case TdApi.UpdateChatDefaultDisableNotification.CONSTRUCTOR =>
      UpdateChatDefaultDisableNotification.fromJava(o.asInstanceOf[TdApi.UpdateChatDefaultDisableNotification])
    case TdApi.UpdateChatReadInbox.CONSTRUCTOR =>
      UpdateChatReadInbox.fromJava(o.asInstanceOf[TdApi.UpdateChatReadInbox])
    case TdApi.UpdateChatReadOutbox.CONSTRUCTOR =>
      UpdateChatReadOutbox.fromJava(o.asInstanceOf[TdApi.UpdateChatReadOutbox])
    case TdApi.UpdateChatUnreadMentionCount.CONSTRUCTOR =>
      UpdateChatUnreadMentionCount.fromJava(o.asInstanceOf[TdApi.UpdateChatUnreadMentionCount])
    case TdApi.UpdateChatNotificationSettings.CONSTRUCTOR =>
      UpdateChatNotificationSettings.fromJava(o.asInstanceOf[TdApi.UpdateChatNotificationSettings])
    case TdApi.UpdateScopeNotificationSettings.CONSTRUCTOR =>
      UpdateScopeNotificationSettings.fromJava(o.asInstanceOf[TdApi.UpdateScopeNotificationSettings])
    case TdApi.UpdateChatActionBar.CONSTRUCTOR =>
      UpdateChatActionBar.fromJava(o.asInstanceOf[TdApi.UpdateChatActionBar])
    case TdApi.UpdateChatPinnedMessage.CONSTRUCTOR =>
      UpdateChatPinnedMessage.fromJava(o.asInstanceOf[TdApi.UpdateChatPinnedMessage])
    case TdApi.UpdateChatReplyMarkup.CONSTRUCTOR =>
      UpdateChatReplyMarkup.fromJava(o.asInstanceOf[TdApi.UpdateChatReplyMarkup])
    case TdApi.UpdateChatDraftMessage.CONSTRUCTOR =>
      UpdateChatDraftMessage.fromJava(o.asInstanceOf[TdApi.UpdateChatDraftMessage])
    case TdApi.UpdateChatOnlineMemberCount.CONSTRUCTOR =>
      UpdateChatOnlineMemberCount.fromJava(o.asInstanceOf[TdApi.UpdateChatOnlineMemberCount])
    case TdApi.UpdateNotificationGroup.CONSTRUCTOR =>
      UpdateNotificationGroup.fromJava(o.asInstanceOf[TdApi.UpdateNotificationGroup])
    case TdApi.UpdateActiveNotifications.CONSTRUCTOR =>
      UpdateActiveNotifications.fromJava(o.asInstanceOf[TdApi.UpdateActiveNotifications])
    case TdApi.UpdateHavePendingNotifications.CONSTRUCTOR =>
      UpdateHavePendingNotifications.fromJava(o.asInstanceOf[TdApi.UpdateHavePendingNotifications])
    case TdApi.UpdateDeleteMessages.CONSTRUCTOR =>
      UpdateDeleteMessages.fromJava(o.asInstanceOf[TdApi.UpdateDeleteMessages])
    case TdApi.UpdateUserChatAction.CONSTRUCTOR =>
      UpdateUserChatAction.fromJava(o.asInstanceOf[TdApi.UpdateUserChatAction])
    case TdApi.UpdateBasicGroupFullInfo.CONSTRUCTOR =>
      UpdateBasicGroupFullInfo.fromJava(o.asInstanceOf[TdApi.UpdateBasicGroupFullInfo])
    case TdApi.UpdateSupergroupFullInfo.CONSTRUCTOR =>
      UpdateSupergroupFullInfo.fromJava(o.asInstanceOf[TdApi.UpdateSupergroupFullInfo])
    case TdApi.UpdateServiceNotification.CONSTRUCTOR =>
      UpdateServiceNotification.fromJava(o.asInstanceOf[TdApi.UpdateServiceNotification])
    case TdApi.UpdateFileGenerationStart.CONSTRUCTOR =>
      UpdateFileGenerationStart.fromJava(o.asInstanceOf[TdApi.UpdateFileGenerationStart])
    case TdApi.UpdateFileGenerationStop.CONSTRUCTOR =>
      UpdateFileGenerationStop.fromJava(o.asInstanceOf[TdApi.UpdateFileGenerationStop])
    case TdApi.UpdateUserPrivacySettingRules.CONSTRUCTOR =>
      UpdateUserPrivacySettingRules.fromJava(o.asInstanceOf[TdApi.UpdateUserPrivacySettingRules])
    case TdApi.UpdateUnreadMessageCount.CONSTRUCTOR =>
      UpdateUnreadMessageCount.fromJava(o.asInstanceOf[TdApi.UpdateUnreadMessageCount])
    case TdApi.UpdateUnreadChatCount.CONSTRUCTOR =>
      UpdateUnreadChatCount.fromJava(o.asInstanceOf[TdApi.UpdateUnreadChatCount])
    case TdApi.UpdateInstalledStickerSets.CONSTRUCTOR =>
      UpdateInstalledStickerSets.fromJava(o.asInstanceOf[TdApi.UpdateInstalledStickerSets])
    case TdApi.UpdateTrendingStickerSets.CONSTRUCTOR =>
      UpdateTrendingStickerSets.fromJava(o.asInstanceOf[TdApi.UpdateTrendingStickerSets])
    case TdApi.UpdateRecentStickers.CONSTRUCTOR =>
      UpdateRecentStickers.fromJava(o.asInstanceOf[TdApi.UpdateRecentStickers])
    case TdApi.UpdateFavoriteStickers.CONSTRUCTOR =>
      UpdateFavoriteStickers.fromJava(o.asInstanceOf[TdApi.UpdateFavoriteStickers])
    case TdApi.UpdateSavedAnimations.CONSTRUCTOR =>
      UpdateSavedAnimations.fromJava(o.asInstanceOf[TdApi.UpdateSavedAnimations])
    case TdApi.UpdateSelectedBackground.CONSTRUCTOR =>
      UpdateSelectedBackground.fromJava(o.asInstanceOf[TdApi.UpdateSelectedBackground])
    case TdApi.UpdateLanguagePackStrings.CONSTRUCTOR =>
      UpdateLanguagePackStrings.fromJava(o.asInstanceOf[TdApi.UpdateLanguagePackStrings])
    case TdApi.UpdateConnectionState.CONSTRUCTOR =>
      UpdateConnectionState.fromJava(o.asInstanceOf[TdApi.UpdateConnectionState])
    case TdApi.UpdateTermsOfService.CONSTRUCTOR =>
      UpdateTermsOfService.fromJava(o.asInstanceOf[TdApi.UpdateTermsOfService])
    case TdApi.UpdateNewInlineQuery.CONSTRUCTOR =>
      UpdateNewInlineQuery.fromJava(o.asInstanceOf[TdApi.UpdateNewInlineQuery])
    case TdApi.UpdateNewChosenInlineResult.CONSTRUCTOR =>
      UpdateNewChosenInlineResult.fromJava(o.asInstanceOf[TdApi.UpdateNewChosenInlineResult])
    case TdApi.UpdateNewCallbackQuery.CONSTRUCTOR =>
      UpdateNewCallbackQuery.fromJava(o.asInstanceOf[TdApi.UpdateNewCallbackQuery])
    case TdApi.UpdateNewInlineCallbackQuery.CONSTRUCTOR =>
      UpdateNewInlineCallbackQuery.fromJava(o.asInstanceOf[TdApi.UpdateNewInlineCallbackQuery])
    case TdApi.UpdateNewShippingQuery.CONSTRUCTOR =>
      UpdateNewShippingQuery.fromJava(o.asInstanceOf[TdApi.UpdateNewShippingQuery])
    case TdApi.UpdateNewPreCheckoutQuery.CONSTRUCTOR =>
      UpdateNewPreCheckoutQuery.fromJava(o.asInstanceOf[TdApi.UpdateNewPreCheckoutQuery])
    case TdApi.UpdateNewCustomEvent.CONSTRUCTOR =>
      UpdateNewCustomEvent.fromJava(o.asInstanceOf[TdApi.UpdateNewCustomEvent])
    case TdApi.UpdateNewCustomQuery.CONSTRUCTOR =>
      UpdateNewCustomQuery.fromJava(o.asInstanceOf[TdApi.UpdateNewCustomQuery])
  }
}
