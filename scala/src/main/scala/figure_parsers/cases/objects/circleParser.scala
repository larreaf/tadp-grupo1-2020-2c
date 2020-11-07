package figure_parsers.cases.objects

import figure_parsers.cases.Coordinates2D
import figure_parsers.internal.Figure.Circle
import figure_parsers.internal.{Figure, point2d_parser}
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.integer
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object circleParser extends Parser[Figure] {
  val parser: Parser[(Coordinates2D, Integer)] = string("circulo") ~> char('[') ~> point2d_parser <> (string(", ") ~> integer) <~ char(']')

  override def apply(source: String): Try[ParseResult[Figure]] = {
    this.parser(source).map(parseResult => {
      val tupleParsed = parseResult.parsed
      ParseResult(Circle(tupleParsed._1, tupleParsed._2), parseResult.remnant)
    })
  }
}