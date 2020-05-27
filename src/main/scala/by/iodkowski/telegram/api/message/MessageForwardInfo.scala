package by.iodkowski.telegram.api.message

import org.drinkless.tdlib.TdApi

/**
  * Contains information about a forwarded message.
  *
  * @param origin        Origin of a forwarded message.
  * @param date          Point in time (Unix timestamp) when the message was originally sent.
  * @param fromChatId    For messages forwarded to the chat with the current user (Saved Messages) or to the channel's discussion group, the identifier of the chat from which the message was forwarded last time; 0 if unknown.
  * @param fromMessageId For messages forwarded to the chat with the current user (Saved Messages) or to the channel's discussion group, the identifier of the original message from which the new message was forwarded last time; 0 if unknown.
  */
final case class MessageForwardInfo(origin: MessageForwardOrigin, date: Int, fromChatId: Long, fromMessageId: Long)

private[api] object MessageForwardInfo {
  def fromJava(o: TdApi.MessageForwardInfo): MessageForwardInfo =
    MessageForwardInfo(MessageForwardOrigin.fromJava(o.origin), o.date, o.fromChatId, o.fromMessageId)
}
