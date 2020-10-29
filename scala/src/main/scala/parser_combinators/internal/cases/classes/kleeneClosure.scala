package parser_combinators.internal.cases.classes

import parser_combinators.internal.auxiliars.{flatParsedResults, parseRecursively, remnantClosure}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class kleeneClosure[Parsed](function: Function[String, Try[ParseResult[Parsed]]]) extends Parser[List[Parsed]] {
  override protected def result(source: String): Try[List[Parsed]] = {
    Try(flatParsedResults(parseRecursively(source, function)))
  }

  override def remnant(source: String): String = remnantClosure(source, function)
}
