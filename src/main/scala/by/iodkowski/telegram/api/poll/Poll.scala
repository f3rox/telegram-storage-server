package by.iodkowski.telegram.api.poll

import org.drinkless.tdlib.TdApi

/**
  * Describes a poll.
  *
  * @param id                 Unique poll identifier.
  * @param question           Poll question, 1-255 characters.
  * @param options            List of poll answer options.
  * @param totalVoterCount    Total number of voters, participating in the poll.
  * @param recentVoterUserIds User identifiers of recent voters, if the poll is non-anonymous.
  * @param isAnonymous        True, if the poll is anonymous.
  * @param type               Type of the poll.
  * @param isClosed           True, if the poll is closed.
  */
final case class Poll(
  id: Long,
  question: String,
  options: List[PollOption],
  totalVoterCount: Int,
  recentVoterUserIds: List[Int],
  isAnonymous: Boolean,
  `type`: PollType,
  isClosed: Boolean
)

private[api] object Poll {
  def fromJava(o: TdApi.Poll): Poll =
    Poll(
      o.id,
      o.question,
      o.options.map(PollOption.fromJava).toList,
      o.totalVoterCount,
      o.recentVoterUserIds.toList,
      o.isAnonymous,
      PollType.fromJava(o.`type`),
      o.isClosed
    )
}
