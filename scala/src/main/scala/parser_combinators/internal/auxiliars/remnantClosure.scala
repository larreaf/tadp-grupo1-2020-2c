package parser_combinators.internal.auxiliars

import parser_combinators.internal.cases.classes.ParseResult

import scala.Option
import scala.util.{Success, Try}

case object remnantClosure {
  def apply[Parsed](source: String, function: Function[String, Try[ParseResult[Parsed]]]): String = {
    parseRecursively(source, function).lastOption
                                      .getOrElse(Success(ParseResult("",source)))
                                      .get
                                      .remnant
  }
}
