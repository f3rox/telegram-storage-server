package by.iodkowski.telegram.api.message

import org.drinkless.tdlib.TdApi

/**
  * Contains information about the origin of a forwarded message.
  */
sealed abstract class MessageForwardOrigin extends Product with Serializable

/**
  * The message was originally written by a known user.
  *
  * @param senderUserId Identifier of the user that originally sent the message.
  */
final case class MessageForwardOriginUser(senderUserId: Int) extends MessageForwardOrigin

private[api] object MessageForwardOriginUser {
  def fromJava(o: TdApi.MessageForwardOriginUser): MessageForwardOriginUser = MessageForwardOriginUser(o.senderUserId)
}

/**
  * The message was originally written by a user, which is hidden by their privacy settings.
  *
  * @param senderName Name of the sender.
  */
final case class MessageForwardOriginHiddenUser(senderName: String) extends MessageForwardOrigin

private[api] object MessageForwardOriginHiddenUser {
  def fromJava(o: TdApi.MessageForwardOriginHiddenUser): MessageForwardOriginHiddenUser =
    MessageForwardOriginHiddenUser(o.senderName)
}

/**
  * The message was originally a post in a channel.
  *
  * @param chatId          Identifier of the chat from which the message was originally forwarded.
  * @param messageId       Message identifier of the original message; 0 if unknown.
  * @param authorSignature Original post author signature.
  */
final case class MessageForwardOriginChannel(chatId: Long, messageId: Long, authorSignature: String)
    extends MessageForwardOrigin

private[api] object MessageForwardOriginChannel {
  def fromJava(o: TdApi.MessageForwardOriginChannel): MessageForwardOriginChannel =
    MessageForwardOriginChannel(o.chatId, o.messageId, o.authorSignature)
}

private[api] object MessageForwardOrigin {
  def fromJava(o: TdApi.MessageForwardOrigin): MessageForwardOrigin =
    o.getConstructor match {
      case TdApi.MessageForwardOriginUser.CONSTRUCTOR =>
        MessageForwardOriginUser.fromJava(o.asInstanceOf[TdApi.MessageForwardOriginUser])
      case TdApi.MessageForwardOriginHiddenUser.CONSTRUCTOR =>
        MessageForwardOriginHiddenUser.fromJava(o.asInstanceOf[TdApi.MessageForwardOriginHiddenUser])
      case TdApi.MessageForwardOriginChannel.CONSTRUCTOR =>
        MessageForwardOriginChannel.fromJava(o.asInstanceOf[TdApi.MessageForwardOriginChannel])
    }
}
