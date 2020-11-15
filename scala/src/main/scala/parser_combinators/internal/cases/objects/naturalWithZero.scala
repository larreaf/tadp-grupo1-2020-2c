package parser_combinators.internal.cases.objects

import parser_combinators.internal.cases.classes.{ParseResult, positiveClosure}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object naturalWithZero extends Parser[Integer] {

  val parser: positiveClosure[Char] = positiveClosure(digit)

  override def apply(source: String): Try[ParseResult[Integer]] = {
    this.parser.map[Integer](digits => digits.mkString("").toInt)(source)
  }
}