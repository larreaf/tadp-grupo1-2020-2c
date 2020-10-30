package parser_combinators.internal.cases.classes

import parser_combinators.internal.auxiliars.{flatParsedResults, parseRecursively, remnantClosure}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class kleeneClosure[Parsed](seedFunction: Function[String, Try[ParseResult[Parsed]]], populateFunction: Function[String, Try[ParseResult[Parsed]]]) extends Parser[List[Parsed]] {
  override protected def result(source: String): Try[List[Parsed]] = {
    Try(flatParsedResults(parseRecursively(source, seedFunction, populateFunction)))
  }

  override def remnant(source: String): String = remnantClosure(source, seedFunction, populateFunction)
}
