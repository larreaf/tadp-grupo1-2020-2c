package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.OptionParser

import scala.util.{Failure, Success, Try}

case class positiveClosure[Source, Parsed](kleeneClosure: kleeneClosure[Source, Parsed]) extends OptionParser[Source, List[ParseResult[Source, Parsed]]] {

  def apply(source: Source): Try[List[ParseResult[Source, Parsed]]] = {
    val result = Try(kleeneClosure.function(source).get)
    result match {
      case Success(_) => Try(kleeneClosure(source))
      case Failure(exception) => Try(throw exception)
    }
  }

  override def obtainRemnant(result: Try[List[ParseResult[Source, Parsed]]]): Source = result.get
                                                                                             .last
                                                                                             .remnant

  override def <|>(optionParser: OptionParser[Source, List[ParseResult[Source, Parsed]]]): OptionParser[Source, List[ParseResult[Source, Parsed]]] = ListHusk((source: Source) => this(source).orElse(optionParser(source)))
}
