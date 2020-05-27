package by.iodkowski.telegram.api.chat

import org.drinkless.tdlib.TdApi

/**
  * Describes actions that a user is allowed to take in a chat.
  *
  * @param canSendMessages       True, if the user can send text messages, contacts, locations, and venues.
  * @param canSendMediaMessages  True, if the user can send audio files, documents, photos, videos, video notes, and voice notes. Implies canSendMessages permissions.
  * @param canSendPolls          True, if the user can send polls. Implies canSendMessages permissions.
  * @param canSendOtherMessages  True, if the user can send animations, games, and stickers and use inline bots. Implies canSendMessages permissions.
  * @param canAddWebPagePreviews True, if the user may add a web page preview to their messages. Implies canSendMessages permissions.
  * @param canChangeInfo         True, if the user can change the chat title, photo, and other settings.
  * @param canInviteUsers        True, if the user can invite new users to the chat.
  * @param canPinMessages        True, if the user can pin messages.
  */
final case class ChatPermissions(
  canSendMessages: Boolean,
  canSendMediaMessages: Boolean,
  canSendPolls: Boolean,
  canSendOtherMessages: Boolean,
  canAddWebPagePreviews: Boolean,
  canChangeInfo: Boolean,
  canInviteUsers: Boolean,
  canPinMessages: Boolean
)

private[api] object ChatPermissions {
  def fromJava(o: TdApi.ChatPermissions): ChatPermissions =
    ChatPermissions(
      o.canSendMessages,
      o.canSendMediaMessages,
      o.canSendPolls,
      o.canSendOtherMessages,
      o.canAddWebPagePreviews,
      o.canChangeInfo,
      o.canInviteUsers,
      o.canPinMessages
    )
}
