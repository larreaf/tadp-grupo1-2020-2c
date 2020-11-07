package figure_parsers

import figure_parsers.cases.objects.{circleParser, groupParser, rectangleParser, triangleParser}

object main_figure_parsers extends App {
  val triangle = "triangulo[0 @ 100, 200 @ 300, 400 @ 500]"

  val rectangle = "rectangulo[0 @ 100, 200 @ 300]"

  val circle = "circulo[0 @ 100, 50]"

  val group = "grupo(triangulo[0 @ 100, 200 @ 300, 400 @ 500],grupo(triangulo[0 @ 100, 200 @ 300, 400 @ 500],),rectangulo[0 @ 100, 200 @ 300],circulo[0 @ 100, 50])"

  val parsedTriangle = triangleParser(triangle)

  val parsedRectangle = rectangleParser(rectangle)

  val parsedCircle = circleParser(circle)

  val parsedGroup = groupParser(group)

  val dummyImplicit = 2
}