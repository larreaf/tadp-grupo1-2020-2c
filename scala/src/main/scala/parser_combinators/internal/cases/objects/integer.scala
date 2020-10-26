package parser_combinators.internal.cases.objects


import parser_combinators.internal.auxiliars.regularExpressionParser

case object integer extends regularExpressionParser[Int]("-?\\d+".r) {
}