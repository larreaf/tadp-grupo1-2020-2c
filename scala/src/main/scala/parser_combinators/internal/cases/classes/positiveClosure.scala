package parser_combinators.internal.cases.classes

import parser_combinators.internal.auxiliars.{flatParsedResults, parseRecursively, remnantClosure}
import parser_combinators.internal.mixins.Parser

import scala.util.{Failure, Success, Try}

case class positiveClosure[Parsed](function: Function[String, Try[ParseResult[Parsed]]]) extends Parser[List[Parsed]] {
  override protected def result(source: String): Try[List[Parsed]] =  {
    val result = Try(function(source).get)
    result match {
      case Success(_) => Try(flatParsedResults(parseRecursively(source, function)))
      case Failure(exception) => Try(throw exception)
    }
  }

  override def remnant(source: String): String = remnantClosure(source, function)
}
