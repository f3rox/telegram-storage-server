package by.iodkowski.telegram.api.user

import org.drinkless.tdlib.TdApi

/**
  * A list of privacy rules. Rules are matched in the specified order. The first matched rule defines the privacy setting for a given user. If no rule matches, the action is not allowed.
  *
  * @param rules A list of rules.
  */
final case class UserPrivacySettingRules(rules: List[UserPrivacySettingRule])

private[api] object UserPrivacySettingRules {
  def fromJava(o: TdApi.UserPrivacySettingRules): UserPrivacySettingRules =
    UserPrivacySettingRules(o.rules.map(UserPrivacySettingRule.fromJava).toList)
}
