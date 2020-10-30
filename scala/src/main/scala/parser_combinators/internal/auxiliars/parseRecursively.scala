package parser_combinators.internal.auxiliars
import parser_combinators.internal.cases.classes.ParseResult

import scala.util.Try

case object parseRecursively {
  def apply[Parsed](source: String, seedfunction: Function[String, Try[ParseResult[Parsed]]], populateFunction: Function[String, Try[ParseResult[Parsed]]]): List[Try[ParseResult[Parsed]]] = {
    Iterator.iterate(seedfunction(source))(parsed => populateFunction(parsed.get.remnant))
            .takeWhile(tryResult =>  tryResult.isSuccess)
            .toList
  }
}
