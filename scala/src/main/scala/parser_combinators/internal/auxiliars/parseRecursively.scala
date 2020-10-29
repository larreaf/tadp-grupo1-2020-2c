package parser_combinators.internal.auxiliars
import parser_combinators.internal.cases.classes.ParseResult

import scala.util.Try

case object parseRecursively {
  def apply[Parsed](source: String, function: Function[String, Try[ParseResult[Parsed]]]): List[Try[ParseResult[Parsed]]] = {
    Iterator.iterate(function(source))(parsed => function(parsed.get.remnant))
            .takeWhile(tryResult =>  tryResult.isSuccess)
            .toList
  }
}
