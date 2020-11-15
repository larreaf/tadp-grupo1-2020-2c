package parser_combinators.internal.cases.objects

import parser_combinators.internal.cases.classes.{ParseResult, char}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object double extends Parser[Double] {

  val parser: Parser[(Integer, Option[Integer])] = integer <> (char('.') ~> naturalWithZero).opt

  override def apply(source: String): Try[ParseResult[Double]] = {
    this.parser
        .map(tupleParsed => {
          val numberBeforeDot = tupleParsed._1.toString
          val numberAfterDot = tupleParsed._2.map(numbers => numbers.toString).getOrElse("0")
          s"$numberBeforeDot.$numberAfterDot".toDouble
        })(source)
  }
}
