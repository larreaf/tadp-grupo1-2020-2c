package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.{Circle, Colour, Group, Rectangle, Relocate, Rotate, Scale, Triangle}

import scala.util.{Failure, Success, Try}


case object simplify extends Function[Figure, Figure] {

  def apply(figureTree: Figure): Figure = figureTree match {
    case Group(figures) => {
      val commonColor = Try(figures.head.asInstanceOf[Colour])
      val figuresInsideColors = groupWithCommonColor(commonColor)(figures)

      figuresInsideColors match {
        case Success(figs) => Colour(commonColor.get.red, commonColor.get.green, commonColor.get.blue, simplify(Group(figs)))
        case Failure(_) => Group(for (figure <- figures) yield simplify(figure))
      }
    }

    case Colour(_, _, _, Colour(_red, _green, _blue, figure)) => Colour(_red, _green, _blue, simplify(figure))
    case Rotate(grade, Rotate(_grade, figure)) => Rotate(grade + _grade, simplify(figure))
    case Scale(x, y, Scale(_x, _y, figure: Figure)) => Scale(x * _x, y * _y, simplify(figure))
    case Relocate(x, y, Relocate(_x, _y, figure)) => Relocate(x + _x, y + _y, simplify(figure))

    case Colour(red, green, blue, figure) => Colour(red, green, blue, simplify(figure))
    case Rotate(grade, figure) => if(grade == 0) simplify(figure) else Rotate(grade, simplify(figure))
    case Scale(x, y, figure) => if(x == 1 && y == 1) simplify(figure) else Scale(x, y, simplify(figure))
    case Relocate(x, y, figure) => if(x == 0 && y == 0) simplify(figure) else Relocate(0, 0, simplify(figure))

    case Rectangle(coordinate1, coordinate2) => Rectangle(coordinate1, coordinate2)
    case Triangle(coordinate1, coordinate2, coordinate3) => Triangle(coordinate1, coordinate2, coordinate3)
    case Circle(center, radius) => Circle(center, radius)
  }
}

case class groupWithCommonColor(commonColor: Try[Colour]) extends (List[Figure] => Try[List[Figure]]){

  def apply(figures: List[Figure]): Try[List[Figure]] = {
    Try({
      val figuresWithoutColour = for {
        color <- figures.asInstanceOf[List[Colour]]
        if color.equals(commonColor.get)
      } yield color.figure

      if (!figuresWithoutColour.size.equals(figures.size))
        throw new Error
      else
        figuresWithoutColour
    })
  }
}