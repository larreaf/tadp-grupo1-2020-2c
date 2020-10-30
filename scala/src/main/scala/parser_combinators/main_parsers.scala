package parser_combinators

import parser_combinators.internal.cases.classes.{char, string}
import parser_combinators.internal.cases.objects.{digit, double, integer}

object main_parsers extends App {
  val optionalChars = char('b') <|> char('e') <|> char('a')

  val resultOptionalsChars = optionalChars("cava")

  val combinatedChars = char('b') <> char('v')

  val a = char('a')

  val digitResult = digit("CA2O")

  val holaMundoParsers = string("hola") <> string("mundo")

  val holaMundoParsed = holaMundoParsers("holamundo")

  val ada = "-?\\d+".r.findFirstIn("asd-251a")

  val integerResult = integer("asda242")

  val kleeneDigits = char('a').* <> char('b').*
  val result = kleeneDigits("aabb")

  val result2 = double("A212.21A")

  val result3 = double("A212.A")

  val result4 = double("212.21")

  for (
    value <- result
  ) { print { value + "\n" }}

  val integerSepByChar = integer.sepBy(char('-'))

  val result5 = integerSepByChar("1234 5678")

  val dummyValue = 2
}
