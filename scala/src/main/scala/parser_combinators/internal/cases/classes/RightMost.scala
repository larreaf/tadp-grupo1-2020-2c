package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.Parser

import scala.util.{Failure, Success, Try}

case class RightMost[Parsed](parser1: Parser[Parsed], parser2: Parser[Parsed]) extends Parser[Parsed]{

  override def result(source: String): Try[Parsed] = {
    val firstResult = parser1(source)
    firstResult match {
      case Success(value) => Try(parser2(parser1.obtainRemnant(firstResult)).get.parsed)
      case Failure(exception) => Try(throw exception)
    }

  }
  override def remnant(source: String): String = {
    val firstResult = parser1(source)

    parser2(parser1.obtainRemnant(firstResult))
          .getOrElse(ParseResult("", source))
          .remnant
  }
}