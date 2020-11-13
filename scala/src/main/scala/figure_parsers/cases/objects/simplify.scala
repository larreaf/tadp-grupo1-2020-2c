package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure._

import scala.util.{Failure, Success, Try}


case object simplify extends Function[Figure, Figure] {

  val simplifyCommonColour: List[Figure] => Colour = (figures: List[Figure]) => {
    val colors = figures.asInstanceOf[List[Colour]]
    val head = colors.head
    Colour(
        head.red,
        head.green,
        head.blue,
        Group(for (color <- colors) yield simplify(color.figure))
    )
  }

  val groupHasCommonColor: List[Figure] => Boolean = (figures: List[Figure]) => {
    Try({
      val colors = figures.asInstanceOf[List[Colour]]
      val head = colors.head
      colors.forall(color => color.equals(head))
    }) match {
      case Success(result) => result
      case Failure(_) => false
    }
  }

  def apply(figureTree: Figure): Figure = figureTree match {
    case Group(figures) =>
      if (groupHasCommonColor(figures))
        simplifyCommonColour(figures)
      else
        Group(for (figure <- figures) yield simplify(figure))

    case Colour(_, _, _, Colour(_red, _green, _blue, figure)) => Colour(_red, _green, _blue, simplify(figure))
    case Rotate(grade, Rotate(_grade, figure)) => Rotate(grade + _grade, simplify(figure))
    case Scale(x, y, Scale(_x, _y, figure: Figure)) => Scale(x * _x, y * _y, simplify(figure))
    case Relocate(x, y, Relocate(_x, _y, figure)) => Relocate(x + _x, y + _y, simplify(figure))

    case Colour(red, green, blue, figure) => Colour(red, green, blue, simplify(figure))
    case Rotate(grade, figure) => if (grade == 0) simplify(figure) else Rotate(grade, simplify(figure))
    case Scale(x, y, figure) => if (x == 1 && y == 1) simplify(figure) else Scale(x, y, simplify(figure))
    case Relocate(x, y, figure) => if (x == 0 && y == 0) simplify(figure) else Relocate(x, y, simplify(figure))

    case figure: Figure => figure
  }
}