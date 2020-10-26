package parser_combinators.internal.auxiliars

import parser_combinators.internal.mixins.StringParser

import scala.util.Try
import scala.util.matching.Regex

abstract class regularExpressionParser[Parsed](regex: Regex) extends StringParser[Parsed] {
  override protected def result(originalString: String): Try[Parsed] = originalString match {
    case string: String if this.findFirstIn(string).nonEmpty => Try(this.findFirstIn(string)
                                                                        .get
                                                                        .asInstanceOf[Parsed])
    case _ => Try(throw new Error)
  }

  override protected def remnant(source: String): String = {
    val integer = this.findFirstIn(source).get
    source.substring(source.indexOf(integer) + integer.length)
  }

  private def findFirstIn(string: String): Option[String] = this.regex
                                                                .findFirstIn(string)
}
