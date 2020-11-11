package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.{Circle, Colour, Group, Rectangle, Relocate, Rotate, Scale, Triangle}
import scalafx.scene.paint.Color
import tadp.internal.TADPDrawingAdapter

case class draw(figure: Figure) extends (TADPDrawingAdapter => Any){
  def apply(adapter: TADPDrawingAdapter): Any = figure match {
    case Group(figures) => for (figure <- figures) yield draw(figure)(adapter)
    case Colour(red, green, blue, _figure) => {
      val _adapter = adapter.beginColor(Color.rgb(red, green, blue))
      draw(_figure)(_adapter)
      _adapter.end()
    }
    case Rotate(grade, _figure) => {
      val _adapter = adapter.beginRotate(grade.toDouble)
      draw(_figure)(_adapter)
      _adapter.end()
    }
    case Scale(x, y, _figure) => {
      val _adapter = adapter.beginScale(x, y)
      draw(_figure)(_adapter)
      _adapter.end()
    }
    case Relocate(x, y, _figure) => {
      val _adapter = adapter.beginTranslate(x.toDouble, y.toDouble)
      draw(_figure)(_adapter)
      _adapter.end()
    }
    case Rectangle(coordinate1, coordinate2) => adapter.rectangle((coordinate1.x.toDouble, coordinate1.y.toDouble), (coordinate2.x.toDouble, coordinate2.y.toDouble))
    case Triangle(coordinate1, coordinate2, coordinate3) => adapter.triangle((coordinate1.x.toDouble, coordinate1.y.toDouble), (coordinate2.x.toDouble, coordinate2.y.toDouble), (coordinate3.x.toDouble, coordinate3.y.toDouble))
    case Circle(center, radius) => adapter.circle((center.x.toDouble, center.y.toDouble), radius.toDouble)
  }
}
