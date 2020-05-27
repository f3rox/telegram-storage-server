package by.iodkowski.telegram.api.message

import org.drinkless.tdlib.TdApi

/**
  * Contains information about a message draft.
  *
  * @param replyToMessageId Identifier of the message to reply to; 0 if none.
  * @param inputMessageText Content of the message draft.
  */
final case class DraftMessage(replyToMessageId: Long, inputMessageText: InputMessageText)

private[api] object DraftMessage {
  def fromJava(o: TdApi.DraftMessage): DraftMessage =
    DraftMessage(o.replyToMessageId, InputMessageText.fromJava(o.inputMessageText.asInstanceOf[TdApi.InputMessageText]))
}
