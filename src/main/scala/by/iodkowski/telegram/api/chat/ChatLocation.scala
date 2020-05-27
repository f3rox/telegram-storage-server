package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.Location

/**
  * Represents a location to which a chat is connected.
  *
  * @param location The location.
  * @param address  Location address; 1-64 characters, as defined by the chat owner.
  */
final case class ChatLocation(location: Location, address: String)

private[api] object ChatLocation {
  def fromJava(o: TdApi.ChatLocation): ChatLocation = ChatLocation(Location.fromJava(o.location), o.address)
}
