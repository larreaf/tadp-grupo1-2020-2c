package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class mixedParser[Parsed, OtherParsed](parser: Parser[Parsed], separator: Parser[OtherParsed]) extends Parser[Parsed] {
  override protected def result(source: String): Try[Parsed] = Try(parser(source).get.parsed)

  override def remnant(source: String): String = separator(parser(source).get.remnant).get
                                                                                      .remnant
}
