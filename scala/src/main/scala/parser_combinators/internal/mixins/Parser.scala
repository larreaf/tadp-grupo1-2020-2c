package parser_combinators.internal.mixins

import parser_combinators.internal.cases.classes.{Husk, ParseResult, kleeneClosure, positiveClosure}

import scala.util.{Failure, Success, Try}

trait Parser[Parsed] extends Function[String, Try[ParseResult[Parsed]]]  {
  def apply (source: String): Try[ParseResult[Parsed]] = {
    this.result(source) match {
      case Success(value) => Try(ParseResult(value, this.remnant(source)))
      case Failure(exception) => Try(throw exception)
    }
  }

  def <|>(optionParser: Parser[Parsed]): Parser[Parsed] = Husk((source: String) => this(source).orElse(optionParser(source)))

  def <> (parser: Parser[Parsed]): Function[String, Try[(Parsed, Parsed)]] = {
    source: String => {
      val firstResult = this(source)
      Try((firstResult.get.parsed, parser(this.obtainRemnant(firstResult)).get.parsed))
    }
  }

  def * : kleeneClosure[Parsed] = kleeneClosure(this)

  def + : positiveClosure[Parsed] = positiveClosure(this)

  protected def result(source: String): Try[Parsed]

  def remnant(source: String): String

  def obtainRemnant(result: Try[ParseResult[Parsed]]): String = result.get
                                                                      .remnant
}