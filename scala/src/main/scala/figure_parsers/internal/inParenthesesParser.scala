package figure_parsers.internal

import parser_combinators.internal.cases.classes.ParseResult
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class inParenthesesParser[Parsed](parser: Parser[Parsed]) extends Parser[Parsed] {
  val innerParser: Parser[Parsed] = inParser('(', parser, ')')

  override def apply(source: String): Try[ParseResult[Parsed]] = this.innerParser(source)
}
