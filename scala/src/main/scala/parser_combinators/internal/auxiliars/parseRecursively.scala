package parser_combinators.internal.auxiliars
import parser_combinators.internal.cases.classes.ParseResult

import scala.util.Try
import parser_combinators.internal.mixins.Parser

case object parseRecursively {
  def apply[Parsed](source: String, parser: Parser[Parsed]): List[Try[ParseResult[Parsed]]] = {
    Iterator.iterate(parser(source))(parsed => if (parsed.get.remnant.isEmpty) Try(throw new Error) else parser(parsed.get.remnant))
            .takeWhile(tryResult =>  tryResult.isSuccess)
            .toList
  }
}
