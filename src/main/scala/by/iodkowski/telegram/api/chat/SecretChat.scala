package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi

/**
  * Represents a secret chat.
  *
  * @param id         Secret chat identifier.
  * @param userId     Identifier of the chat partner.
  * @param state      State of the secret chat.
  * @param isOutbound True, if the chat was created by the current user; otherwise false.
  * @param ttl        Current message Time To Live setting (self-destruct timer) for the chat, in seconds.
  * @param keyHash    Hash of the currently used key for comparison with the hash of the chat partner's key. This is a string of 36 little-endian bytes, which must be split into groups of 2 bits, each denoting a pixel of one of 4 colors FFFFFF, D5E6F3, 2D5775, and 2F99C9. The pixels must be used to make a 12x12 square image filled from left to right, top to bottom. Alternatively, the first 32 bytes of the hash can be converted to the hexadecimal format and printed as 32 2-digit hex numbers.
  * @param layer      Secret chat layer; determines features supported by the other client. Video notes are supported if the layer &gt;= 66; nested text entities and underline and strikethrough entities are supported if the layer &gt;= 101.
  */
final case class SecretChat(
  id: Int,
  userId: Int,
  state: SecretChatState,
  isOutbound: Boolean,
  ttl: Int,
  keyHash: List[Byte],
  layer: Int
)

private[api] object SecretChat {
  def fromJava(o: TdApi.SecretChat): SecretChat =
    SecretChat(o.id, o.userId, SecretChatState.fromJava(o.state), o.isOutbound, o.ttl, o.keyHash.toList, o.layer)
}
