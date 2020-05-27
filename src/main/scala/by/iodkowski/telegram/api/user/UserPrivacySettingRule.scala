package by.iodkowski.telegram.api.user

import org.drinkless.tdlib.TdApi

/**
  * Represents a single rule for managing privacy settings.
  */
sealed abstract class UserPrivacySettingRule extends Product with Serializable

/**
  * A rule to allow all users to do something.
  */
case object UserPrivacySettingRuleAllowAll extends UserPrivacySettingRule

/**
  * A rule to allow all of a user's contacts to do something.
  */
case object UserPrivacySettingRuleAllowContacts extends UserPrivacySettingRule

/**
  * A rule to allow certain specified users to do something.
  *
  * @param userIds The user identifiers, total number of users in all rules must not exceed 1000.
  */
final case class UserPrivacySettingRuleAllowUsers(userIds: List[Int]) extends UserPrivacySettingRule

private[api] object UserPrivacySettingRuleAllowUsers {
  def fromJava(o: TdApi.UserPrivacySettingRuleAllowUsers): UserPrivacySettingRuleAllowUsers =
    UserPrivacySettingRuleAllowUsers(o.userIds.toList)
}

/**
  * A rule to allow all members of certain specified basic groups and supergroups to doing something.
  *
  * @param chatIds The chat identifiers, total number of chats in all rules must not exceed 20.
  */
final case class UserPrivacySettingRuleAllowChatMembers(chatIds: List[Long]) extends UserPrivacySettingRule

private[api] object UserPrivacySettingRuleAllowChatMembers {
  def fromJava(o: TdApi.UserPrivacySettingRuleAllowChatMembers): UserPrivacySettingRuleAllowChatMembers =
    UserPrivacySettingRuleAllowChatMembers(o.chatIds.toList)
}

/**
  * A rule to restrict all users from doing something.
  */
case object UserPrivacySettingRuleRestrictAll extends UserPrivacySettingRule

/**
  * A rule to restrict all contacts of a user from doing something.
  */
case object UserPrivacySettingRuleRestrictContacts extends UserPrivacySettingRule

/**
  * A rule to restrict all specified users from doing something.
  *
  * @param userIds The user identifiers, total number of users in all rules must not exceed 1000.
  */
final case class UserPrivacySettingRuleRestrictUsers(userIds: List[Int]) extends UserPrivacySettingRule

private[api] object UserPrivacySettingRuleRestrictUsers {
  def fromJava(o: TdApi.UserPrivacySettingRuleRestrictUsers): UserPrivacySettingRuleRestrictUsers =
    UserPrivacySettingRuleRestrictUsers(o.userIds.toList)
}

/**
  * A rule to restrict all members of specified basic groups and supergroups from doing something.
  *
  * @param chatIds The chat identifiers, total number of chats in all rules must not exceed 20.
  */
final case class UserPrivacySettingRuleRestrictChatMembers(chatIds: List[Long]) extends UserPrivacySettingRule

private[api] object UserPrivacySettingRuleRestrictChatMembers {
  def fromJava(o: TdApi.UserPrivacySettingRuleRestrictChatMembers): UserPrivacySettingRuleRestrictChatMembers =
    UserPrivacySettingRuleRestrictChatMembers(o.chatIds.toList)
}

private[api] object UserPrivacySettingRule {
  def fromJava(o: TdApi.UserPrivacySettingRule): UserPrivacySettingRule = o.getConstructor match {
    case TdApi.UserPrivacySettingRuleAllowAll.CONSTRUCTOR         => UserPrivacySettingRuleAllowAll
    case TdApi.UserPrivacySettingRuleRestrictAll.CONSTRUCTOR      => UserPrivacySettingRuleRestrictAll
    case TdApi.UserPrivacySettingRuleRestrictContacts.CONSTRUCTOR => UserPrivacySettingRuleRestrictContacts
    case TdApi.UserPrivacySettingRuleAllowContacts.CONSTRUCTOR    => UserPrivacySettingRuleAllowContacts
    case TdApi.UserPrivacySettingRuleAllowUsers.CONSTRUCTOR =>
      UserPrivacySettingRuleAllowUsers.fromJava(o.asInstanceOf[TdApi.UserPrivacySettingRuleAllowUsers])
    case TdApi.UserPrivacySettingRuleAllowChatMembers.CONSTRUCTOR =>
      UserPrivacySettingRuleAllowChatMembers.fromJava(o.asInstanceOf[TdApi.UserPrivacySettingRuleAllowChatMembers])
    case TdApi.UserPrivacySettingRuleRestrictUsers.CONSTRUCTOR =>
      UserPrivacySettingRuleRestrictUsers.fromJava(o.asInstanceOf[TdApi.UserPrivacySettingRuleRestrictUsers])
    case TdApi.UserPrivacySettingRuleRestrictChatMembers.CONSTRUCTOR =>
      UserPrivacySettingRuleRestrictChatMembers.fromJava(
        o.asInstanceOf[TdApi.UserPrivacySettingRuleRestrictChatMembers]
      )
  }
}
