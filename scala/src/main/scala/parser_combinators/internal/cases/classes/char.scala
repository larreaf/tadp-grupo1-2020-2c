package parser_combinators.internal.cases.classes

import scala.util.Try
import parser_combinators.internal.mixins.Parser

case class char(character: Char) extends Parser[Char]
{
  override protected def result(source: String): Try[Char] = source match {
    case string: String if string.contains(character) => Try(character)
    case _ => Try(throw new Error)
  }

  override def remnant(string: String): String = {
    string.substring(string.indexOf(character) + 1)
  }
}
