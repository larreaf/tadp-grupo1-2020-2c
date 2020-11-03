package parser_combinators.internal.cases.objects

import parser_combinators.internal.cases.classes.{ParseResult, positiveClosure}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object integer extends Parser[Int] {
  private val parser = positiveClosure(digit)

  override def apply(source: String): Try[ParseResult[Int]] = {
    val parseResult = this.parser(source).get
    val parsedValue = parseResult.parsed
                                 .mkString("")

    Try(ParseResult(parsedValue.toInt, parseResult.remnant))
  }
}
