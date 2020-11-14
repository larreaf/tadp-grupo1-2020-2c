package figure_parsers.cases.objects

import figure_parsers.internal.Drawable
import figure_parsers.internal.Figure.Group
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object groupParser extends Parser[Drawable] {

  val parser: Parser[List[Drawable]] = string("grupo") ~> char('(').withBlanks ~> figureParser.sepBy(char(',').withBlanks) <~ char(')').withBlanks

  override def apply(source: String): Try[ParseResult[Drawable]] = {
    parser.map[Drawable](figures => Group(figures))(source)
  }
}
