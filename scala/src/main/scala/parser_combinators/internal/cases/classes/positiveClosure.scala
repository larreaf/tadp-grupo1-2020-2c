package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.Parser

import scala.util.Try

case class positiveClosure[Parsed](parser: Parser[Parsed]) extends Parser[List[Parsed]] {

  val innerParser: Parser[(Parsed, List[Parsed])] = parser <> kleeneClosure(parser)

  override def apply(source: String): Try[ParseResult[List[Parsed]]] = {
    this.innerParser(source)
        .map(combinedParseResult => {
          val parsedTuple = combinedParseResult.parsed
          ParseResult(parsedTuple._1 +: parsedTuple._2, combinedParseResult.remnant)
        })
  }
}
