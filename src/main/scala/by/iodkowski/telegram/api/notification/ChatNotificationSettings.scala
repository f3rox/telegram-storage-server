package by.iodkowski.telegram.api.notification

import org.drinkless.tdlib.TdApi

/**
  * Contains information about notification settings for a chat.
  *
  * @param useDefaultMuteFor                           If true, muteFor is ignored and the value for the relevant type of chat is used instead.
  * @param muteFor                                     Time left before notifications will be unmuted, in seconds.
  * @param useDefaultSound                             If true, sound is ignored and the value for the relevant type of chat is used instead.
  * @param sound                                       The name of an audio file to be used for notification sounds; only applies to iOS applications.
  * @param useDefaultShowPreview                       If true, showPreview is ignored and the value for the relevant type of chat is used instead.
  * @param showPreview                                 True, if message content should be displayed in notifications.
  * @param useDefaultDisablePinnedMessageNotifications If true, disablePinnedMessageNotifications is ignored and the value for the relevant type of chat is used instead.
  * @param disablePinnedMessageNotifications           If true, notifications for incoming pinned messages will be created as for an ordinary unread message.
  * @param useDefaultDisableMentionNotifications       If true, disableMentionNotifications is ignored and the value for the relevant type of chat is used instead.
  * @param disableMentionNotifications                 If true, notifications for messages with mentions will be created as for an ordinary unread message.
  */
final case class ChatNotificationSettings(
  useDefaultMuteFor: Boolean,
  muteFor: Int,
  useDefaultSound: Boolean,
  sound: String,
  useDefaultShowPreview: Boolean,
  showPreview: Boolean,
  useDefaultDisablePinnedMessageNotifications: Boolean,
  disablePinnedMessageNotifications: Boolean,
  useDefaultDisableMentionNotifications: Boolean,
  disableMentionNotifications: Boolean
)

private[api] object ChatNotificationSettings {
  def fromJava(o: TdApi.ChatNotificationSettings): ChatNotificationSettings =
    ChatNotificationSettings(
      o.useDefaultMuteFor,
      o.muteFor,
      o.useDefaultSound,
      o.sound,
      o.useDefaultShowPreview,
      o.showPreview,
      o.useDefaultDisablePinnedMessageNotifications,
      o.disablePinnedMessageNotifications,
      o.useDefaultDisableMentionNotifications,
      o.disableMentionNotifications
    )
}
