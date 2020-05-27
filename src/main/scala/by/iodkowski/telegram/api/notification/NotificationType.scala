package by.iodkowski.telegram.api.notification

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.message.Message

/**
  * Contains detailed information about a notification.
  */
sealed abstract class NotificationType extends Product with Serializable

/**
  * New message was received.
  *
  * @param message The message.
  */
final case class NotificationTypeNewMessage(message: Message) extends NotificationType

private[api] object NotificationTypeNewMessage {
  def fromJava(o: TdApi.NotificationTypeNewMessage): NotificationTypeNewMessage =
    NotificationTypeNewMessage(Message.fromJava(o.message))
}

/**
  * New secret chat was created.
  */
case object NotificationTypeNewSecretChat extends NotificationType

/**
  * New call was received.
  *
  * @param callId Call identifier.
  */
final case class NotificationTypeNewCall(callId: Int) extends NotificationType

private[api] object NotificationTypeNewCall {
  def fromJava(o: TdApi.NotificationTypeNewCall): NotificationTypeNewCall = NotificationTypeNewCall(o.callId)
}

/**
  * New message was received through a push notification.
  *
  * @param messageId    The message identifier. The message will not be available in the chat history, but the ID can be used in viewMessages and as replyToMessageId.
  * @param senderUserId Sender of the message. Corresponding user may be inaccessible.
  * @param content      Push message content.
  */
final case class NotificationTypeNewPushMessage(messageId: Long, senderUserId: Int, content: PushMessageContent)
    extends NotificationType

private[api] object NotificationTypeNewPushMessage {
  def fromJava(o: TdApi.NotificationTypeNewPushMessage): NotificationTypeNewPushMessage =
    NotificationTypeNewPushMessage(o.messageId, o.senderUserId, PushMessageContent.fromJava(o.content))
}

private[api] object NotificationType {
  def fromJava(o: TdApi.NotificationType): NotificationType = o.getConstructor match {
    case TdApi.NotificationTypeNewSecretChat.CONSTRUCTOR => NotificationTypeNewSecretChat
    case TdApi.NotificationTypeNewMessage.CONSTRUCTOR =>
      NotificationTypeNewMessage.fromJava(o.asInstanceOf[TdApi.NotificationTypeNewMessage])
    case TdApi.NotificationTypeNewCall.CONSTRUCTOR =>
      NotificationTypeNewCall.fromJava(o.asInstanceOf[TdApi.NotificationTypeNewCall])
    case TdApi.NotificationTypeNewPushMessage.CONSTRUCTOR =>
      NotificationTypeNewPushMessage.fromJava(o.asInstanceOf[TdApi.NotificationTypeNewPushMessage])
  }
}
