package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Represents a payload of a callback query.
  */
sealed abstract class CallbackQueryPayload extends Product with Serializable

/**
  * The payload from a general callback button.
  *
  * @param data Data that was attached to the callback button.
  */
final case class CallbackQueryPayloadData(data: List[Byte]) extends CallbackQueryPayload

private[api] object CallbackQueryPayloadData {
  def fromJava(o: TdApi.CallbackQueryPayloadData): CallbackQueryPayloadData = CallbackQueryPayloadData(o.data.toList)
}

/**
  * The payload from a game callback button.
  *
  * @param gameShortName A short name of the game that was attached to the callback button.
  */
final case class CallbackQueryPayloadGame(gameShortName: String) extends CallbackQueryPayload

private[api] object CallbackQueryPayloadGame {
  def fromJava(o: TdApi.CallbackQueryPayloadGame): CallbackQueryPayloadGame = CallbackQueryPayloadGame(o.gameShortName)
}

private[api] object CallbackQueryPayload {
  def fromJava(o: TdApi.CallbackQueryPayload): CallbackQueryPayload = o.getConstructor match {
    case TdApi.CallbackQueryPayloadData.CONSTRUCTOR =>
      CallbackQueryPayloadData.fromJava(o.asInstanceOf[TdApi.CallbackQueryPayloadData])
    case TdApi.CallbackQueryPayloadGame.CONSTRUCTOR =>
      CallbackQueryPayloadGame.fromJava(o.asInstanceOf[TdApi.CallbackQueryPayloadGame])
  }
}
