package parser_combinators.internal.cases.classes

import parser_combinators.internal.mixins.OptionParser

import scala.util.Try

case class ListHusk[Source, Parsed](function: Function[Source, Try[List[ParseResult[Source, Parsed]]]]) extends OptionParser[Source, List[ParseResult[Source, Parsed]]] {
  def apply (source: Source): Try[List[ParseResult[Source, Parsed]]] = function(source)

  override def <|>(optionParser: OptionParser[Source, List[ParseResult[Source, Parsed]]]): OptionParser[Source, List[ParseResult[Source, Parsed]]] = ListHusk((source: Source) => this(source).orElse(optionParser(source)))

  override def obtainRemnant(result: Try[List[ParseResult[Source, Parsed]]]): Source = result.get
                                                                                             .last
                                                                                             .remnant
}