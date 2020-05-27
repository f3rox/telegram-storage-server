package by.iodkowski.telegram.api.user

import org.drinkless.tdlib.TdApi

/**
  * Provides information about a bot and its supported commands.
  *
  * @param description Long description shown on the user info page.
  * @param commands    A list of commands supported by the bot.
  */
final case class BotInfo(description: String, commands: List[BotCommand])

private[api] object BotInfo {
  def fromJava(o: TdApi.BotInfo): BotInfo = BotInfo(o.description, o.commands.map(BotCommand.fromJava).toList)
}
