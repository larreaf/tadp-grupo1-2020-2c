package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.Parser

import scala.util.{Failure, Success, Try}

case class LeftMost[Parsed](parser1: Parser[Parsed], parser2: Parser[Parsed]) extends Parser[Parsed]{

  override def result(source: String): Try[Parsed] = {
    val result = parser1 <> parser2
    Try(result(source).get.parsed._1)
  }
  override def remnant(source: String): String = parser1(source).getOrElse(ParseResult("", source))
                                                                .remnant
}
