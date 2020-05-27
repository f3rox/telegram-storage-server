package by.iodkowski.telegram.api.notification

import org.drinkless.tdlib.TdApi

/**
  * Contains information about notification settings for several chats.
  *
  * @param muteFor                           Time left before notifications will be unmuted, in seconds.
  * @param sound                             The name of an audio file to be used for notification sounds; only applies to iOS applications.
  * @param showPreview                       True, if message content should be displayed in notifications.
  * @param disablePinnedMessageNotifications True, if notifications for incoming pinned messages will be created as for an ordinary unread message.
  * @param disableMentionNotifications       True, if notifications for messages with mentions will be created as for an ordinary unread message.
  */
final case class ScopeNotificationSettings(
  muteFor: Int,
  sound: String,
  showPreview: Boolean,
  disablePinnedMessageNotifications: Boolean,
  disableMentionNotifications: Boolean
)

private[api] object ScopeNotificationSettings {
  def fromJava(o: TdApi.ScopeNotificationSettings): ScopeNotificationSettings =
    ScopeNotificationSettings(
      o.muteFor,
      o.sound,
      o.showPreview,
      o.disablePinnedMessageNotifications,
      o.disableMentionNotifications
    )
}
