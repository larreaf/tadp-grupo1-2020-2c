package figure_parsers.cases.objects

import figure_parsers.cases.Coordinates2D
import figure_parsers.internal.Figure.Circle
import figure_parsers.internal.{Drawable, point2d_parser}
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.double
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object circleParser extends Parser[Drawable] {
  val parser: Parser[(Coordinates2D, Double)] = string("circulo") ~> char('[').withBlanks ~> point2d_parser <> (char(',').withBlanks ~> double) <~ char(']').withBlanks

  override def apply(source: String): Try[ParseResult[Drawable]] = {
    this.parser.map[Drawable](tupleParsed => Circle(tupleParsed._1, tupleParsed._2))(source)
  }
}