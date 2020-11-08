package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.Colour
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.integer
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object colourParser extends Parser[Figure] {

  val parser: Parser[(List[Integer], Figure)] = string("color") ~> char('[') ~> integer.sepBy(string(", ")) <~ char(']') <> (char('(') ~> figureParser <~ char(')'))

  override def apply(source: String): Try[ParseResult[Figure]] = {
    this.parser(source)
        .map(parseResult => {
          val rgb = parseResult.parsed._1
          ParseResult(Colour(rgb.head, rgb(1), rgb.last, parseResult.parsed._2), parseResult.remnant)
        })
  }
}
