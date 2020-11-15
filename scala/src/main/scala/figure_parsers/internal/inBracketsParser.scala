package figure_parsers.internal

import parser_combinators.internal.mixins.Parser

case object inBracketsParser {
  def apply[Parsed](parser: Parser[Parsed]): inParser[Parsed] = inParser('[', parser, ']')
}
