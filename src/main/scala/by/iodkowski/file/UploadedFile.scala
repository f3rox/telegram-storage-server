package by.iodkowski.file

import java.util.UUID

final case class UploadedFile(id: String, userId: UUID, fileName: String)
