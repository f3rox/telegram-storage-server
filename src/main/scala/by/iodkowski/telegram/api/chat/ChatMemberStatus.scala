package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi

/**
  * Provides information about the status of a member in a chat.
  */
sealed abstract class ChatMemberStatus extends Product with Serializable

/**
  * The user is the owner of a chat and has all the administrator privileges.
  *
  * @param customTitle A custom title of the owner; 0-16 characters without emojis; applicable to supergroups only.
  * @param isMember    True, if the user is a member of the chat.
  */
final case class ChatMemberStatusCreator(customTitle: String, isMember: Boolean) extends ChatMemberStatus

private[api] object ChatMemberStatusCreator {
  def fromJava(o: TdApi.ChatMemberStatusCreator): ChatMemberStatusCreator =
    ChatMemberStatusCreator(o.customTitle, o.isMember)
}

/**
  * The user is a member of a chat and has some additional privileges. In basic groups, administrators can edit and delete messages sent by others, add new members, and ban unprivileged members. In supergroups and channels, there are more detailed options for administrator privileges.
  *
  * @param customTitle        A custom title of the administrator; 0-16 characters without emojis; applicable to supergroups only.
  * @param canBeEdited        True, if the current user can edit the administrator privileges for the called user.
  * @param canChangeInfo      True, if the administrator can change the chat title, photo, and other settings.
  * @param canPostMessages    True, if the administrator can create channel posts; applicable to channels only.
  * @param canEditMessages    True, if the administrator can edit messages of other users and pin messages; applicable to channels only.
  * @param canDeleteMessages  True, if the administrator can delete messages of other users.
  * @param canInviteUsers     True, if the administrator can invite new users to the chat.
  * @param canRestrictMembers True, if the administrator can restrict, ban, or unban chat members.
  * @param canPinMessages     True, if the administrator can pin messages; applicable to groups only.
  * @param canPromoteMembers  True, if the administrator can add new administrators with a subset of their own privileges or demote administrators that were directly or indirectly promoted by them.
  */
final case class ChatMemberStatusAdministrator(
  customTitle: String,
  canBeEdited: Boolean,
  canChangeInfo: Boolean,
  canPostMessages: Boolean,
  canEditMessages: Boolean,
  canDeleteMessages: Boolean,
  canInviteUsers: Boolean,
  canRestrictMembers: Boolean,
  canPinMessages: Boolean,
  canPromoteMembers: Boolean
) extends ChatMemberStatus

private[api] object ChatMemberStatusAdministrator {
  def fromJava(o: TdApi.ChatMemberStatusAdministrator): ChatMemberStatusAdministrator =
    ChatMemberStatusAdministrator(
      o.customTitle,
      o.canBeEdited,
      o.canChangeInfo,
      o.canPostMessages,
      o.canEditMessages,
      o.canDeleteMessages,
      o.canInviteUsers,
      o.canRestrictMembers,
      o.canPinMessages,
      o.canPromoteMembers
    )
}

/**
  * The user is a member of a chat, without any additional privileges or restrictions.
  */
case object ChatMemberStatusMember extends ChatMemberStatus

/**
  * The user is under certain restrictions in the chat. Not supported in basic groups and channels.
  *
  * @param isMember            True, if the user is a member of the chat.
  * @param restrictedUntilDate Point in time (Unix timestamp) when restrictions will be lifted from the user; 0 if never. If the user is restricted for more than 366 days or for less than 30 seconds from the current time, the user is considered to be restricted forever.
  * @param permissions         User permissions in the chat.
  */
final case class ChatMemberStatusRestricted(isMember: Boolean, restrictedUntilDate: Int, permissions: ChatPermissions)
    extends ChatMemberStatus

private[api] object ChatMemberStatusRestricted {
  def fromJava(o: TdApi.ChatMemberStatusRestricted): ChatMemberStatusRestricted =
    ChatMemberStatusRestricted(o.isMember, o.restrictedUntilDate, ChatPermissions.fromJava(o.permissions))
}

/**
  * The user is not a chat member.
  */
case object ChatMemberStatusLeft extends ChatMemberStatus

/**
  * The user was banned (and hence is not a member of the chat). Implies the user can't return to the chat or view messages.
  *
  * @param bannedUntilDate Point in time (Unix timestamp) when the user will be unbanned; 0 if never. If the user is banned for more than 366 days or for less than 30 seconds from the current time, the user is considered to be banned forever.
  */
final case class ChatMemberStatusBanned(bannedUntilDate: Int) extends ChatMemberStatus

private[api] object ChatMemberStatusBanned {
  def fromJava(o: TdApi.ChatMemberStatusBanned): ChatMemberStatusBanned = ChatMemberStatusBanned(o.bannedUntilDate)
}

private[api] object ChatMemberStatus {
  def fromJava(o: TdApi.ChatMemberStatus): ChatMemberStatus = o.getConstructor match {
    case TdApi.ChatMemberStatusLeft.CONSTRUCTOR   => ChatMemberStatusLeft
    case TdApi.ChatMemberStatusMember.CONSTRUCTOR => ChatMemberStatusMember
    case TdApi.ChatMemberStatusCreator.CONSTRUCTOR =>
      ChatMemberStatusCreator.fromJava(o.asInstanceOf[TdApi.ChatMemberStatusCreator])
    case TdApi.ChatMemberStatusAdministrator.CONSTRUCTOR =>
      ChatMemberStatusAdministrator.fromJava(o.asInstanceOf[TdApi.ChatMemberStatusAdministrator])
    case TdApi.ChatMemberStatusRestricted.CONSTRUCTOR =>
      ChatMemberStatusRestricted.fromJava(o.asInstanceOf[TdApi.ChatMemberStatusRestricted])
    case TdApi.ChatMemberStatusBanned.CONSTRUCTOR =>
      ChatMemberStatusBanned.fromJava(o.asInstanceOf[TdApi.ChatMemberStatusBanned])
  }
}
