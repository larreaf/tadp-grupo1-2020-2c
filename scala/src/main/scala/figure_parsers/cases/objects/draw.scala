package figure_parsers.cases.objects

import figure_parsers.internal.Figure
import figure_parsers.internal.Figure.{Circle, Colour, Group, Rectangle, Relocate, Rotate, Scale, Triangle}
import scalafx.scene.paint.Color
import tadp.internal.TADPDrawingAdapter

case class draw(figure: Figure) extends (TADPDrawingAdapter => TADPDrawingAdapter){
  def apply(adapter: TADPDrawingAdapter): TADPDrawingAdapter = figure match {
    case Group(figures) => figures.foldLeft(adapter) { (_adapter, fig) => draw(fig)(_adapter)}
    case Colour(red, green, blue, _figure) => {
      draw(_figure)(adapter.beginColor(Color.rgb(red, green, blue)))
        .end()
    }
    case Rotate(grade, _figure) => {
      draw(_figure)(adapter.beginRotate(grade.toDouble))
        .end()
    }
    case Scale(x, y, _figure) => {
      draw(_figure)(adapter.beginScale(x, y))
        .end()
    }
    case Relocate(x, y, _figure) => {
      draw(_figure)(adapter.beginTranslate(x.toDouble, y.toDouble))
        .end()
    }
    case Rectangle(coordinate1, coordinate2) => adapter.rectangle((coordinate1.x.toDouble, coordinate1.y.toDouble), (coordinate2.x.toDouble, coordinate2.y.toDouble))
    case Triangle(coordinate1, coordinate2, coordinate3) => adapter.triangle((coordinate1.x.toDouble, coordinate1.y.toDouble), (coordinate2.x.toDouble, coordinate2.y.toDouble), (coordinate3.x.toDouble, coordinate3.y.toDouble))
    case Circle(center, radius) => adapter.circle((center.x.toDouble, center.y.toDouble), radius.toDouble)
  }
}

