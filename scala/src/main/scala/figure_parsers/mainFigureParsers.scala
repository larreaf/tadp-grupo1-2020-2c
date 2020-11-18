package figure_parsers

import figure_parsers.cases.Coordinates2D
import figure_parsers.cases.objects._
import figure_parsers.internal.{Circle, Group, Relocate, Scale}

object mainFigureParsers extends App {
  val triangle = "triangulo[0 @ 100, 200 @ 300, 400 @ 500]"

  val rectangle = "rectangulo[0 @ 100, 200 @ 300]"

  val circle = "circulo[0 @ 100, 50]"

  val group = "grupo(triangulo[0 @ 100, 200 @ 300, 400 @ 500],grupo(triangulo[0 @ 100, 200 @ 300, 400 @ 500]),rectangulo[0 @ 100, 200 @ 300],circulo[0 @ 100, 50])"

  val parsedTriangle = triangleParser(triangle)

  val parsedRectangle = rectangleParser(rectangle)

  val parsedCircle = circleParser(circle)

  val parsedGroup = groupParser(group)

  val colour = "color[0, 0, 0](rectangulo[0 @ 0, 400 @ 400])"

  val colourParsed = colourParser(colour)

  val scale = "escala[1.45, 1.45](grupo(color[0, 0, 0](rectangulo[0 @ 0, 400 @ 400])))"

  val scaleParsed = scaleParser(scale)

  val secondGroup = "grupo(color[0, 0, 0](rectangulo[0 @ 0, 400 @ 400]),color[200, 70, 0](rectangulo[0 @ 0, 180 @ 150]),color[250, 250, 250](grupo(rectangulo[186 @ 0, 400 @ 150],rectangulo[186 @ 159, 400 @ 240],rectangulo[0 @ 159, 180 @ 240],rectangulo[45 @ 248, 180 @ 400],rectangulo[310 @ 248, 400 @ 400],rectangulo[186 @ 385, 305 @ 400])),color[30, 50, 130](rectangulo[186 @ 248, 305 @ 380]),color[250, 230, 0](rectangulo[0 @ 248, 40 @ 400]))"

  val parsedSecondGroup = groupParser(secondGroup)

  val figures = "escala[1.45, 1.45](grupo(color[0, 0, 0](rectangulo[0 @ 0, 400 @ 400]),color[200, 70, 0](rectangulo[0 @ 0, 180 @ 150]),color[250, 250, 250](grupo(rectangulo[186 @ 0, 400 @ 150],rectangulo[186 @ 159, 400 @ 240],rectangulo[0 @ 159, 180 @ 240],rectangulo[45 @ 248, 180 @ 400],rectangulo[310 @ 248, 400 @ 400],rectangulo[186 @ 385, 305 @ 400])),color[30, 50, 130](rectangulo[186 @ 248, 305 @ 380]),color[250, 230, 0](rectangulo[0 @ 248, 40 @ 400])))"

  val parsedFigures = drawableParser(figures)

  val figuresToSimplify = "grupo(color[255, 255, 255](rectangulo[0 @ 0, 400 @ 400]),color[255, 255, 255](rectangulo[0 @ 0, 180 @ 150]))"

  val everySimplificationUnlessGroupColor = "grupo(color[255, 255, 255](color[150, 0, 150](rectangulo[ 0 @ 0 ,  400 @ 400])),escala[2, 3](escala[3, 5](circulo[0 @ 5, 10])),traslacion[100, 5](traslacion[20, 10](circulo[0 @ 5, 10])),rotacion[300](rotacion[10](rectangulo[100 @ 200, 300 @ 400])))"

  val parsedFiguresToSimplify = drawableParser(everySimplificationUnlessGroupColor)

  val resultOfSimplify = parsedFiguresToSimplify.get.parsed.simplify

  val groupWithCommonColour = "grupo(escala[1.45, 1.45](rectangulo[0 @ 0, 400 @ 400]),escala[1.45, 1.45](rectangulo[0 @ 0, 400 @ 400]),escala[1.45, 1.45](rectangulo[0 @ 0, 400 @ 400]))"

  val groupWithCommonColourParsed = drawableParser(groupWithCommonColour)

  val resultOfSimplify2 = groupWithCommonColourParsed.get.parsed.simplify

  val groupWithNullTransformations = "grupo(escala[1, 1](circulo[0 @ 5, 10]),traslacion[0, 0](circulo[0 @ 5, 10]),rotacion[0](rectangulo[100 @ 200, 300 @ 400])))"

  val groupWithNullTransformationsParsed = drawableParser(groupWithNullTransformations)

  val resultOfSimplify3 = groupWithNullTransformationsParsed.get.parsed.simplify

  val coo = Group(Circle(Coordinates2D(2, 5), 4)::Nil)

  val d2 = Group(Circle(Coordinates2D(2, 5), 3)::Nil)

  val as = coo == d2

  val ad = Scale(1, 1, Scale(1, 1, Circle(Coordinates2D(2, 5), 3)))

  val rel = Relocate(2, 3, Circle(Coordinates2D(2, 5), 3))

  val mot = ad == rel

  val m = ad.simplify

  val drawables = Scale(1, 1, Scale(1, 1, Circle(Coordinates2D(2, 5), 3)))::Relocate(1, 1, Circle(Coordinates2D(2, 5), 3))::Nil

  if (drawables.forall(d => d == ad)) {
    ad.simplify
  }

  val dummyImplicit = 2
}