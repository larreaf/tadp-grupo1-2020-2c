package parser_combinators.internal.cases.objects

import parser_combinators.internal.auxiliars.RegularExpressionParser

case object anyChar extends RegularExpressionParser[Char]("^-?\\w".r) {}

//case object anyChar extends StringParser[Char] {
//  override protected def result(source: String): Try[Char] = source match {
//    case string: String if string.length > 0 => Try(string.charAt(0))
//    case _ => Try(throw new Error)
//  }
//
//  override protected def remnant(string: String): String = {
//    string.substring(1)
//  }
//}
