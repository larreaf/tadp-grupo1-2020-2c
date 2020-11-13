package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.Scale
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.double
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object scaleParser extends Parser[Figure] {

  val parser: Parser[(List[Double], Figure)] = string("escala") ~> char('[').withBlanks ~> double.sepBy(char(',').withBlanks) <~ char(']').withBlanks <> (char('(') ~> figureParser <~ char(')')).withBlanks

  override def apply(source: String): Try[ParseResult[Figure]] = {
    this.parser(source)
        .map(parseResult => {
          val scales = parseResult.parsed._1
          ParseResult(Scale(scales.head, scales.last, parseResult.parsed._2), parseResult.remnant)
        })
  }
}
