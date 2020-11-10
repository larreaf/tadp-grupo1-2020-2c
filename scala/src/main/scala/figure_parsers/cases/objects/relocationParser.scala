package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.Relocate
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.integer
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object relocationParser extends Parser[Figure] {
  val parser: Parser[(List[Integer], Figure)] = string("traslacion") ~> char('[') ~> integer.sepBy(string(", ")) <~ char(']') <> (char('(') ~> figureParser <~ char(')'))

  override def apply(source: String): Try[ParseResult[Figure]] = {
    this.parser(source)
        .map(parseResult => {
          val coordinates = parseResult.parsed._1
          ParseResult(Relocate(coordinates.head, coordinates.last, parseResult.parsed._2), parseResult.remnant)
        })
  }
}