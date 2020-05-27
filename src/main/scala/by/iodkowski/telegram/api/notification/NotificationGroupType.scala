package by.iodkowski.telegram.api.notification

import org.drinkless.tdlib.TdApi

/**
  * Describes the type of notifications in a notification group.
  */
sealed abstract class NotificationGroupType extends Product with Serializable

/**
  * A group containing notifications of type notificationTypeNewMessage and notificationTypeNewPushMessage with ordinary unread messages.
  */
case object NotificationGroupTypeMessages extends NotificationGroupType

/**
  * A group containing notifications of type notificationTypeNewMessage and notificationTypeNewPushMessage with unread mentions of the current user, replies to their messages, or a pinned message.
  */
case object NotificationGroupTypeMentions extends NotificationGroupType

/**
  * A group containing a notification of type notificationTypeNewSecretChat.
  */
case object NotificationGroupTypeSecretChat extends NotificationGroupType

/**
  * A group containing notifications of type notificationTypeNewCall.
  */
case object NotificationGroupTypeCalls extends NotificationGroupType

private[api] object NotificationGroupType {
  def fromJava(o: TdApi.NotificationGroupType): NotificationGroupType = o.getConstructor match {
    case TdApi.NotificationGroupTypeMessages.CONSTRUCTOR   => NotificationGroupTypeMessages
    case TdApi.NotificationGroupTypeMentions.CONSTRUCTOR   => NotificationGroupTypeMentions
    case TdApi.NotificationGroupTypeSecretChat.CONSTRUCTOR => NotificationGroupTypeSecretChat
    case TdApi.NotificationGroupTypeCalls.CONSTRUCTOR      => NotificationGroupTypeCalls
  }
}
