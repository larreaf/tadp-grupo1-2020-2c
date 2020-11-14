package figure_parsers.cases.objects

import figure_parsers.internal.Drawable
import figure_parsers.internal.Figure.Scale
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.double
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object scaleParser extends Parser[Drawable] {

  val parser: Parser[(List[Double], Drawable)] = string("escala") ~> char('[').withBlanks ~> double.sepBy(char(',').withBlanks) <~ char(']').withBlanks <> (char('(') ~> figureParser <~ char(')')).withBlanks

  override def apply(source: String): Try[ParseResult[Drawable]] = {
    this.parser
        .map[Drawable](tupleParsed => {
          val scales = tupleParsed._1
          Scale(scales.head, scales.last, tupleParsed._2)
        })(source)
  }
}
