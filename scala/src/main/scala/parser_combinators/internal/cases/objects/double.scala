package parser_combinators.internal.cases.objects

import parser_combinators.internal.cases.classes.{ParseResult, char}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object double extends Parser[Double] {

  val parser: Parser[(Integer, Option[Integer])] = integer <> (char('.') ~> integer).opt

  override def apply(source: String): Try[ParseResult[Double]] = {
    this.parser(source)
        .map(parseResult => {
          val numberBeforeDot = parseResult.parsed._1.toString
          val numberAfterDot = parseResult.parsed._2.map(number => number.toString).getOrElse("0")
          ParseResult(s"$numberBeforeDot.$numberAfterDot".toDouble, parseResult.remnant)
        })
  }
}
