package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.Group
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object groupParser extends Parser[Figure] {

  val parser: Parser[List[Figure]] = string("grupo") ~> char('(').withBlanks ~> figureParser.sepBy(char(',').withBlanks) <~ char(')').withBlanks

  override def apply(source: String): Try[ParseResult[Figure]] = {
    parser.map[Figure](figures => Group(figures))(source)
  }
}
