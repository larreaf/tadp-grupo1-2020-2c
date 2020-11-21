package figure_parsers.cases.objects

import figure_parsers.internal.{Drawable, Group, inParenthesesParser}
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object groupParser extends Parser[Drawable] {

  val parser: Parser[List[Drawable]] = string("grupo") ~> inParenthesesParser(drawableParser.sepBy(char(',').withBlanks))

  override def apply(source: String): Try[ParseResult[Drawable]] = {
    parser.map[Drawable](figures => Group(figures))(source)
  }
}
