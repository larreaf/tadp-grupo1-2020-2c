package parser_combinators.internal.cases

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parser_combinators.internal.double


class DoubleSpec extends AnyFlatSpec with should.Matchers {
  it should "double debería fallar si parsea un integer" in {
    val result = double("212")
    assert(result.isFailure)
  }

  it should "double debería parsear correctamente un numero decimal" in {
    val doubleString = "212.45001"
    val result = double(doubleString)
    assert(result.isSuccess)
    assertResult(doubleString)(result.get.parsed)
  }

  it should "double debería parsear correctamente un numero decimal y dejar un remanente" in {
    val remnant = "hola soy un remanente"
    val doubleString = "212.45001"
    val textToParse = doubleString + remnant
    val result = double(textToParse)
    assert(result.isSuccess)
    assertResult(doubleString)(result.get.parsed)
    assertResult(remnant)(result.get.remnant)
  }

}

