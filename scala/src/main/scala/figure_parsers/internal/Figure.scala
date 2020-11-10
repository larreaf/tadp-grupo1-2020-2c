package figure_parsers.internal

import figure_parsers.cases.Coordinates2D

sealed trait Figure
object Figure {
  case class Triangle(coordinate1: Coordinates2D, coordinate2: Coordinates2D, coordinate3: Coordinates2D) extends Figure
  case class Rectangle(coordinate1: Coordinates2D, coordinate2: Coordinates2D) extends Figure
  case class Circle(center: Coordinates2D, radius: Integer) extends Figure
  case class Group(figures: List[Figure]) extends Figure
  case class Colour(red: Integer, green: Integer, blue: Integer, figure: Figure) extends Figure {
    def equals(colour: Colour): Boolean = this.red == colour.red && this.green == colour.green && this.blue == colour.blue
  }
  case class Scale(x: Double, y: Double, figure: Figure) extends Figure
  case class Rotate(grade: Integer, figure: Figure) extends Figure
  case class Relocate(x: Integer, y: Integer, figure: Figure) extends Figure
}
