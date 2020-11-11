package tadp

import figure_parsers.cases.objects.{draw, figureParser}
import figure_parsers.internal.Figure
import figure_parsers.main_figure_parsers.testDraw
import scalafx.scene.paint.Color
import tadp.internal.TADPDrawingAdapter

import scala.io.Source

object TADPDrawingApp extends App {
  
  val parseAndDrawFile = (imageName: String, adapter: TADPDrawingAdapter) =>{
    var figureFile = ""

    val bufferedSource = Source.fromFile("resources/"+imageName+".txt")
    for (line <- bufferedSource.getLines) {
      figureFile = figureFile.concat(line)
    }
    bufferedSource.close
    val testDraw = figureParser(figureFile)
    draw(testDraw.get.parsed)(adapter)
  }


  TADPDrawingAdapter.forInteractiveScreen { (imageDescription, adapter) =>
    parseAndDrawFile(imageDescription, adapter)
  }

}

