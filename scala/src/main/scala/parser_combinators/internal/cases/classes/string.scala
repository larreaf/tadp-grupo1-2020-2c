package parser_combinators.internal.cases.classes

import scala.util.Try
import parser_combinators.internal.mixins.Parser

case class string(expected: String) extends Parser[String] {
  override def result(source: String): Try[String] = source match {
    case string: String if string.startsWith(expected) => Try(expected)
    case _ => Try(throw new Error)
  }

  override def remnant(source: String): String = {
    source.substring(expected.length)
  }
}
