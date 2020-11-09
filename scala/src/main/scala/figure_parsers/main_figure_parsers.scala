package figure_parsers

import figure_parsers.cases.objects.{circleParser, colourParser, figureParser, groupParser, rectangleParser, scaleParser, triangleParser}

object main_figure_parsers extends App {
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

  val parsedFigures = figureParser(figures)

  val dummyImplicit = 2
}