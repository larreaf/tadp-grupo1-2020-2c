package parser_combinators.internal.cases.objects

import parser_combinators.internal.mixins.StringParser

import scala.util.Try

case object digit extends StringParser[Char] {
  override protected def result(originalString: String): Try[Char] = originalString match {
    case string: String if string.exists(_.isDigit) => Try(string.find(c => c.isDigit).get)
    case _ => Try(throw new Error)
  }

  override protected def remnant(string: String): String = {
    string.substring(string.indexOf(firstDigit(string)) + 1)
  }

  protected def firstDigit(string: String): Char  = {
    string.find(c => c.isDigit).get
  }
}
