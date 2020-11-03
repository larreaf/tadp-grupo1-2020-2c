package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class char(character: Char) extends Parser[Char] {
  override def apply(source: String): Try[ParseResult[Char]] = source match {
    case string: String if string.charAt(0) == character => Try(ParseResult(character, string.substring(1)))
    case _ => Try(throw new Error)
  }
}