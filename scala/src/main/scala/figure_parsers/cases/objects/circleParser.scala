package figure_parsers.cases.objects

import figure_parsers.cases.Coordinates2D
import figure_parsers.internal.Figure.Circle
import figure_parsers.internal.{Figure, point2d_parser}
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.double
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object circleParser extends Parser[Figure] {
  val parser: Parser[(Coordinates2D, Double)] = string("circulo") ~> char('[').withBlanks ~> point2d_parser <> (char(',').withBlanks ~> double) <~ char(']').withBlanks

  override def apply(source: String): Try[ParseResult[Figure]] = {
    this.parser.map[Figure](tupleParsed => Circle(tupleParsed._1, tupleParsed._2))(source)
  }
}