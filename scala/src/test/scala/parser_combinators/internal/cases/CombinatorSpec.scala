package parser_combinators.internal.cases

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parser_combinators.internal.cases.classes._

class CombinatorSpec extends AnyFlatSpec with should.Matchers  {
  "OR Combinator" should "succeed if the first parser succeeded" in {
    val optionalChars = char('b') <|> char('a')
    val resultOptionalsChars = optionalChars("ave")
    assert(resultOptionalsChars.isSuccess)
    assertResult('a')(resultOptionalsChars.get.parsed)
  }

  it should "succeed if last parser succeeded" in {
    val optionalChars = char('b') <|> char('e') <|> char('a')
    val resultOptionalsChars = optionalChars("ave")
    assert(resultOptionalsChars.isSuccess)
    assertResult('a')(resultOptionalsChars.get.parsed)
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
    assertResult('c')(combinedChars.get.parsed._1)
    assertResult('a')(combinedChars.get.parsed._2)
  }

  it should "fail if a parser failed" in {
    val optionalChars = char('h') <> char('z')
    val combinedChars = optionalChars("cava")
    assert(combinedChars.isFailure)
  }

  "RightMost" should "succeed if both parser succeeded " in {
    val RightMostOp = char('c') ~> char('a')
    val result = RightMostOp("cava")
    assert(result.isSuccess)
  }

  it should "return the second parser value" in {
    val RightMostOp = char('c') ~> char('a')
    val result = RightMostOp("cava")
    assertResult('a') (result.get.parsed)
  }

  it should "fail if first parser failed" in {
    val RightMostOp = char('h') ~> char('a')
    val result = RightMostOp("cava")
    assert(result.isFailure)
  }

  it should "fail if second parser failed" in {
    val RightMostOp = char('c') ~> char('h')
    val result = RightMostOp("cava")
    assert(result.isFailure)
  }

  it should "return the second parser remnant" in {
    val RightMostOp = char('c') ~> char('a')
    val result = RightMostOp("cavas")
    assertResult("vas") (result.get.remnant)
  }

  "LeftMost" should "succeed if both parser succeeded " in {
    val LeftMostOp = char('c') <~ char('a')
    val result = LeftMostOp("acva")
    assert(result.isSuccess)
  }

  it should "return the first parser value" in {
    val LeftMostOp = char('c') <~ char('a')
    val result = LeftMostOp("acva")
    assertResult('c') (result.get.parsed)
  }

  it should "fail if first parser failed" in {
    val LeftMostOp = char('h') <~ char('a')
    val result = LeftMostOp("cava")
    assert(result.isFailure)
  }

  it should "fail if second parser failed" in {
    val LeftMostOp = char('c') <~ char('h')
    val result = LeftMostOp("cava")
    assert(result.isFailure)
  }

  it should "return the first parser remnant" in {
    val LeftMostOp = char('c') <~ char('a')
    val result = LeftMostOp("acvas")
    assertResult("vas") (result.get.remnant)
  }

  "kleeneClousure" should "succeed" in {
    val kleeneDigits = char('a').*
    val result = kleeneDigits("aabb")
    assert(result.isSuccess)
  }
  it should "return the value n-times" in {
    val kleeneDigits = char('a').*
    val result = kleeneDigits("aaabb")
    assertResult(3)(result.get.parsed.length)
    assert(result.get.parsed
                     .foldLeft(true)((boolean, charParsed) => 'a'.equals(charParsed))
    )
  }

  it should "succeed if no value was parse" in {
    val kleeneDigits = char('z').*
    val result = kleeneDigits("no encuentra ninguno")
    assert(result.isSuccess)
    assertResult(0)(result.get.parsed.length)
  }


  "Combine more parsers" should "succeed" in {
    val parser = (char('c') ~> char('h')) <> char('a')
    val result = parser("chau")
    assert(result.isSuccess)

  }

  "opt" should "succeed if the value was parsed" in {
    val talVezIn = string("in").opt
    val result = talVezIn("infija")
    assert(result.isSuccess)
  }

  it should "succeed if no value was parsed" in {
    val talVezIn = string("in").opt
    val result = talVezIn("fija")
    assert(result.isSuccess)
  }
}
