package figure_parsers.cases.objects

import figure_parsers.internal.{Drawable, Relocate, inBracketsParser, inParenthesesDrawableParser}
import parser_combinators.internal.cases.classes.{ParseResult, string}
import parser_combinators.internal.cases.objects.double
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object relocationParser extends Parser[Drawable] {
  val parser: Parser[(List[Double], Drawable)] = string("traslacion") ~> inBracketsParser(double.sepBy(string(",").withBlanks)) <> inParenthesesDrawableParser

  override def apply(source: String): Try[ParseResult[Drawable]] = {
    this.parser.map[Drawable](tupleParsed => {
      val coordinates = tupleParsed._1
      Relocate(coordinates.head, coordinates.last, tupleParsed._2)
    })(source)
  }
}
