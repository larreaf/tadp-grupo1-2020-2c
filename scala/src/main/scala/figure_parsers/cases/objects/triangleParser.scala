package figure_parsers.cases.objects

import figure_parsers.cases.Coordinates2D
import figure_parsers.internal.Figure.Triangle
import figure_parsers.internal.{Figure, point2d_parser}
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object triangleParser extends Parser[Figure] {
    val parser: Parser[List[Coordinates2D]] = string("triangulo") ~> char('[') ~> point2d_parser.sepBy(string(", ")) <~ char(']')

    override def apply(source: String): Try[ParseResult[Figure]] = {
        this.parser(source).map(parseResult => {
          val coordinates2D = parseResult.parsed
          ParseResult(Triangle(coordinates2D.head, coordinates2D(1), coordinates2D(2)), parseResult.remnant)
        })
    }
}