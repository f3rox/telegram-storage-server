package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi

/**
  * Describes the current secret chat state.
  */
sealed abstract class SecretChatState extends Product with Serializable

/**
  * The secret chat is not yet created; waiting for the other user to get online.
  */
case object SecretChatStatePending extends SecretChatState

/**
  * The secret chat is ready to use.
  */
case object SecretChatStateReady extends SecretChatState

/**
  * The secret chat is closed.
  */
case object SecretChatStateClosed extends SecretChatState

private[api] object SecretChatState {
  def fromJava(o: TdApi.SecretChatState): SecretChatState = o.getConstructor match {
    case TdApi.SecretChatStatePending.CONSTRUCTOR => SecretChatStatePending
    case TdApi.SecretChatStateReady.CONSTRUCTOR   => SecretChatStateReady
    case TdApi.SecretChatStateClosed.CONSTRUCTOR  => SecretChatStateClosed
  }
}
