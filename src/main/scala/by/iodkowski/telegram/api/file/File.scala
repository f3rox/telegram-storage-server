package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi

/**
  * Represents a file.
  *
  * @param id           Unique file identifier.
  * @param size         File size; 0 if unknown.
  * @param expectedSize Expected file size in case the exact file size is unknown, but an approximate size is known. Can be used to show download/upload progress.
  * @param local        Information about the local copy of the file.
  * @param remote       Information about the remote copy of the file.
  */
final case class File(id: Int, size: Int, expectedSize: Int, local: LocalFile, remote: RemoteFile)

private[api] object File {
  def fromJava(o: TdApi.File): File =
    File(o.id, o.size, o.expectedSize, LocalFile.fromJava(o.local), RemoteFile.fromJava(o.remote))
}
