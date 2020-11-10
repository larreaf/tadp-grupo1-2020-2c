package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.{Circle, Colour, Group, Rectangle, Relocate, Rotate, Scale, Triangle}


case object simplify extends Function[Figure, Figure] {
  def apply(figureTree: Figure): Figure = figureTree match {
    case Group(figures) => {
      if (figures.forall(c => c match {case c:Colour => c.equals(figures.head) case _ => false } )) {
        figureTree match {
          case Group(figures: List[Colour]) => Colour(figures.head.red, figures.head.green, figures.head.blue, simplify(Group(figures.map(c => c.figure))))
        }
      }
      else
        Group(figures.map(f => simplify(f)))
    }
    case Colour(_, _, _, Colour(_red, _green, _blue, figure)) => Colour(_red, _green, _blue, simplify(figure))
    case Rotate(grade, Rotate(_grade, figure)) => Rotate(grade + _grade, simplify(figure))
    case Scale(x, y, Scale(_x, _y, figure: Figure)) => Scale(x * _x, y * _y, simplify(figure))
    case Relocate(x, y, Relocate(_x, _y, figure)) => Relocate(x + _x, y + _y, simplify(figure))
    case Rectangle(coordinate1, coordinate2) => Rectangle(coordinate1, coordinate2)
    case Triangle(coordinate1, coordinate2, coordinate3) => Triangle(coordinate1, coordinate2, coordinate3)
    case Circle(center, radius) => Circle(center, radius)
  }
}
