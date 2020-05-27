package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi

/**
  * Describes a chat located nearby.
  *
  * @param chatId   Chat identifier.
  * @param distance Distance to the chat location in meters.
  */
final case class ChatNearby(chatId: Long, distance: Int)

private[api] object ChatNearby {
  def fromJava(o: TdApi.ChatNearby): ChatNearby = ChatNearby(o.chatId, o.distance)
}
