package parser_combinators.internal.cases.objects

import scala.util.Try
import parser_combinators.internal.mixins.Parser

case object anyChar extends Parser[Char] {
  override protected def result(source: String): Try[Char] = source match {
    case string: String if string.length > 0 => Try(string.charAt(0))
    case _ => Try(throw new Error)
  }

  override def remnant(string: String): String = {
    string.substring(1)
  }
}
