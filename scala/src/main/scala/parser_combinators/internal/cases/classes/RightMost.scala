package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.Parser

import scala.util.{Failure, Success, Try}

case class RightMost[Parsed](parser1: Parser[Parsed], parser2: Parser[Parsed]) extends Parser[Parsed]{

  override def result(source: String): Try[Parsed] = {
    val concatParser = parser1 <> parser2
    Try(concatParser(source).get.parsed._2)

  }
  override def remnant(source: String): String = {
    val concatParser = parser1 <> parser2
    concatParser(source).getOrElse(ParseResult("", source)).remnant
  }
}
