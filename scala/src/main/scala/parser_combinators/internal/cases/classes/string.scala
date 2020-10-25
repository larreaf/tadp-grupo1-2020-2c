package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.StringParser

import scala.util.Try

case class string(expected: String) extends StringParser[String] {
  override protected def result(originalString: String): Try[String] = originalString match {
    case string: String if string.contains(originalString) => Try(expected)
    case _ => Try(throw new Error)
  }

  override protected def remnant(string: String): String = {
    string.substring(string.indexOfSlice(expected) + expected.length)
  }
}
