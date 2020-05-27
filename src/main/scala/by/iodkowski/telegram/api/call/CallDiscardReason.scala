package by.iodkowski.telegram.api.call

import org.drinkless.tdlib.TdApi

/**
  * Describes the reason why a call was discarded.
  */
sealed abstract class CallDiscardReason extends Product with Serializable

/**
  * The call wasn't discarded, or the reason is unknown.
  */
case object CallDiscardReasonEmpty extends CallDiscardReason

/**
  * The call was ended before the conversation started. It was cancelled by the caller or missed by the other party.
  */
case object CallDiscardReasonMissed extends CallDiscardReason

/**
  * The call was ended before the conversation started. It was declined by the other party.
  */
case object CallDiscardReasonDeclined extends CallDiscardReason

/**
  * The call was ended during the conversation because the users were disconnected.
  */
case object CallDiscardReasonDisconnected extends CallDiscardReason

/**
  * The call was ended because one of the parties hung up.
  */
case object CallDiscardReasonHungUp extends CallDiscardReason

private[api] object CallDiscardReason {
  def fromJava(o: TdApi.CallDiscardReason): CallDiscardReason = o.getConstructor match {
    case TdApi.CallDiscardReasonEmpty.CONSTRUCTOR        => CallDiscardReasonEmpty
    case TdApi.CallDiscardReasonMissed.CONSTRUCTOR       => CallDiscardReasonMissed
    case TdApi.CallDiscardReasonDeclined.CONSTRUCTOR     => CallDiscardReasonDeclined
    case TdApi.CallDiscardReasonDisconnected.CONSTRUCTOR => CallDiscardReasonDisconnected
    case TdApi.CallDiscardReasonHungUp.CONSTRUCTOR       => CallDiscardReasonHungUp
  }
}
