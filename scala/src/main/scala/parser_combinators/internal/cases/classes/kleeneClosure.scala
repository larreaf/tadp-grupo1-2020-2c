package parser_combinators.internal.cases.classes

import parser_combinators.internal.auxiliars.parseRecursively
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class kleeneClosure[Parsed](parser: Parser[Parsed]) extends Parser[List[Parsed]] {
  override def apply(source: String): Try[ParseResult[List[Parsed]]] = {
    val parsedResults = parseRecursively(source, parser)
    val parsedValues = parsedResults.map(parseResult => parseResult.get.parsed)
    val lastRemnant = parsedResults.lastOption
                                   .map(parseResult => parseResult.get.remnant)
                                   .getOrElse(source)
    Try(ParseResult(parsedValues, lastRemnant))
  }
}
