package by.iodkowski.telegram.api.user

import org.drinkless.tdlib.TdApi

/**
  * Represents commands supported by a bot.
  *
  * @param command     Text of the bot command.
  * @param description Description of the bot command.
  */
final case class BotCommand(command: String, description: String)

private[api] object BotCommand {
  def fromJava(o: TdApi.BotCommand): BotCommand = BotCommand(o.command, o.description)
}
