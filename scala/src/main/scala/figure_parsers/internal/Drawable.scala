package figure_parsers.internal

import figure_parsers.cases.Coordinates2D

sealed trait Drawable {
  def simplify: Drawable
  def drawable: Drawable
}
sealed trait Figure extends Drawable {
  override def drawable: Drawable = this
}
sealed trait AtomicFigure extends Figure {
  override def simplify: Drawable = this
}
sealed trait Transformation extends Drawable {
  val drawable: Drawable
  def change(drawable: Drawable): Transformation
}
object Figure {
  case class Triangle(coordinate1: Coordinates2D, coordinate2: Coordinates2D, coordinate3: Coordinates2D) extends AtomicFigure
  case class Rectangle(coordinate1: Coordinates2D, coordinate2: Coordinates2D) extends AtomicFigure
  case class Circle(center: Coordinates2D, radius: Double) extends AtomicFigure
  case class Group(drawables: List[Drawable]) extends Figure {
    override def simplify: Drawable = {
      drawables.head match {
        case transformation: Transformation if drawables.slice(1, drawables.length)
                                                        .forall(d => d == transformation) =>
          transformation.change(this.simplifiedGroup(drawables.map(d => d.drawable)))
        case _ => this.simplifiedGroup(this.drawables)
      }
    }

    private def simplifiedGroup(drawables: List[Drawable]): Drawable = this.copy(drawables = drawables.map(d => d.simplify))
  }
  case class Colour(red: Int, green: Int, blue: Int, drawable: Drawable) extends Transformation {
    override def hashCode(): Int = (red, green, blue).##
    override def change(drawable: Drawable): Transformation = this.copy(drawable = drawable)
    override def simplify: Drawable = this match {
      case Colour(_, _, _, Colour(_red, _green, _blue, drawable)) => Colour(_red, _green, _blue, drawable.simplify)
      case Colour(red, green, blue, drawable) => Colour(red, green, blue, drawable.simplify)
    }
  }
  case class Scale(x: Double, y: Double, drawable: Drawable) extends Transformation {
    override def hashCode(): Int = (x, y).##
    override def change(drawable: Drawable): Transformation = this.copy(drawable = drawable)
    override def simplify: Drawable = this match {
      case Scale(x, y, Scale(_x, _y, drawable)) => Scale(x * _x, y * _y, drawable.simplify)
      case Scale(1, 1, drawable) => drawable.simplify
      case Scale(_, _, drawable) => this.change(drawable.simplify)
    }
  }
  case class Rotate(grade: Double, drawable: Drawable) extends Transformation {
    override def hashCode(): Int = grade.##
    override def change(drawable: Drawable): Transformation = this.copy(drawable = drawable)
    override def simplify: Drawable = this match {
      case Rotate(grade, Rotate(_grade, drawable)) => Rotate(grade + _grade, drawable.simplify)
      case Rotate(0, drawable) => drawable.simplify
      case Rotate(grade, drawable) => Rotate(grade, drawable.simplify)
    }
  }
  case class Relocate(x: Double, y: Double, drawable: Drawable) extends Transformation {
    override def hashCode(): Int = (x, y).##
    override def change(drawable: Drawable): Transformation = this.copy(drawable = drawable)
    override def simplify: Drawable = this match {
      case Relocate(x, y, Relocate(_x, _y, drawable)) => Relocate(x + _x, y + _y, drawable.simplify)
      case Relocate(0, 0, drawable) => drawable.simplify
      case Relocate(_, _, drawable) => this.change(drawable.simplify)
    }
  }
}
