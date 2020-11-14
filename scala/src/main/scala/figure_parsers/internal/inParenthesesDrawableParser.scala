package figure_parsers.internal

import figure_parsers.cases.objects.drawableParser
import parser_combinators.internal.cases.classes.ParseResult
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object inParenthesesDrawableParser extends Parser[Drawable] {
  val innerParser: Parser[Drawable] = inParenthesesParser(drawableParser)

  override def apply(source: String): Try[ParseResult[Drawable]] = this.innerParser(source)
}
