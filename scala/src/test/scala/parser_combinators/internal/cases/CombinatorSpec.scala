package parser_combinators.internal.cases

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parser_combinators.internal.cases.classes._

class CombinatorSpec extends AnyFlatSpec with should.Matchers  {
  "OR Combinator" should "succeed if the first parser succeeded" in {
    val optionalChars = char('b') <|> char('a')
    val resultOptionalsChars = optionalChars("cava")
    assert(resultOptionalsChars.isSuccess)
    assertResult("a")(resultOptionalsChars.get.parsed)
  }

  it should "succeed if last parser succeeded" in {
    val optionalChars = char('b') <|> char('e') <|> char('a')
    val resultOptionalsChars = optionalChars("cava")
    assert(resultOptionalsChars.isSuccess)
    assertResult("a")(resultOptionalsChars.get.parsed)
  }

  it should "fail if all parsers failed" in {
    val optionalChars = char('b') <|> char('e') <|> char('x')
    val resultOptionalsChars = optionalChars("cava")
    assert(resultOptionalsChars.isFailure)
  }

  "Concat Combinator" should "succeed if every parser succeeded" in {
    val optionalChars = char('c') <> char('a')
    val combinedChars = optionalChars("cava")
    assert(combinedChars.isSuccess)
    assertResult("c")(combinedChars.get._1.get.parsed)
    assertResult("a")(combinedChars.get._2.get.parsed)
  }
  
  it should "fail if a parser failed" in {
    val optionalChars = char('h') <> char('z')
    val combinedChars = optionalChars("cava")
    assert(combinedChars.isFailure)
  }

}
