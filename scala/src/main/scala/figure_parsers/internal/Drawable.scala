package figure_parsers.internal

import figure_parsers.cases.Coordinates2D

sealed trait Drawable
sealed trait Figure extends Drawable
sealed trait Transformation extends Drawable
object Figure {
  case class Triangle(coordinate1: Coordinates2D, coordinate2: Coordinates2D, coordinate3: Coordinates2D) extends Figure
  case class Rectangle(coordinate1: Coordinates2D, coordinate2: Coordinates2D) extends Figure
  case class Circle(center: Coordinates2D, radius: Double) extends Figure
  case class Group(figures: List[Drawable]) extends Figure
  case class Colour(red: Int, green: Int, blue: Int, drawable: Drawable) extends Transformation {
    def equals(colour: Colour): Boolean = this.red == colour.red && this.green == colour.green && this.blue == colour.blue
  }
  case class Scale(x: Double, y: Double, drawable: Drawable) extends Transformation
  case class Rotate(grade: Double, drawable: Drawable) extends Transformation
  case class Relocate(x: Double, y: Double, drawable: Drawable) extends Transformation
}
