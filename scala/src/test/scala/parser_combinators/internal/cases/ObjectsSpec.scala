package parser_combinators.internal.cases

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parser_combinators.internal.cases.objects._


class ObjectsSpec extends AnyFlatSpec with should.Matchers  {

  "double" should "fail if an integer is parsed" in {
    val result = double("212")
    assert(result.isFailure)
  }

  it should "success if a decimal is parsed" in {
    val doubleString = "212.45001"
    val result = double(doubleString)
    assert(result.isSuccess)
    assertResult(doubleString)(result.get.parsed)
  }

  it should "success and leave a remnant if a decimal within a text is parsed" in {
    val remnant = "hola soy un remanente"
    val doubleString = "212.45001"
    val textToParse = "texto adicional" + doubleString + remnant
    val result = double(textToParse)
    assert(result.isSuccess)
    assertResult(doubleString)(result.get.parsed)
    assertResult(remnant)(result.get.remnant)
  }

  "integer" should "fail if text is parsed" in {
    val result = integer("asdfghjkl")
    assert(result.isFailure)
  }

  it should "success if an integer is parsed" in {
    val integerString = "212"
    val result = integer(integerString)
    assert(result.isSuccess)
    assertResult(integerString)(result.get.parsed)
  }

  it should "success and leave a remnant if a decimal within a text is parsed" in {
    val remnant = "hola soy un remanente"
    val integerString = "212"
    val textToParse = integerString + remnant
    val result = integer(textToParse)
    assert(result.isSuccess)
    assertResult(integerString)(result.get.parsed)
    assertResult(remnant)(result.get.remnant)
  }

   "anyChar" should "success if text is parsed" in {
    val someText = "Hello World!"
    val result = anyChar(someText)
    assert(result.isSuccess)
    assertResult(someText(0).toString)(result.get.parsed)
  }

  it should "fail if no text is parsed" in {
    val someText = ""
    val result = anyChar(someText)
    assert(result.isFailure)
  }

  "digit" should "success if any digit within text is parsed" in {
    val someDigit = "2"
    val someText = "this is text" + someDigit + "this is text"
    val result = digit(someText)
    assert(result.isSuccess)
    assertResult(someDigit)(result.get.parsed)
  }

  it should "fail if none digit within text is parsed" in {
    val someText = "this is text"
    val result = digit(someText)
    assert(result.isFailure)
  }

}

