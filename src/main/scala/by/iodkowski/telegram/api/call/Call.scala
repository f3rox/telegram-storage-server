package by.iodkowski.telegram.api.call

import org.drinkless.tdlib.TdApi

/**
  * Describes a call.
  *
  * @param id         Call identifier, not persistent.
  * @param userId     Peer user identifier.
  * @param isOutgoing True, if the call is outgoing.
  * @param state      Call state.
  */
final case class Call(id: Int, userId: Int, isOutgoing: Boolean, state: CallState)

private[api] object Call {
  def fromJava(o: TdApi.Call): Call = Call(o.id, o.userId, o.isOutgoing, CallState.fromJava(o.state))
}
