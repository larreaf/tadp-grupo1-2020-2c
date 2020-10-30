package parser_combinators.internal.auxiliars

import parser_combinators.internal.cases.classes.ParseResult

import scala.util.{Success, Try}

case object remnantClosure {
  def apply[Parsed](source: String, seedFunction: Function[String, Try[ParseResult[Parsed]]], populateFunction: Function[String, Try[ParseResult[Parsed]]]): String = {
    parseRecursively(source, seedFunction, populateFunction).lastOption
                                      .getOrElse(Success(ParseResult("",source)))
                                      .get
                                      .remnant
  }
}
