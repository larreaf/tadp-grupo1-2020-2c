package parser_combinators.internal

import parser_combinators.internal.auxiliars.RegularExpressionParser

case object double extends RegularExpressionParser[Double]("-?\\d+\\.\\d+".r) {
}
