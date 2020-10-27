package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.CombinableParser

import scala.util.Try

case class kleeneClosure[Source, Parsed](function: Function[Source, Try[ParseResult[Source, Parsed]]]) extends CombinableParser[Source, List[ParseResult[Source, Parsed]]] {
  override def apply(source: Source): List[ParseResult[Source, Parsed]] = {
      for {
        parseResult <- this.parseRecursively(source)
      } yield parseResult.get
  }

  def parseRecursively(source: Source): List[Try[ParseResult[Source, Parsed]]] = {
    Iterator.iterate(function(source))(parsed => function(parsed.get.remnant))
            .takeWhile(tryResult =>  tryResult.isSuccess)
            .toList
  }

  override def obtainRemnant(result: List[ParseResult[Source, Parsed]]): Source = result.last
                                                                                        .remnant
}
