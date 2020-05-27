package by.iodkowski.telegram.api.notification

import org.drinkless.tdlib.TdApi

/**
  * Contains information about a notification.
  *
  * @param id       Unique persistent identifier of this notification.
  * @param date     Notification date.
  * @param isSilent True, if the notification was initially silent.
  * @param type     Notification type.
  */
final case class Notification(id: Int, date: Int, isSilent: Boolean, `type`: NotificationType)

private[api] object Notification {
  def fromJava(o: TdApi.Notification): Notification =
    Notification(o.id, o.date, o.isSilent, NotificationType.fromJava(o.`type`))
}
