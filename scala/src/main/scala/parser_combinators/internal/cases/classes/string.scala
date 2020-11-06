package parser_combinators.internal.cases.classes

import scala.util.Try
import parser_combinators.internal.mixins.Parser

case class string(expected: String) extends Parser[String] {

  override def apply(source: String): Try[ParseResult[String]] = source match {
    case string: String if string.startsWith(expected) => Try(ParseResult(expected, source.substring(expected.length)))
    case _ => Try(throw new Error)
  }
}

