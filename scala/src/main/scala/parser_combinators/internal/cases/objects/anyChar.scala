package parser_combinators.internal.cases.objects

import parser_combinators.internal.mixins.StringParser

import scala.util.Try

case object anyChar extends StringParser[Char] {
  override protected def result(originalString: String): Try[Char] = originalString match {
    case string: String if string.length > 0 => Try(string.charAt(0))
    case _ => Try(throw new Error)
  }

  override protected def remnant(string: String): String = {
    string.substring(1)
  }
}
