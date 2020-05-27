package by.iodkowski.telegram.api.file

import org.drinkless.tdlib.TdApi

/**
  * File with the date it was uploaded.
  *
  * @param file The file.
  * @param date Point in time (Unix timestamp) when the file was uploaded.
  */
final case class DatedFile(file: File, date: Int)

private[api] object DatedFile {
  def fromJava(o: TdApi.DatedFile): DatedFile = DatedFile(File.fromJava(o.file), o.date)
}
