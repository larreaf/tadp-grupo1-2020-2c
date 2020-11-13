package tadp
import java.nio.file.{FileSystems, Files}

import figure_parsers.cases.objects.{draw, figureParser}
import tadp.internal.TADPDrawingAdapter

import scala.io.Source

case object parseAndDraw extends ((String, TADPDrawingAdapter) => Any){

  def apply (imageNameOrText: String, adapter: TADPDrawingAdapter): Any = {
      if (checkIfFile(imageNameOrText))
        parseAndDrawFile(imageNameOrText, adapter)
      else
        parseAndDrawText(imageNameOrText, adapter)
  }


  def checkIfFile(fileName: String): Boolean = {
    val imageFileName = fileName.replace("/", "").replace("\\", "")
    Files.exists(FileSystems.getDefault().getPath("resources/" + fileName + ".txt"))
  }

  val parseAndDrawFile = (imageName: String, adapter: TADPDrawingAdapter) => {
    var figureFile = ""

    val bufferedSource = Source.fromFile("resources/" + imageName + ".txt")
    for (line <- bufferedSource.getLines) {
      figureFile = figureFile.concat(line)
    }
    bufferedSource.close
    parseAndDrawText(figureFile, adapter)
  }

  val parseAndDrawText = (text: String, adapter: TADPDrawingAdapter) => {
    val testDraw = figureParser(text)
    draw(testDraw.get.parsed)(adapter)
  }



}