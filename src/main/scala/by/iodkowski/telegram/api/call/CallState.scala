package by.iodkowski.telegram.api.call

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.Error

/**
  * Describes the current call state.
  */
sealed abstract class CallState extends Product with Serializable

/**
  * The call is pending, waiting to be accepted by a user.
  *
  * @param isCreated  True, if the call has already been created by the server.
  * @param isReceived True, if the call has already been received by the other party.
  */
final case class CallStatePending(isCreated: Boolean, isReceived: Boolean) extends CallState

private[api] object CallStatePending {
  def fromJava(o: TdApi.CallStatePending): CallStatePending = CallStatePending(o.isCreated, o.isReceived)
}

/**
  * The call has been answered and encryption keys are being exchanged.
  */
case object CallStateExchangingKeys extends CallState

/**
  * The call is ready to use.
  *
  * @param protocol      Call protocols supported by the peer.
  * @param connections   Available UDP reflectors.
  * @param config        A JSON-encoded call config.
  * @param encryptionKey Call encryption key.
  * @param emojis        Encryption key emojis fingerprint.
  * @param allowP2p      True, if peer-to-peer connection is allowed by users privacy settings.
  */
final case class CallStateReady(
  protocol: CallProtocol,
  connections: List[CallConnection],
  config: String,
  encryptionKey: List[Byte],
  emojis: List[String],
  allowP2p: Boolean
) extends CallState

private[api] object CallStateReady {
  def fromJava(o: TdApi.CallStateReady): CallStateReady =
    CallStateReady(
      CallProtocol.fromJava(o.protocol),
      o.connections.map(CallConnection.fromJava).toList,
      o.config,
      o.encryptionKey.toList,
      o.emojis.toList,
      o.allowP2p
    )
}

/**
  * The call is hanging up after discardCall has been called.
  */
case object CallStateHangingUp extends CallState

/**
  * The call has ended successfully.
  *
  * @param reason               The reason, why the call has ended.
  * @param needRating           True, if the call rating should be sent to the server.
  * @param needDebugInformation True, if the call debug information should be sent to the server.
  */
final case class CallStateDiscarded(reason: CallDiscardReason, needRating: Boolean, needDebugInformation: Boolean)
    extends CallState

private[api] object CallStateDiscarded {
  def fromJava(o: TdApi.CallStateDiscarded): CallStateDiscarded =
    CallStateDiscarded(CallDiscardReason.fromJava(o.reason), o.needRating, o.needDebugInformation)
}

/**
  * The call has ended with an error.
  *
  * @param error Error. An error with the code 4005000 will be returned if an outgoing call is missed because of an expired timeout.
  */
final case class CallStateError(error: Error) extends CallState

private[api] object CallStateError {
  def fromJava(o: TdApi.CallStateError): CallStateError = CallStateError(Error.fromJava(o.error))

}

private[api] object CallState {
  def fromJava(o: TdApi.CallState): CallState = o.getConstructor match {
    case TdApi.CallStateHangingUp.CONSTRUCTOR      => CallStateHangingUp
    case TdApi.CallStateExchangingKeys.CONSTRUCTOR => CallStateExchangingKeys
    case TdApi.CallStatePending.CONSTRUCTOR        => CallStatePending.fromJava(o.asInstanceOf[TdApi.CallStatePending])
    case TdApi.CallStateReady.CONSTRUCTOR          => CallStateReady.fromJava(o.asInstanceOf[TdApi.CallStateReady])
    case TdApi.CallStateDiscarded.CONSTRUCTOR      => CallStateDiscarded.fromJava(o.asInstanceOf[TdApi.CallStateDiscarded])
    case TdApi.CallStateError.CONSTRUCTOR          => CallStateError.fromJava(o.asInstanceOf[TdApi.CallStateError])
  }
}
