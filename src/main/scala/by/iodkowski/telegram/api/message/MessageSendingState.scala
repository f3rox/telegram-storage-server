package by.iodkowski.telegram.api.message

import org.drinkless.tdlib.TdApi

/**
  * Contains information about the sending state of the message.
  */
sealed abstract class MessageSendingState extends Product with Serializable

/**
  * The message is being sent now, but has not yet been delivered to the server.
  */
case object MessageSendingStatePending extends MessageSendingState

/**
  * The message failed to be sent.
  *
  * @param errorCode    An error code; 0 if unknown.
  * @param errorMessage Error message.
  * @param canRetry     True, if the message can be re-sent.
  * @param retryAfter   Time left before the message can be re-sent, in seconds. No update is sent when this field changes.
  */
final case class MessageSendingStateFailed(errorCode: Int, errorMessage: String, canRetry: Boolean, retryAfter: Double)
    extends MessageSendingState

private[api] object MessageSendingStateFailed {
  def fromJava(o: TdApi.MessageSendingStateFailed): MessageSendingStateFailed =
    MessageSendingStateFailed(o.errorCode, o.errorMessage, o.canRetry, o.retryAfter)
}

private[api] object MessageSendingState {
  def fromJava(o: TdApi.MessageSendingState): MessageSendingState =
    o.getConstructor match {
      case TdApi.MessageSendingStatePending.CONSTRUCTOR => MessageSendingStatePending
      case TdApi.MessageSendingStateFailed.CONSTRUCTOR =>
        MessageSendingStateFailed.fromJava(o.asInstanceOf[TdApi.MessageSendingStateFailed])
    }
}
