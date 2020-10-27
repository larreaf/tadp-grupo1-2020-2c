package parser_combinators.internal.cases.objects

import parser_combinators.internal.auxiliars.RegularExpressionParser

case object double extends RegularExpressionParser[Double]("-?\\d+\\.\\d+".r) {}
