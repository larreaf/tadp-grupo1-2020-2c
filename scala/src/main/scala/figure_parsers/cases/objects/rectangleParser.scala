package figure_parsers.cases.objects

import figure_parsers.cases.Coordinates2D
import figure_parsers.internal.Figure.Rectangle
import figure_parsers.internal.{Figure, point2d_parser}
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object rectangleParser extends Parser[Figure] {
  val parser: Parser[List[Coordinates2D]] = string("rectangulo") ~> char('[').withBlanks ~> point2d_parser.sepBy(char(',')) <~ char(']').withBlanks

  override def apply(source: String): Try[ParseResult[Figure]] = {
    this.parser.map[Figure](coordinates2D => Rectangle(coordinates2D.head, coordinates2D.last))(source)
  }
}