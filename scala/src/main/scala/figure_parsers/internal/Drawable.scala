package figure_parsers.internal

import figure_parsers.cases.Coordinates2D

sealed trait Drawable
sealed trait Figure extends Drawable
sealed trait Transformation extends Drawable {
  def drawable(): Drawable = ???

  def copyWithDrawable(drawable: Drawable): Transformation = ???
}
object Figure {
  case class Triangle(coordinate1: Coordinates2D, coordinate2: Coordinates2D, coordinate3: Coordinates2D) extends Figure
  case class Rectangle(coordinate1: Coordinates2D, coordinate2: Coordinates2D) extends Figure
  case class Circle(center: Coordinates2D, radius: Double) extends Figure
  case class Group(figures: List[Drawable]) extends Figure
  case class Colour(red: Int, green: Int, blue: Int, override val drawable: Drawable) extends Transformation {
    def equals(colour: Colour): Boolean = this.red == colour.red && this.green == colour.green && this.blue == colour.blue

    override def copyWithDrawable(_drawable: Drawable): Transformation = this.copy(drawable = _drawable)
  }
  case class Scale(x: Double, y: Double, override val drawable: Drawable) extends Transformation {
    def equals(scale: Scale): Boolean = this.x == scale.x && this.y == scale.y

    override def copyWithDrawable(_drawable: Drawable): Transformation = this.copy(drawable = _drawable)
  }
  case class Rotate(grade: Double, override val drawable: Drawable) extends Transformation {
    def equals(rotate: Rotate): Boolean = this.grade == rotate.grade

    override def copyWithDrawable(_drawable: Drawable): Transformation = this.copy(drawable = _drawable)
  }
  case class Relocate(x: Double, y: Double, override val drawable: Drawable) extends Transformation {
    def equals(relocate: Relocate): Boolean = this.x == relocate.x && this.y == relocate.y

    override def copyWithDrawable(_drawable: Drawable): Transformation = this.copy(drawable = _drawable)
  }
}
