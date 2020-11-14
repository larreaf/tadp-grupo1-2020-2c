package parser_combinators.internal.cases.objects

import parser_combinators.internal.cases.classes.{ParseResult, char}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object integer extends Parser[Integer] {

  val parser: Parser[(Option[Char], Integer)] = char('-').opt <> naturalWithZero

  override def apply(source: String): Try[ParseResult[Integer]] = {
    this.parser
      .map[Integer](tupleParsed => {
        val optionalMinusSign = tupleParsed._1.map(minusSing => minusSing.toString).getOrElse("")
        val digits = tupleParsed._2
        s"$optionalMinusSign$digits".toInt
      })(source)
  }
}
