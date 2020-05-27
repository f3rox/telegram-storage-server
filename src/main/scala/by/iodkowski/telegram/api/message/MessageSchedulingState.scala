package by.iodkowski.telegram.api.message

import org.drinkless.tdlib.TdApi

/**
  * Contains information about the time when a scheduled message will be sent.
  */
sealed abstract class MessageSchedulingState extends Product with Serializable

/**
  * The message will be sent when the peer will be online. Applicable to private chats only and when the exact online status of the peer is known.
  */
case object MessageSchedulingStateSendWhenOnline extends MessageSchedulingState

/**
  * The message will be sent at the specified date.
  *
  * @param sendDate Date the message will be sent. The date must be within 367 days in the future.
  */
final case class MessageSchedulingStateSendAtDate(sendDate: Int) extends MessageSchedulingState

private[api] object MessageSchedulingStateSendAtDate {
  def fromJava(o: TdApi.MessageSchedulingStateSendAtDate): MessageSchedulingStateSendAtDate =
    MessageSchedulingStateSendAtDate(o.sendDate)
}

private[api] object MessageSchedulingState {
  def fromJava(o: TdApi.MessageSchedulingState): MessageSchedulingState =
    o.getConstructor match {
      case TdApi.MessageSchedulingStateSendWhenOnline.CONSTRUCTOR => MessageSchedulingStateSendWhenOnline
      case TdApi.MessageSchedulingStateSendAtDate.CONSTRUCTOR =>
        MessageSchedulingStateSendAtDate.fromJava(o.asInstanceOf[TdApi.MessageSchedulingStateSendAtDate])
    }
}
