package parser_combinators.internal.auxiliars

import scala.util.Try
import scala.util.matching.Regex
import parser_combinators.internal.mixins.Parser

abstract class RegularExpressionParser[Parsed](regex: Regex) extends Parser[Parsed] {

  override def result(originalString: String): Try[Parsed] = originalString match {
    case string: String if this.findFirstIn(string).nonEmpty => Try(this.findFirstIn(string)
                                                                        .get
                                                                        .asInstanceOf[Parsed])
    case _ => Try(throw new Error)
  }

  override def remnant(source: String): String = {
    val integer = this.findFirstIn(source).get
    source.substring(source.indexOf(integer) + integer.length)
  }

  private def findFirstIn(string: String): Option[String] = this.regex
                                                                .findFirstIn(string)
}
