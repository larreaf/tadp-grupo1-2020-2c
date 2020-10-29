package parser_combinators.internal.cases.objects

import parser_combinators.internal.auxiliars.RegularExpressionParser

case object digit extends RegularExpressionParser[Char]("-?\\d".r) {}
//case object digit extends StringParser[Char] {
//  override protected def result(source: String): Try[Char] = source match {
//    case string: String if string.length > 0 && string.charAt(0).isDigit => Try(string.charAt(0))
//    case _ => Try(throw new Error)
//  }
//
//  override protected def remnant(source: String): String = {
//    source.substring(source.indexOf(firstDigit(source)) + 1)
//  }
//
//  protected def firstDigit(source: String): Char = {
//    source.find(c => c.isDigit).get
//  }
//}