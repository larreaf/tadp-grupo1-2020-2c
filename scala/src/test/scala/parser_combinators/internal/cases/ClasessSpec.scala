package parser_combinators.internal.cases

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parser_combinators.internal.cases.classes._

class ClasessSpec extends AnyFlatSpec with should.Matchers  {
  "char" should "success if the character appears inside the text parsed" in {
    val character = 'W'
    val result = char(character) ("Hello World!")
    assert(result.isSuccess)
    assertResult(character.toString)(result.get.parsed)
  }
  it should "fail if the char does not appear inside the text parsed" in {
    val character = 'b'
    val result = char(character) ("Hello World!")
    assert(result.isFailure)
  }
}
