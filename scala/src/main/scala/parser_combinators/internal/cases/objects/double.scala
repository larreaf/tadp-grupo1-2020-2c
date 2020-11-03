package parser_combinators.internal.cases.objects

import parser_combinators.internal.cases.classes.{ParseResult, char, kleeneClosure, positiveClosure}
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object double extends Parser[Double] {
  private val parser = integer <> (char('.') <> integer).opt

  override def apply(source: String): Try[ParseResult[Double]] =
    char('1')(source).map {
      case
    }
}
