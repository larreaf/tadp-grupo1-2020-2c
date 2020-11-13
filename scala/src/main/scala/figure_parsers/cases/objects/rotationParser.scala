package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.Rotate
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.naturalWithZero
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object rotationParser extends Parser[Figure] {
  val parser: Parser[(Integer, Figure)] = string("rotacion") ~> char('[').withBlanks ~> naturalWithZero <~ char(']').withBlanks <> (char('(') ~> figureParser <~ char(')')).withBlanks

  override def apply(source: String): Try[ParseResult[Figure]] = {
    this.parser
        .map[Figure](tupleParsed => {
          val grade = tupleParsed._1
          Rotate(if (grade > 359) grade % 360 else grade, tupleParsed._2)
        })(source)
  }
}
