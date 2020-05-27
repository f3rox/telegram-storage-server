package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.user.BotInfo

/**
  * A user with information about joining/leaving a chat.
  *
  * @param userId         User identifier of the chat member.
  * @param inviterUserId  Identifier of a user that invited/promoted/banned this member in the chat; 0 if unknown.
  * @param joinedChatDate Point in time (Unix timestamp) when the user joined a chat.
  * @param status         Status of the member in the chat.
  * @param botInfo        If the user is a bot, information about the bot; may be None. Can be None even for a bot if the bot is not a chat member.
  */
final case class ChatMember(
  userId: Int,
  inviterUserId: Int,
  joinedChatDate: Int,
  status: ChatMemberStatus,
  botInfo: Option[BotInfo]
)

private[api] object ChatMember {
  def fromJava(o: TdApi.ChatMember): ChatMember =
    ChatMember(
      o.userId,
      o.inviterUserId,
      o.joinedChatDate,
      ChatMemberStatus.fromJava(o.status),
      Option(o.botInfo).map(BotInfo.fromJava)
    )
}
