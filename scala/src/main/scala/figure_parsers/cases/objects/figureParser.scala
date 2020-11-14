package figure_parsers.cases.objects

import figure_parsers.internal.Drawable
import parser_combinators.internal.cases.classes.ParseResult
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object figureParser extends Parser[Drawable] {
  val parser: Parser[Drawable] = (colourParser <|> scaleParser <|>
                               rotationParser <|> relocationParser <|>
                               groupParser <|> triangleParser <|>
                               rectangleParser <|> circleParser).withBlanks

  override def apply(source: String): Try[ParseResult[Drawable]] = this.parser(source)
}
