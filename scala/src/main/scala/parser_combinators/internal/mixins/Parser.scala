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

  def ~>[OtherParsed](parser: Parser[OtherParsed]): Parser[OtherParsed] = sequentialParse[OtherParsed, OtherParsed](parser, _._2)

  def <~[OtherParsed](parser: Parser[OtherParsed]): Parser[Parsed] = sequentialParse[OtherParsed, Parsed](parser, _._1)

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

  //TODO: Corregir bug de parseo, debería permitir un parseo aunque no exista el separador, se puede probar este caso en main_figure_parsers, el parser de group
  def sepBy[OtherParsed](separator: Parser[OtherParsed]): Parser[List[Parsed]] = positiveClosure(this <~ separator.opt)

  def * : kleeneClosure[Parsed] = kleeneClosure(this)

  def + : positiveClosure[Parsed] = positiveClosure(this)

  def map[Destination](function: Function[Parsed, Destination]): Function[String, Try[Destination]] = {
    source: String => this(source).map(parseResult => function(parseResult.parsed))
  }

  private def sequentialParse[OtherParsed, UsedParsed](parser : Parser[OtherParsed], selectResult: Function[(Parsed, OtherParsed), UsedParsed]) : Parser[UsedParsed] = {
    source: String => {
      (this <> parser)(source).map(parseResult =>
        ParseResult(selectResult(parseResult.parsed), parseResult.remnant))
    }
  }
}
