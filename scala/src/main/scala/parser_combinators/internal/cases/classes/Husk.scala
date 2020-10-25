package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class Husk[Source, Parsed](function: Function[Source, Try[ParseResult[Source, Parsed]]]) extends Parser[Source, Parsed] {
  override protected def result(source: Source): Try[Parsed] = Try(function(source).get.parsed)
  override protected def remnant(source: Source): Source = function(source).get.remnant
}
