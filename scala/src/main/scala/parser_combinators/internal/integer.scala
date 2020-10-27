package parser_combinators.internal

import parser_combinators.internal.auxiliars.RegularExpressionParser


case object integer extends RegularExpressionParser[Int]("-?\\d+".r) {
}