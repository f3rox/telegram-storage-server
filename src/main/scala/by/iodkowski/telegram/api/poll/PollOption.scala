package by.iodkowski.telegram.api.poll

import org.drinkless.tdlib.TdApi

/**
  * Describes one answer option of a poll.
  *
  * @param text           Option text, 1-100 characters.
  * @param voterCount     Number of voters for this option, available only for closed or voted polls.
  * @param votePercentage The percentage of votes for this option, 0-100.
  * @param isChosen       True, if the option was chosen by the user.
  * @param isBeingChosen  True, if the option is being chosen by a pending setPollAnswer request.
  */
final case class PollOption(
  text: String,
  voterCount: Int,
  votePercentage: Int,
  isChosen: Boolean,
  isBeingChosen: Boolean
)

private[api] object PollOption {
  def fromJava(o: TdApi.PollOption): PollOption =
    PollOption(o.text, o.voterCount, o.votePercentage, o.isChosen, o.isBeingChosen)
}
