package by.iodkowski.telegram.api.user

import org.drinkless.tdlib.TdApi

/**
  * Describes the last time the user was online.
  */
sealed abstract class UserStatus extends Product with Serializable

/**
  * The user status was never changed.
  */
case object UserStatusEmpty extends UserStatus

/**
  * The user is online.
  *
  * @param expires Point in time (Unix timestamp) when the user's online status will expire.
  */
final case class UserStatusOnline(expires: Int) extends UserStatus

private[api] object UserStatusOnline {
  def fromJava(o: TdApi.UserStatusOnline): UserStatusOnline = UserStatusOnline(o.expires)
}

/**
  * The user is offline.
  *
  * @param wasOnline Point in time (Unix timestamp) when the user was last online.
  */
final case class UserStatusOffline(wasOnline: Int) extends UserStatus

private[api] object UserStatusOffline {
  def fromJava(o: TdApi.UserStatusOffline): UserStatusOffline = UserStatusOffline(o.wasOnline)
}

/**
  * The user was online recently.
  */
case object UserStatusRecently extends UserStatus

/**
  * The user is offline, but was online last week.
  */
case object UserStatusLastWeek extends UserStatus

/**
  * The user is offline, but was online last month.
  */
case object UserStatusLastMonth extends UserStatus

private[api] object UserStatus {
  def fromJava(o: TdApi.UserStatus): UserStatus = o.getConstructor match {
    case TdApi.UserStatusEmpty.CONSTRUCTOR     => UserStatusEmpty
    case TdApi.UserStatusRecently.CONSTRUCTOR  => UserStatusRecently
    case TdApi.UserStatusLastWeek.CONSTRUCTOR  => UserStatusLastWeek
    case TdApi.UserStatusLastMonth.CONSTRUCTOR => UserStatusLastMonth
    case TdApi.UserStatusOnline.CONSTRUCTOR    => UserStatusOnline.fromJava(o.asInstanceOf[TdApi.UserStatusOnline])
    case TdApi.UserStatusOffline.CONSTRUCTOR   => UserStatusOffline.fromJava(o.asInstanceOf[TdApi.UserStatusOffline])
  }
}
