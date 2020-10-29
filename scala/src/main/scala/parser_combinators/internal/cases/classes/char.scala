package parser_combinators.internal.cases.classes

import parser_combinators.internal.auxiliars.RegularExpressionParser

import scala.util.matching.Regex

case class char(character: Char) extends RegularExpressionParser[Char]( new Regex("-?"+character)) {}
//case class char(character: Char) extends StringParser[Char]
//{
//  override protected def result(source: String): Try[Char] = source match {
//    case string: String if string.contains(character) => Try(character)
//    case _ => Try(throw new Error)
//  }
//
//  override protected def remnant(string: String): String = {
//    string.substring(string.indexOf(character) + 1)
//  }
//}
