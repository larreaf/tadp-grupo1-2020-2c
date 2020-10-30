package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class mixedParser[Parsed, OtherParsed](parser: Parser[Parsed], separator: Parser[OtherParsed]) extends Parser[Parsed] {
  override def result(source: String): Try[Parsed] = parser.result(source)

  override def remnant(source: String): String = separator.remnant(parser.remnant(source))
}
