package figure_parsers.cases.objects

import figure_parsers.internal.Drawable
import figure_parsers.internal.Figure._

import scala.util.Try


case object simplify extends Function[Drawable, Drawable] {

  def simplifyGroup(drawables: List[Drawable]): Drawable = {
    drawables match {
      case colours: List[Colour] if this.allColoursAreEqual(colours) =>
        val colour = colours.head
        val drawables = colours.map(c => c.drawable)
        colour.copy(drawable = this.simplifyGroupInnerFigures(drawables))
      case _ => this.simplifyGroupInnerFigures(drawables)
    }
  }

  def simplifyGroupInnerFigures(drawables: List[Drawable]): Drawable = Group(for (drawable <- drawables) yield simplify(drawable))

  def allColoursAreEqual(colours: List[Colour]): Boolean = {
    Try({
      val head = colours.head
      colours.forall(colour => colour.equals(head))
    }).getOrElse(false)
  }

  def apply(drawableTree: Drawable): Drawable = drawableTree match {
    case Group(drawables) => this.simplifyGroup(drawables)

    case Colour(_, _, _, Colour(_red, _green, _blue, drawable)) => Colour(_red, _green, _blue, simplify(drawable))
    case Rotate(grade, Rotate(_grade, drawable)) => Rotate(grade + _grade, simplify(drawable))
    case Scale(x, y, Scale(_x, _y, drawable)) => Scale(x * _x, y * _y, simplify(drawable))
    case Relocate(x, y, Relocate(_x, _y, figure)) => Relocate(x + _x, y + _y, simplify(figure))

    case Rotate(0, drawable) => simplify(drawable)
    case Scale(1, 1, drawable) => simplify(drawable)
    case Relocate(0, 0, drawable) => simplify(drawable)

    case Colour(red, green, blue, drawable) => Colour(red, green, blue, simplify(drawable))
    case Rotate(grade, drawable) => Rotate(grade, simplify(drawable))
    case Scale(x, y, drawable) => Scale(x, y, simplify(drawable))
    case Relocate(x, y, drawable) => Relocate(x, y, simplify(drawable))

    case drawable => drawable
  }
}