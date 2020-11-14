package figure_parsers.cases.objects

import figure_parsers.cases.Coordinates2D
import figure_parsers.internal.Figure.Triangle
import figure_parsers.internal.{Drawable, inBracketsParser, point2DParser}
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object triangleParser extends Parser[Drawable] {
    val parser: Parser[List[Coordinates2D]] = string("triangulo") ~> inBracketsParser(point2DParser.sepBy(char(',').withBlanks))

    override def apply(source: String): Try[ParseResult[Drawable]] = {
        this.parser.map[Drawable](coordinates2D => Triangle(coordinates2D.head, coordinates2D(1), coordinates2D(2)))(source)
    }
}