package parser_combinators

import parser_combinators.internal.cases.classes.char
import parser_combinators.internal.cases.objects.{double, integer}

object mainParsers extends App {
  val a = char('a') <|> char('b') <|> char('c')

  val b = char('a').opt ~> char('b')

  val e = b("ab")

  val number = double("-013.32")

  val fe = integer.sepBy(char('-'))

  val value = fe("4356-1234-1234-")

  val dummyImplicit = 2
}
