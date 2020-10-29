package parser_combinators.internal.auxiliars

import parser_combinators.internal.cases.classes.ParseResult

import scala.util.Try

case object flatParsedResults {
  def apply[Parsed](parsedResults: List[Try[ParseResult[Parsed]]]): List[Parsed] = {
    for {
      parseResult <- parsedResults
    } yield parseResult.get.parsed
  }
}
