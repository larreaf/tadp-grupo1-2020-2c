package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.Parser

import scala.util.{Failure, Success, Try}

case class Concat[Parsed](parser1: Parser[Parsed], parser2: Parser[Parsed]) extends Parser[(Parsed, Parsed)]{

  override def result(source: String): Try[(Parsed, Parsed)] = {
    val firstResult = parser1(source)
    Try((firstResult.get.parsed, parser2(parser1.obtainRemnant(firstResult)).get.parsed))
  }
  override def remnant(source: String): String = {
    val firstResult = parser1(source)
    parser2(parser1.obtainRemnant(firstResult)).getOrElse(ParseResult("", source)).remnant
  }
}