package by.iodkowski.telegram.api.user

import org.drinkless.tdlib.TdApi
import by.iodkowski.telegram.api.file.File

/**
  * Describes a user profile photo.
  *
  * @param id    Photo identifier; 0 for an empty photo. Can be used to find a photo in a list of userProfilePhotos.
  * @param small A small (160x160) user profile photo. The file can be downloaded only before the photo is changed.
  * @param big   A big (640x640) user profile photo. The file can be downloaded only before the photo is changed.
  */
final case class ProfilePhoto(id: Long, small: File, big: File)

private[api] object ProfilePhoto {
  def fromJava(o: TdApi.ProfilePhoto): ProfilePhoto = ProfilePhoto(o.id, File.fromJava(o.small), File.fromJava(o.big))
}
