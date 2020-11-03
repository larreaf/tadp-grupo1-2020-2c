package parser_combinators.internal.cases.objects

import parser_combinators.internal.cases.classes.{ParseResult, char}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object digit extends Parser[Char] {

  private val parser =  char('0') <|> char('1') <|>
                        char('2') <|> char('3') <|>
                        char('4') <|> char('5') <|>
                        char('6') <|> char('7') <|>
                        char('8') <|> char('9')

  override def apply(source: String): Try[ParseResult[Char]] = this.parser(source)
}
