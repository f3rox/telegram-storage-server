package by.iodkowski.telegram.api.user

import org.drinkless.tdlib.TdApi

/**
  * Represents the type of a user. The following types are possible: regular users, deleted users and bots.
  */
sealed abstract class UserType extends Product with Serializable

/**
  * A regular user.
  */
case object UserTypeRegular extends UserType

/**
  * A deleted user or deleted bot. No information on the user besides the user identifier is available. It is not possible to perform any active actions on this type of user.
  */
case object UserTypeDeleted extends UserType

/**
  * A bot (see https://core.telegram.org/bots).
  *
  * @param canJoinGroups           True, if the bot can be invited to basic group and supergroup chats.
  * @param canReadAllGroupMessages True, if the bot can read all messages in basic group or supergroup chats and not just those addressed to the bot. In private and channel chats a bot can always read all messages.
  * @param isInline                True, if the bot supports inline queries.
  * @param inlineQueryPlaceholder  Placeholder for inline queries (displayed on the client input field).
  * @param needLocation            True, if the location of the user should be sent with every inline query to this bot.
  */
final case class UserTypeBot(
  canJoinGroups: Boolean,
  canReadAllGroupMessages: Boolean,
  isInline: Boolean,
  inlineQueryPlaceholder: String,
  needLocation: Boolean
) extends UserType

private[api] object UserTypeBot {
  def fromJava(o: TdApi.UserTypeBot): UserTypeBot =
    UserTypeBot(o.canJoinGroups, o.canReadAllGroupMessages, o.isInline, o.inlineQueryPlaceholder, o.needLocation)
}

/**
  * No information on the user besides the user identifier is available, yet this user has not been deleted. This object is extremely rare and must be handled like a deleted user. It is not possible to perform any actions on users of this type.
  */
case object UserTypeUnknown extends UserType

private[api] object UserType {
  def fromJava(o: TdApi.UserType): UserType = o.getConstructor match {
    case TdApi.UserTypeRegular.CONSTRUCTOR => UserTypeRegular
    case TdApi.UserTypeDeleted.CONSTRUCTOR => UserTypeDeleted
    case TdApi.UserTypeUnknown.CONSTRUCTOR => UserTypeUnknown
    case TdApi.UserTypeBot.CONSTRUCTOR     => UserTypeBot.fromJava(o.asInstanceOf[TdApi.UserTypeBot])
  }
}
