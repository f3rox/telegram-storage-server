package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi

/**
  * Describes a list of chats.
  */
sealed abstract class ChatList extends Product with Serializable

/**
  * A main list of chats.
  */
case object ChatListMain extends ChatList

/**
  * A list of chats usually located at the top of the main chat list. Unmuted chats are automatically moved from the Archive to the Main chat list when a new message arrives.
  */
case object ChatListArchive extends ChatList

private[api] object ChatList {
  def fromJava(o: TdApi.ChatList): ChatList = o.getConstructor match {
    case TdApi.ChatListMain.CONSTRUCTOR    => ChatListMain
    case TdApi.ChatListArchive.CONSTRUCTOR => ChatListArchive
  }
}
