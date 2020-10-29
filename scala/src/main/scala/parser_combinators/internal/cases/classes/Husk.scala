package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class Husk[Parsed](function: Function[String, Try[ParseResult[Parsed]]]) extends Parser[Parsed] {
  override protected def result(source: String): Try[Parsed] = Try(function(source).get.parsed)
  override def remnant(source: String): String = function(source).get.remnant
}
