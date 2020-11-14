package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import parser_combinators.internal.cases.classes.ParseResult
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object figureParser extends Parser[Figure] {
  val parser: Parser[Figure] = (colourParser <|> scaleParser <|>
                               rotationParser <|> relocationParser <|>
                               groupParser <|> triangleParser <|>
                               rectangleParser <|> circleParser).withBlanks

  override def apply(source: String): Try[ParseResult[Figure]] = this.parser(source)
}
