package parser_combinators.internal.mixins

import parser_combinators.internal.cases.classes.{Husk, ParseResult, kleeneClosure, mixedParser, positiveClosure, RightMost, LeftMost, Concat}

import scala.util.{Failure, Success, Try}

trait Parser[Parsed] extends Function[String, Try[ParseResult[Parsed]]]  {
  def apply (source: String): Try[ParseResult[Parsed]] = {
    this.result(source) match { // cambiar por operaciones monádicas.
      case Success(value) => Try(ParseResult(value, this.remnant(source)))
      case Failure(exception) => Try(throw exception)
    }
  }

  def <|>(optionParser: Parser[Parsed]): Parser[Parsed] = Husk((source: String) => this(source).orElse(optionParser(source)))

  def <> (parser: Parser[Parsed]): Husk[(Parsed, Parsed)] = Husk((source: String) => {
    val firstResult = this(source)
    Try(ParseResult(
          (firstResult.get.parsed, parser(this.obtainRemnant(firstResult)).get.parsed),
          parser(this.obtainRemnant(firstResult)).get.remnant
    ))
  })

  def ~> (parser: Parser[Parsed]): RightMost[Parsed] = RightMost(this, parser)

  def <~ (parser: Parser[Parsed]): LeftMost[Parsed] = LeftMost(this, parser)

  def satisfies(condition: Function[Parsed, Boolean]): Function[String, Option[Parser[Parsed]]] = {
    source =>  {
      this(source) match {
        case Success(parseResult) if condition(parseResult.parsed) => Some(this)
        case Failure(_) => None
      }
    }
  }

  def opt: Parser[String] = {
    Husk(source => {
      this.result(source) match {
        case Success(result: String) => Try(ParseResult(result, this.remnant(source)))
        case Failure(_) => Try(ParseResult("", source))
      }
    })
  }

  def * : kleeneClosure[Parsed] = kleeneClosure(this)

  def + : positiveClosure[Parsed] = positiveClosure(this)

  def sepBy[OtherParsed](parser: Parser[OtherParsed]): positiveClosure[Parsed] = positiveClosure(mixedParser(this, parser))

  def map[Destination](function: Function[Parsed, Destination]): Function[String, Try[Destination]] = {
    source: String => this(source).map(parseResult => function(parseResult.parsed))
  }

  def result(source: String): Try[Parsed]

  def remnant(source: String): String

  def obtainRemnant(result: Try[ParseResult[Parsed]]): String = result.get
                                                                      .remnant
}
