package parser_combinators.internal.cases.classes

case class ParseResult[Source, Parsed](parsed: Parsed, remnant: Source)
