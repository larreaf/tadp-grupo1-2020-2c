package parser_combinators.internal.mixins

import scala.util.{Failure, Success, Try}
import parser_combinators.internal.cases.classes.{Husk, ParseResult}

trait Parser[Source, Parsed] extends Function[Source, Try[ParseResult[Source, Parsed]]] {
  def apply (source: Source): Try[ParseResult[Source, Parsed]] = {
    this.result(source) match {
      case Success(value) => Try(ParseResult(value, this.remnant(source)))
      case Failure(exception) => Try(throw exception)
    }
  }

  def <|>(parser: Parser[Source, Parsed]): Parser[Source, Parsed] = {
    Husk((source: Source) => this(source).orElse(parser(source)))
  }

  def <> (parser: Function[Source, Try[ParseResult[Source, Parsed]]]): Function[Source, Try[(ParseResult[Source, Parsed], ParseResult[Source, Parsed])]] = {
    source: Source => {
      val firstResult = this(source)
      Try((firstResult.get, parser(firstResult.get.remnant).get))
    }
  }

  protected def result(source: Source): Try[Parsed]

  protected def remnant(source: Source): Source
}
