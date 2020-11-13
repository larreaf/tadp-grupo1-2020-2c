package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.{Circle, Colour, Group, Rectangle, Relocate, Rotate, Scale, Triangle}
import figure_parsers.main_figure_parsers.{figures, resultOfSimplify}

import scala.util.{Failure, Success, Try}


case object simplify extends Function[Figure, Figure] {

  val simplifyCommonColour = (figures: List[Figure]) => {
    val colors = figures.asInstanceOf[List[Colour]]
    val head = colors.head
    Colour(
        head.red,
        head.green,
        head.blue,
        Group(for (color <- colors) yield simplify(color.figure))
    )
  }

  val groupHasCommonColor = (figures: List[Figure]) => {
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
    case Group(figures) => {
      if (groupHasCommonColor(figures))
        simplifyCommonColour(figures)
      else
        Group(for (figure <- figures) yield simplify(figure))
    }
    case Colour(_, _, _, Colour(_red, _green, _blue, figure)) => Colour(_red, _green, _blue, simplify(figure))
    case Rotate(grade, Rotate(_grade, figure)) => Rotate(grade + _grade, simplify(figure))
    case Scale(x, y, Scale(_x, _y, figure: Figure)) => Scale(x * _x, y * _y, simplify(figure))
    case Relocate(x, y, Relocate(_x, _y, figure)) => Relocate(x + _x, y + _y, simplify(figure))

    case Colour(red, green, blue, figure) => Colour(red, green, blue, simplify(figure))
    case Rotate(grade, figure) => if (grade == 0) simplify(figure) else Rotate(grade, simplify(figure))
    case Scale(x, y, figure) => if (x == 1 && y == 1) simplify(figure) else Scale(x, y, simplify(figure))
    case Relocate(x, y, figure) => if (x == 0 && y == 0) simplify(figure) else Relocate(0, 0, simplify(figure))

    case Rectangle(coordinate1, coordinate2) => Rectangle(coordinate1, coordinate2)
    case Triangle(coordinate1, coordinate2, coordinate3) => Triangle(coordinate1, coordinate2, coordinate3)
    case Circle(center, radius) => Circle(center, radius)
  }
}