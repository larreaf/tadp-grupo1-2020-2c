package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure._

import scala.util.Try


case object simplify extends Function[Figure, Figure] {

  def simplifyGroup(figures: List[Figure]): Figure = {
    figures match {
      case colours: List[Colour] if this.allColoursAreEqual(figures) =>
        val colour = colours.head
        val figures = colours.map(c => c.figure)
        colour.copy(figure = this.simplifyGroupInnerFigures(figures))
      case _ => this.simplifyGroupInnerFigures(figures)
    }
  }

  def simplifyGroupInnerFigures(figures: List[Figure]): Figure = Group(for (figure <- figures) yield simplify(figure))

  def allColoursAreEqual(figures: List[Figure]): Boolean = {
    Try({
      val colors = figures.asInstanceOf[List[Colour]]
      val head = colors.head
      colors.forall(color => color.equals(head))
    }).getOrElse(false)
  }

  def apply(figureTree: Figure): Figure = figureTree match {
    case Group(figures) => this.simplifyGroup(figures)

    case Colour(_, _, _, Colour(_red, _green, _blue, figure)) => Colour(_red, _green, _blue, simplify(figure))
    case Rotate(grade, Rotate(_grade, figure)) => Rotate(grade + _grade, simplify(figure))
    case Scale(x, y, Scale(_x, _y, figure: Figure)) => Scale(x * _x, y * _y, simplify(figure))
    case Relocate(x, y, Relocate(_x, _y, figure)) => Relocate(x + _x, y + _y, simplify(figure))

    case Rotate(0, figure) => simplify(figure)
    case Scale(1, 1, figure) => simplify(figure)
    case Relocate(0, 0, figure) => simplify(figure)

    case Colour(red, green, blue, figure) => Colour(red, green, blue, simplify(figure))
    case Rotate(grade, figure) => Rotate(grade, simplify(figure))
    case Scale(x, y, figure) => Scale(x, y, simplify(figure))
    case Relocate(x, y, figure) => Relocate(x, y, figure)

    case figure: Figure => figure
  }
}