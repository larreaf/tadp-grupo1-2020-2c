package parser_combinators.internal.cases.objects

import parser_combinators.internal.auxiliars.RegularExpressionParser


case object integer extends RegularExpressionParser[Int]("-?\\d+".r) {
}