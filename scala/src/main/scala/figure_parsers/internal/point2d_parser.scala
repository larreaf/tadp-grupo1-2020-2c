package figure_parsers.internal

import figure_parsers.cases.Coordinates2D
import parser_combinators.internal.cases.classes.{ParseResult, char}
import parser_combinators.internal.cases.objects.double
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object point2d_parser extends Parser[Coordinates2D] {

  val parser: Parser[(Double, Double)] = (double <> ( char('@').withBlanks ~> double)).withBlanks

  override def apply(source: String): Try[ParseResult[Coordinates2D]] = this.parser(source).map(parseResult =>
    ParseResult(Coordinates2D(parseResult.parsed._1, parseResult.parsed._2), parseResult.remnant))
}