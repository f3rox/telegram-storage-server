package by.iodkowski.telegram.api.notification

import org.drinkless.tdlib.TdApi

/**
  * Describes a group of notifications.
  *
  * @param id            Unique persistent auto-incremented from 1 identifier of the notification group.
  * @param type          Type of the group.
  * @param chatId        Identifier of a chat to which all notifications in the group belong.
  * @param totalCount    Total number of active notifications in the group.
  * @param notifications The list of active notifications.
  */
final case class NotificationGroup(
  id: Int,
  `type`: NotificationGroupType,
  chatId: Long,
  totalCount: Int,
  notifications: List[Notification]
)

private[api] object NotificationGroup {
  def fromJava(o: TdApi.NotificationGroup): NotificationGroup =
    NotificationGroup(
      o.id,
      NotificationGroupType.fromJava(o.`type`),
      o.chatId,
      o.totalCount,
      o.notifications.map(Notification.fromJava).toList
    )
}
