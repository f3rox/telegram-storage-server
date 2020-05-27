package by.iodkowski.telegram.api.poll

import org.drinkless.tdlib.TdApi

/**
  * Describes the type of a poll.
  */
sealed abstract class PollType extends Product with Serializable

/**
  * A regular poll.
  *
  * @param allowMultipleAnswers True, if multiple answer options can be chosen simultaneously.
  */
final case class PollTypeRegular(allowMultipleAnswers: Boolean) extends PollType

private[api] object PollTypeRegular {
  def fromJava(o: TdApi.PollTypeRegular): PollTypeRegular = PollTypeRegular(o.allowMultipleAnswers)
}

/**
  * A poll in quiz mode, which has exactly one correct answer option and can be answered only once.
  *
  * @param correctOptionId 0-based identifier of the correct answer option; -1 for a yet unanswered poll.
  */
final case class PollTypeQuiz(correctOptionId: Int) extends PollType

private[api] object PollTypeQuiz {
  def fromJava(o: TdApi.PollTypeQuiz): PollTypeQuiz = PollTypeQuiz(o.correctOptionId)
}

private[api] object PollType {
  def fromJava(o: TdApi.PollType): PollType = o.getConstructor match {
    case TdApi.PollTypeRegular.CONSTRUCTOR => PollTypeRegular.fromJava(o.asInstanceOf[TdApi.PollTypeRegular])
    case TdApi.PollTypeQuiz.CONSTRUCTOR    => PollTypeQuiz.fromJava(o.asInstanceOf[TdApi.PollTypeQuiz])
  }
}
