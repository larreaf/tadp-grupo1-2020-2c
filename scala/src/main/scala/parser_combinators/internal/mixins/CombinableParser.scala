package parser_combinators.internal.mixins

import scala.util.Try

trait CombinableParser[Source, Result] extends Function[Source, Result] {
  def apply (source: Source): Result

  def <> (parser: Function[Source, Result]): Function[Source, Try[(Result, Result)]] = {
    source: Source => {
      val firstResult = this(source)
      Try((firstResult, parser(this.obtainRemnant(firstResult))))
    }
  }

  def obtainRemnant(result: Result): Source
}
