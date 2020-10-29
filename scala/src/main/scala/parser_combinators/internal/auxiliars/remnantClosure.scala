package parser_combinators.internal.auxiliars

import parser_combinators.internal.cases.classes.ParseResult

import scala.util.{Success, Try}

case object remnantClosure {
  def apply[Parsed](source: String, function: Function[String, Try[ParseResult[Parsed]]]): String = parseRecursively(source, function).last match {
    case Success(parseResult: ParseResult[Parsed]) => parseResult.remnant
    case _ => source
  }
}
