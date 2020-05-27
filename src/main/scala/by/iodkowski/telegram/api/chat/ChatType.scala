package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi

/**
  * Describes the type of a chat.
  */
sealed abstract class ChatType extends Product with Serializable

/**
  * An ordinary chat with a user.
  *
  * @param userId User identifier.
  */
final case class ChatTypePrivate(userId: Int) extends ChatType

private[api] object ChatTypePrivate {
  def fromJava(o: TdApi.ChatTypePrivate): ChatTypePrivate = ChatTypePrivate(o.userId)
}

/**
  * A basic group (i.e., a chat with 0-200 other users).
  *
  * @param basicGroupId Basic group identifier.
  */
final case class ChatTypeBasicGroup(basicGroupId: Int) extends ChatType

private[api] object ChatTypeBasicGroup {
  def fromJava(o: TdApi.ChatTypeBasicGroup): ChatTypeBasicGroup = ChatTypeBasicGroup(o.basicGroupId)
}

/**
  * A supergroup (i.e. a chat with up to GetOption(&quot;supergroup_max_size&quot;) other users), or channel (with unlimited members).
  *
  * @param supergroupId Supergroup or channel identifier.
  * @param isChannel    True, if the supergroup is a channel.
  */
final case class ChatTypeSupergroup(supergroupId: Int, isChannel: Boolean) extends ChatType

private[api] object ChatTypeSupergroup {
  def fromJava(o: TdApi.ChatTypeSupergroup): ChatTypeSupergroup = ChatTypeSupergroup(o.supergroupId, o.isChannel)
}

/**
  * A secret chat with a user.
  *
  * @param secretChatId Secret chat identifier.
  * @param userId       User identifier of the secret chat peer.
  */
final case class ChatTypeSecret(secretChatId: Int, userId: Int) extends ChatType

private[api] object ChatTypeSecret {
  def fromJava(o: TdApi.ChatTypeSecret): ChatTypeSecret = ChatTypeSecret(o.secretChatId, o.userId)
}

private[api] object ChatType {
  def fromJava(o: TdApi.ChatType): ChatType = o.getConstructor match {
    case TdApi.ChatTypePrivate.CONSTRUCTOR    => ChatTypePrivate.fromJava(o.asInstanceOf[TdApi.ChatTypePrivate])
    case TdApi.ChatTypeBasicGroup.CONSTRUCTOR => ChatTypeBasicGroup.fromJava(o.asInstanceOf[TdApi.ChatTypeBasicGroup])
    case TdApi.ChatTypeSupergroup.CONSTRUCTOR => ChatTypeSupergroup.fromJava(o.asInstanceOf[TdApi.ChatTypeSupergroup])
    case TdApi.ChatTypeSecret.CONSTRUCTOR     => ChatTypeSecret.fromJava(o.asInstanceOf[TdApi.ChatTypeSecret])
  }
}
