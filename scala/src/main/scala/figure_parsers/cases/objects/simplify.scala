package figure_parsers.cases.objects

import figure_parsers.internal.{Drawable, Transformation}
import figure_parsers.internal.Figure._

import scala.util.Try


case object simplify extends Function[Drawable, Drawable] {

  def simplifyGroup(drawables: List[Drawable]): Drawable = {
    drawables match {
      case transformations: List[Transformation] if this.allTransformationsAreEqual(transformations) =>
        val tHead = transformations.head
        val drawables = transformations.map(t => t.drawable())
        tHead.copyWithDrawable(drawable = this.simplifyGroupInnerFigures(drawables))
      case _ => this.simplifyGroupInnerFigures(drawables)
    }
  }

  def simplifyGroupInnerFigures(drawables: List[Drawable]): Drawable = Group(for (drawable <- drawables) yield simplify(drawable))

  def allTransformationsAreEqual(transformations: List[Transformation]): Boolean = {
    Try(transformations.forall(t => t.equals(transformations.head)))
        .getOrElse(false)
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