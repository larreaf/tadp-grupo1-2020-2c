package parser_combinators

import parser_combinators.internal.cases.classes.{char, string}
import parser_combinators.internal.cases.objects.{digit, integer}

object main_parsers extends App {
  val optionalChars = char('b') <|> char('e') <|> char('a')

  val resultOptionalsChars = optionalChars("cava")

  val combinatedChars = char('b') <> char('v')

  val digitResult = digit("CA2O")

  val holaMundoParsers = string("hola") <> string("mundo")

  val holaMundoParsed = holaMundoParsers("holamundo")

  val ada = "-?\\d+".r.findFirstIn("asd-251a")

  val l = integer("asda242")

  val adasda = digit.*("12351a512")

  for (
    value <- adasda
  ) { print { value+ "\n" }}

  val dummyValue = 2
}
