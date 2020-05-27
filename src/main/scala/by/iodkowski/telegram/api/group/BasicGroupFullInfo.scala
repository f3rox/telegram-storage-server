package by.iodkowski.telegram.api.group

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.chat.ChatMember

/**
  * Contains full information about a basic group.
  *
  * @param description   Group description.
  * @param creatorUserId User identifier of the creator of the group; 0 if unknown.
  * @param members       Group members.
  * @param inviteLink    Invite link for this group; available only after it has been generated at least once and only for the group creator.
  */
final case class BasicGroupFullInfo(
  description: String,
  creatorUserId: Int,
  members: List[ChatMember],
  inviteLink: String
)

private[api] object BasicGroupFullInfo {
  def fromJava(o: TdApi.BasicGroupFullInfo): BasicGroupFullInfo =
    BasicGroupFullInfo(o.description, o.creatorUserId, o.members.map(ChatMember.fromJava).toList, o.inviteLink)
}
