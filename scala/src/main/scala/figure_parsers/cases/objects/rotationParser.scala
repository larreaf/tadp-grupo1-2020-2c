package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.Rotate
import parser_combinators.internal.cases.classes.{ParseResult, char, string}
import parser_combinators.internal.cases.objects.double
import parser_combinators.internal.mixins.Parser

import scala.util.Try

case object rotationParser extends Parser[Figure] {
  val parser: Parser[(Double, Figure)] = string("rotacion") ~> char('[').withBlanks ~> double <~ char(']').withBlanks <> (char('(') ~> figureParser <~ char(')')).withBlanks

  override def apply(source: String): Try[ParseResult[Figure]] = {
    this.parser
        .map[Figure](tupleParsed => {
          val grade = tupleParsed._1
          Rotate(grade % 360, tupleParsed._2)
        })(source)
  }
}
