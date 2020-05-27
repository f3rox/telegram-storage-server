package by.iodkowski.telegram.api

import org.drinkless.tdlib.TdApi

/**
  * Represents the value of an option.
  */
sealed abstract class OptionValue extends Product with Serializable

/**
  * Represents a boolean option.
  *
  * @param value The value of the option.
  */
final case class OptionValueBoolean(value: Boolean) extends OptionValue

private[api] object OptionValueBoolean {
  def fromJava(o: TdApi.OptionValueBoolean): OptionValueBoolean = OptionValueBoolean(o.value)
}

/**
  * Represents an unknown option or an option which has a default value.
  */
case object OptionValueEmpty extends OptionValue

/**
  * Represents an integer option.
  *
  * @param value The value of the option.
  */
final case class OptionValueInteger(value: Int) extends OptionValue

private[api] object OptionValueInteger {
  def fromJava(o: TdApi.OptionValueInteger): OptionValueInteger = OptionValueInteger(o.value)
}

/**
  * Represents a string option.
  *
  * @param value The value of the option.
  */
final case class OptionValueString(value: String) extends OptionValue

private[api] object OptionValueString {
  def fromJava(o: TdApi.OptionValueString): OptionValueString = OptionValueString(o.value)
}

private[api] object OptionValue {
  def fromJava(o: TdApi.OptionValue): OptionValue = o.getConstructor match {
    case TdApi.OptionValueBoolean.CONSTRUCTOR => OptionValueBoolean.fromJava(o.asInstanceOf[TdApi.OptionValueBoolean])
    case TdApi.OptionValueEmpty.CONSTRUCTOR   => OptionValueEmpty
    case TdApi.OptionValueInteger.CONSTRUCTOR => OptionValueInteger.fromJava(o.asInstanceOf[TdApi.OptionValueInteger])
    case TdApi.OptionValueString.CONSTRUCTOR  => OptionValueString.fromJava(o.asInstanceOf[TdApi.OptionValueString])
  }
}
