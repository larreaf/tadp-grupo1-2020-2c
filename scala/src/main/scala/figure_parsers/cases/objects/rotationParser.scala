package figure_parsers.cases.objects

import figure_parsers.internal.Figure.Rotate
import figure_parsers.internal.{Drawable, inBracketsParser, inParenthesesDrawableParser}
import parser_combinators.internal.cases.classes.{ParseResult, string}
import parser_combinators.internal.cases.objects.double
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object rotationParser extends Parser[Drawable] {
  val parser: Parser[(Double, Drawable)] = string("rotacion") ~> inBracketsParser(double) <> inParenthesesDrawableParser

  override def apply(source: String): Try[ParseResult[Drawable]] = {
    this.parser
        .map[Drawable](tupleParsed => {
          val grade = tupleParsed._1
          Rotate(grade % 360, tupleParsed._2)
        })(source)
  }
}
