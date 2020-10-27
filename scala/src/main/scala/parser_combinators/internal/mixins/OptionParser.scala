package parser_combinators.internal.mixins

import scala.util.Try

trait OptionParser[Source, Result] extends CombinableParser[Source, Try[Result]] {
  def <|>(optionParser: OptionParser[Source, Result]): OptionParser[Source, Result]
}
