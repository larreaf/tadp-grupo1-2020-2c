package figure_parsers.cases.objects

import figure_parsers.internal.Drawable
import figure_parsers.internal.Figure.Colour
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.integer
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object colourParser extends Parser[Drawable] {

  val parser: Parser[(List[Integer], Drawable)] = string("color") ~> char('[').withBlanks ~> integer.sepBy(char(',').withBlanks) <~ char(']').withBlanks <> (char('(').withBlanks ~> figureParser <~ char(')').withBlanks)

  override def apply(source: String): Try[ParseResult[Drawable]] = {
    this.parser.map[Drawable](tupleParsed => {
      val rgb = tupleParsed._1
      Colour(rgb.head, rgb(1), rgb.last, tupleParsed._2)
    })(source)
  }
}
