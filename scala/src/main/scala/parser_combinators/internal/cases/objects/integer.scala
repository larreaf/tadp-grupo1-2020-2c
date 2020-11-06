package parser_combinators.internal.cases.objects

import parser_combinators.internal.cases.classes.{ParseResult, char, positiveClosure}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object integer extends Parser[Integer] {

  val parser: Parser[(Option[Char], List[Char])] = char('-').opt <> positiveClosure(digit)

  override def apply(source: String): Try[ParseResult[Integer]] = {
    this.parser(source)
        .map(parseResult => {
          val parsedTuple = parseResult.parsed
          val optionalMinusSign = parsedTuple._1.map(minusSing => minusSing.toString).getOrElse("")
          val digits = parsedTuple._2.mkString("")
          ParseResult(s"$optionalMinusSign$digits".toInt, parseResult.remnant)
        })
  }
}
