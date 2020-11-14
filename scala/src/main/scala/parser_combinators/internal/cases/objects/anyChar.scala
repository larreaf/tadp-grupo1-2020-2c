package parser_combinators.internal.cases.objects

import parser_combinators.internal.cases.classes.ParseResult
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object anyChar extends Parser[Char] {
  override def apply(source: String): Try[ParseResult[Char]] = source match {
    case string: String if !string.isEmpty => Try(ParseResult(string.charAt(0), string.substring(1)))
    case _ => Try(throw new Error)
  }
}
