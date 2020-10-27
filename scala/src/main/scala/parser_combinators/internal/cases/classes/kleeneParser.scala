package parser_combinators.internal.cases.classes

import scala.util.Try

case class kleeneParser[Source, Parsed](function: Function[Source, Try[ParseResult[Source, Parsed]]]) extends Function[Source, List[Parsed]] {
  def apply(source: Source): List[Parsed] = {
      for {
        parseResult <- this.parseRecursively(source)
      } yield parseResult.get.parsed
  }

  def parseRecursively(source: Source): List[Try[ParseResult[Source, Parsed]]] = {
    Iterator.iterate(function(source))(parsed => function(parsed.get.remnant))
            .takeWhile(tryResult =>  tryResult.isSuccess)
            .toList
  }
}
