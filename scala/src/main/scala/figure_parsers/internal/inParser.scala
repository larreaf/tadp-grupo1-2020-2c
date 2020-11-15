package figure_parsers.internal

import parser_combinators.internal.cases.classes.{ParseResult, char}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class inParser[Parsed](begin: Char, parser: Parser[Parsed], end: Char) extends Parser[Parsed] {
  val innerParser: Parser[Parsed] = char(begin).withBlanks ~> parser <~ char(end).withBlanks

  override def apply(source: String): Try[ParseResult[Parsed]] = this.innerParser(source)
}
