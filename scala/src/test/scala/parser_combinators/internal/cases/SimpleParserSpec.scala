package parser_combinators.internal.cases

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parser_combinators.internal.cases.classes.char
import parser_combinators.internal.cases.objects.{anyChar, digit, double, integer}


class SimpleParserSpec extends AnyFlatSpec with should.Matchers  {

  "double" should "fail if an integer is parsed" in {
    val result = double("212")
    assert(result.isSuccess)
  }

  it should "succeed if a decimal is parsed" in {
    val doubleString = "212.45001"
    val result = double(doubleString)
    assert(result.isSuccess)
    assertResult(doubleString.toDouble)(result.get.parsed)
  }

  it should "succeed and leave a remnant if a decimal within a text is parsed" in {
    val remnant = "hola soy un remanente"
    val doubleString = "212.45001"
    val textToParse = doubleString + remnant
    val result = double(textToParse)
    assert(result.isSuccess)
    assertResult(doubleString.toDouble)(result.get.parsed)
    assertResult(remnant)(result.get.remnant)
  }

  "integer" should "fail if text is parsed" in {
    val result = integer("asdfghjkl")
    assert(result.isFailure)
  }

  it should "succeed if an integer is parsed" in {
    val integerString = "212"
    val result = integer(integerString)
    assert(result.isSuccess)
    assertResult(integerString.toInt)(result.get.parsed)
  }

  it should "succeed and leave a remnant if a decimal within a text is parsed" in {
    val remnant = "hola soy un remanente"
    val integerString = "212"
    val textToParse = integerString + remnant
    val result = integer(textToParse)
    assert(result.isSuccess)
    assertResult(integerString.toInt)(result.get.parsed)
    assertResult(remnant)(result.get.remnant)
  }

   "anyChar" should "succeed if text is parsed" in {
    val someText = "Hello World!"
    val result = anyChar(someText)
    assert(result.isSuccess)
    assertResult(someText(0))(result.get.parsed)
  }

  it should "fail if no text is parsed" in {
    val someText = ""
    val result = anyChar(someText)
    assert(result.isFailure)
  }

  "digit" should "succeed if any digit within text isn't parsed" in {
    val someDigit = "2"
    val someText = "this is text" + someDigit + "this is text"
    val result = digit(someText)
    assert(result.isFailure)
  }

  it should "succeed if any digit before text is parsed" in {
    val someDigit = "2"
    val someText = someDigit + "this is text"
    val result = digit(someText)
    assert(result.isSuccess)
    assertResult(someDigit.charAt(0))(result.get.parsed)
  }

  it should "fail if none digit within text is parsed" in {
    val someText = "this is text"
    val result = digit(someText)
    assert(result.isFailure)
  }

  "char" should "succeed if the character appears inside the text parsed" in {
    val character = 'W'
    val result = char(character) ("World!")
    assert(result.isSuccess)
    assertResult(character)(result.get.parsed)
  }
  it should "fail if the char does not appear inside the text parsed" in {
    val character = 'b'
    val result = char(character) ("Hello World!")
    assert(result.isFailure)
  }

}

