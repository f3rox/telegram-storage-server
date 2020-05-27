package by.iodkowski.telegram.api.notification

import org.drinkless.tdlib.TdApi

/**
  * Describes the types of chats to which notification settings are applied.
  */
sealed abstract class NotificationSettingsScope extends Product with Serializable

/**
  * Notification settings applied to all private and secret chats when the corresponding chat setting has a default value.
  */
case object NotificationSettingsScopePrivateChats extends NotificationSettingsScope

/**
  * Notification settings applied to all basic groups and supergroups when the corresponding chat setting has a default value.
  */
case object NotificationSettingsScopeGroupChats extends NotificationSettingsScope

/**
  * Notification settings applied to all channels when the corresponding chat setting has a default value.
  */
case object NotificationSettingsScopeChannelChats extends NotificationSettingsScope

private[api] object NotificationSettingsScope {
  def fromJava(o: TdApi.NotificationSettingsScope): NotificationSettingsScope = o.getConstructor match {
    case TdApi.NotificationSettingsScopePrivateChats.CONSTRUCTOR => NotificationSettingsScopePrivateChats
    case TdApi.NotificationSettingsScopeGroupChats.CONSTRUCTOR   => NotificationSettingsScopeGroupChats
    case TdApi.NotificationSettingsScopeChannelChats.CONSTRUCTOR => NotificationSettingsScopeChannelChats
  }
}
