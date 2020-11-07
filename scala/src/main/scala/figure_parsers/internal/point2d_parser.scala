package figure_parsers.internal

import figure_parsers.cases.Coordinates2D
import parser_combinators.internal.cases.classes.{ParseResult, string}
import parser_combinators.internal.cases.objects.integer
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object point2d_parser extends Parser[Coordinates2D] {

  val parser: Parser[(Integer, Integer)] = integer <> (string(" @ ") ~> integer)

  override def apply(source: String): Try[ParseResult[Coordinates2D]] = this.parser(source).map(parseResult =>
    ParseResult(Coordinates2D(parseResult.parsed._1, parseResult.parsed._2), parseResult.remnant))
}
