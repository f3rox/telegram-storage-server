package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Contains parameters for TDLib initialization.
  *
  * @param useTestDc              If set to true, the Telegram test environment will be used instead of the production environment.
  * @param databaseDirectory      The path to the directory for the persistent database; if empty, the current working directory will be used.
  * @param filesDirectory         The path to the directory for storing files; if empty, databaseDirectory will be used.
  * @param useFileDatabase        If set to true, information about downloaded and uploaded files will be saved between application restarts.
  * @param useChatInfoDatabase    If set to true, the library will maintain a cache of users, basic groups, supergroups, channels and secret chats. Implies useFileDatabase.
  * @param useMessageDatabase     If set to true, the library will maintain a cache of chats and messages. Implies useChatInfoDatabase.
  * @param useSecretChats         If set to true, support for secret chats will be enabled.
  * @param apiId                  Application identifier for Telegram API access, which can be obtained at https://my.telegram.org.
  * @param apiHash                Application identifier hash for Telegram API access, which can be obtained at https://my.telegram.org.
  * @param systemLanguageCode     IETF language tag of the user's operating system language; must be non-empty.
  * @param deviceModel            Model of the device the application is being run on; must be non-empty.
  * @param systemVersion          Version of the operating system the application is being run on; must be non-empty.
  * @param applicationVersion     Application version; must be non-empty.
  * @param enableStorageOptimizer If set to true, old files will automatically be deleted.
  * @param ignoreFileNames        If set to true, original file names will be ignored. Otherwise, downloaded files will be saved under names as close as possible to the original name.
  */
final case class TdlibParameters(
  useTestDc: Boolean,
  databaseDirectory: String,
  filesDirectory: String,
  useFileDatabase: Boolean,
  useChatInfoDatabase: Boolean,
  useMessageDatabase: Boolean,
  useSecretChats: Boolean,
  apiId: Int,
  apiHash: String,
  systemLanguageCode: String,
  deviceModel: String,
  systemVersion: String,
  applicationVersion: String,
  enableStorageOptimizer: Boolean,
  ignoreFileNames: Boolean
) {
  private[api] def toJava: TdApi.TdlibParameters =
    new TdApi.TdlibParameters(
      useTestDc,
      databaseDirectory,
      filesDirectory,
      useFileDatabase,
      useChatInfoDatabase,
      useMessageDatabase,
      useSecretChats,
      apiId,
      apiHash,
      systemLanguageCode,
      deviceModel,
      systemVersion,
      applicationVersion,
      enableStorageOptimizer,
      ignoreFileNames
    )
}
