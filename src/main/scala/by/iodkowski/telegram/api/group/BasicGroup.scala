package by.iodkowski.telegram.api.group

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.chat.ChatMemberStatus

/**
  * Represents a basic group of 0-200 users (must be upgraded to a supergroup to accommodate more than 200 users).
  *
  * @param id                     Group identifier.
  * @param memberCount            Number of members in the group.
  * @param status                 Status of the current user in the group.
  * @param isActive               True, if the group is active.
  * @param upgradedToSupergroupId Identifier of the supergroup to which this group was upgraded; 0 if none.
  */
final case class BasicGroup(
  id: Int,
  memberCount: Int,
  status: ChatMemberStatus,
  isActive: Boolean,
  upgradedToSupergroupId: Int
)

private[api] object BasicGroup {
  def fromJava(o: TdApi.BasicGroup): BasicGroup =
    BasicGroup(o.id, o.memberCount, ChatMemberStatus.fromJava(o.status), o.isActive, o.upgradedToSupergroupId)
}
