package figure_parsers.cases.objects

import figure_parsers.internal.Drawable
import figure_parsers.internal.Figure._
import scalafx.scene.paint.Color
import tadp.internal.TADPDrawingAdapter

case class draw(drawable: Drawable) extends (TADPDrawingAdapter => TADPDrawingAdapter){
  def apply(adapter: TADPDrawingAdapter): TADPDrawingAdapter = drawable match {
    case Group(drawables) => drawables.foldLeft(adapter) { (_adapter, _figure) => draw(_figure)(_adapter)}

    case Colour(red, green, blue, _figure) => draw(_figure)(adapter.beginColor(Color.rgb(red, green, blue))).end()
    case Rotate(grade, _figure) => draw(_figure)(adapter.beginRotate(grade)).end()
    case Scale(x, y, _figure) => draw(_figure)(adapter.beginScale(x, y)).end()
    case Relocate(x, y, _figure) => draw(_figure)(adapter.beginTranslate(x, y)).end()

    case Rectangle(coordinate1, coordinate2) => adapter.rectangle((coordinate1.begin, coordinate1.end), (coordinate2.begin, coordinate2.end))
    case Triangle(coordinate1, coordinate2, coordinate3) => adapter.triangle((coordinate1.begin, coordinate1.end), (coordinate2.begin, coordinate2.end), (coordinate3.begin, coordinate3.end))
    case Circle(center, radius) => adapter.circle((center.begin, center.end), radius.toDouble)
  }
}

