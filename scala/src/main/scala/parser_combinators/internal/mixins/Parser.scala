package parser_combinators.internal.mixins

import parser_combinators.internal.cases.classes._

import scala.util.{Failure, Success, Try}

trait Parser[Parsed] extends Function[String, Try[ParseResult[Parsed]]] {
  def apply(source: String): Try[ParseResult[Parsed]]

  def <|>(optionParser: Parser[Parsed]): Parser[Parsed] = (source: String) => this(source).orElse(optionParser(source))

  def <>[OtherParsed](parser: Parser[OtherParsed]): Parser[(Parsed, OtherParsed)] = (source: String) => {
    this (source).map(parseResult => {
      val secondParseResult = parser(parseResult.remnant).get
      ParseResult((parseResult.parsed, secondParseResult.parsed), secondParseResult.remnant)
    })
  }

  def ~>[OtherParsed](parser: Parser[OtherParsed]): Parser[OtherParsed] = sequentialParse(this, parser)

  def <~[OtherParsed](parser: Parser[OtherParsed]): Parser[Parsed] = sequentialParse(parser, this)

  def satisfies(condition: Function[Parsed, Boolean]): Function[String, Option[Parser[Parsed]]] = {
    source =>  {
      this(source) match {
        case Success(parseResult) if condition(parseResult.parsed) => Some(this)
        case Failure(_) => None
      }
    }
  }

  def opt: Parser[Option[Parsed]] = (source: String) => {
    val parseResult = this(source)
    parseResult match {
      case Success(parseResult) => Try(ParseResult(Some(parseResult.parsed), parseResult.remnant))
      case Failure(_) => Try(ParseResult(None, source))
    }
  }

  def sepBy[OtherParsed](parser: Parser[OtherParsed]): positiveClosure[Parsed] = positiveClosure(this <~ parser)

  def * : kleeneClosure[Parsed] = kleeneClosure(this)

  def + : positiveClosure[Parsed] = positiveClosure(this)

  def map[Destination](function: Function[Parsed, Destination]): Function[String, Try[Destination]] = {
    source: String => this(source).map(parseResult => function(parseResult.parsed))
  }

  private def sequentialParse[Parsed1, Parsed2](parser1 : Parser[Parsed1], parser2 : Parser[Parsed2]) : Parser[Parsed2] = {
    source: String => {
      parser1(source).map(parseResult => parser2(parseResult.remnant).get)
    }
  }
}
