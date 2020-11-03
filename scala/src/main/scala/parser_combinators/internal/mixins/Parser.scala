package parser_combinators.internal.mixins

import parser_combinators.internal.cases.classes._

import scala.util.Try

trait Parser[Parsed] extends Function[String, Try[ParseResult[Parsed]]]  {
  def apply (source: String): Try[ParseResult[Parsed]]

  def <|>(optionParser: Parser[Parsed]): Parser[Parsed] = Husk((source: String) => this(source).orElse(optionParser(source)))

  def <>[OtherParsed] (parser: Parser[OtherParsed]): Husk[(Parsed, OtherParsed)] = Husk((source: String) => {
    this(source).map(parseResult => {
      val secondParseResult = parser(parseResult.remnant).get
      ParseResult((parseResult.parsed, secondParseResult.parsed), secondParseResult.remnant)
    })
  })

  /*
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
*/
  def * : kleeneClosure[Parsed] = kleeneClosure(this)

  def + : positiveClosure[Parsed] = positiveClosure(this)

  def sepBy[OtherParsed](parser: Parser[OtherParsed]): positiveClosure[Parsed] = positiveClosure(mixedParser(this, parser))

  def map[Destination](function: Function[Parsed, Destination]): Function[String, Try[Destination]] = {
    source: String => this(source).map(parseResult => function(parseResult.parsed))
  }
}
