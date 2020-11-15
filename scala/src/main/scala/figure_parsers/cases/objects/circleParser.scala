package figure_parsers.cases.objects

import figure_parsers.cases.Coordinates2D
import figure_parsers.internal.Figure.Circle
import figure_parsers.internal.{Drawable, inBracketsParser, point2DParser}
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.double
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object circleParser extends Parser[Drawable] {
  val parser: Parser[(Coordinates2D, Double)] = string("circulo") ~> inBracketsParser(point2DParser <> (char(',').withBlanks ~> double))

  override def apply(source: String): Try[ParseResult[Drawable]] = {
    this.parser.map[Drawable](tupleParsed => Circle(tupleParsed._1, tupleParsed._2))(source)
  }
}