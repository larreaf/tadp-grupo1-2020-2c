package parser_combinators

import parser_combinators.internal.cases.classes.{char, string}
import parser_combinators.internal.cases.objects.digit

object main_parsers extends App {
  val optionalChars = char('b') <|> char('e') <|> char('a')

  val resultOptionalsChars = optionalChars("cava")

  val combinatedChars = char('b') <> char('v')

  val digitResult = digit("CA2O")

  val holaMundoParsers = string("hola") <> string("mundo")

  val holaMundoParsed = holaMundoParsers("holamundo")

  val l = char('l')("nene")

  val dummyValue = 2
}
