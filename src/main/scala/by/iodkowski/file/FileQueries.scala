package by.iodkowski.file

import skunk._
import skunk.codec.all._
import skunk.implicits._

object FileQueries {

  val insertFile: Command[UploadedFile] =
    sql"""
         INSERT INTO files
         VALUES (${varchar(50)}, $uuid, ${varchar(70)})
    """
      .command
      .gcontramap[UploadedFile]
}
