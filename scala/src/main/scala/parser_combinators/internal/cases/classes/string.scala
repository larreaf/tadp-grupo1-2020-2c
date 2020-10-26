package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.StringParser

import scala.util.Try

case class string(expected: String) extends StringParser[String] {
  override protected def result(source: String): Try[String] = source match {
    case string: String if string.contains(expected) => Try(expected)
    case _ => Try(throw new Error)
  }

  override protected def remnant(source: String): String = {
    source.substring(source.indexOfSlice(expected) + expected.length)
  }
}
