package parser_combinators.internal.mixins

import parser_combinators.internal.cases.classes.{Husk, ParseResult, kleeneClosure, positiveClosure}

import scala.util.{Failure, Success, Try}

trait Parser[Source, Parsed] extends OptionParser[Source, ParseResult[Source, Parsed]] {
  def apply (source: Source): Try[ParseResult[Source, Parsed]] = {
    this.result(source) match {
      case Success(value) => Try(ParseResult(value, this.remnant(source)))
      case Failure(exception) => Try(throw exception)
    }
  }

  override def <|>(optionParser: OptionParser[Source, ParseResult[Source, Parsed]]): OptionParser[Source, ParseResult[Source, Parsed]] = Husk((source: Source) => this(source).orElse(optionParser(source)))

  def * : kleeneClosure[Source, Parsed] = kleeneClosure(this)

  def + : positiveClosure[Source, Parsed] = positiveClosure(kleeneClosure(this))

  protected def result(source: Source): Try[Parsed]

  protected def remnant(source: Source): Source

  override def obtainRemnant(result: Try[ParseResult[Source, Parsed]]): Source = result.get
                                                                                       .remnant
}
