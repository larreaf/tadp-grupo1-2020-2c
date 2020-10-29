package parser_combinators.internal.cases.classes

case class ParseResult[Parsed](parsed: Parsed, remnant: String)
