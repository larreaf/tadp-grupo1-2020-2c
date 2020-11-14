package tadp
import java.nio.file.{FileSystems, Files}

import figure_parsers.cases.objects.{draw, figureParser}
import tadp.internal.TADPDrawingAdapter

import scala.io.Source
import scala.util.{Failure, Success, Try}

case object parseAndDraw extends ((String, TADPDrawingAdapter) => Any){

  val resourcesDir = "resources/"
  val ext = ".txt"

  def apply (imageNameOrText: String, adapter: TADPDrawingAdapter): Any = {
    Try(_parseAndDraw(imageNameOrText, adapter)) match {
      case Success(parseResult) => parseResult
      case Failure(_) => throw new Exception("No existe el archivo o el texto no se pudo parsear.")
    }
  }

  def checkIfFile(fileName: String): Boolean = {
    Files.exists(
          FileSystems.getDefault
                     .getPath( resourcesDir + normalize(fileName) + ext)
    )
  }

  def normalize(str: String): String = {
    str.replace("/", "")
       .replace("\\", "")
       .replace(".","")
       .replace("\n", "")
       .replace("\t", "")
  }

  val parseAndDrawFile: (String, TADPDrawingAdapter) => TADPDrawingAdapter = (imageName: String, adapter: TADPDrawingAdapter) => {
    var figureFile = ""

    val bufferedSource = Source.fromFile(resourcesDir + imageName + ext)
    for (line <- bufferedSource.getLines) {
      figureFile = figureFile.concat(line)
    }
    bufferedSource.close
    parseAndDrawText(figureFile, adapter)
  }

  val parseAndDrawText: (String, TADPDrawingAdapter) => TADPDrawingAdapter = (text: String, adapter: TADPDrawingAdapter) => {
    val testDraw = figureParser(text)
    draw(testDraw.get.parsed)(adapter)
  }


  val _parseAndDraw: (String, TADPDrawingAdapter) => TADPDrawingAdapter = (imageNameOrText: String, adapter: TADPDrawingAdapter) => {
    if (checkIfFile(imageNameOrText))
      parseAndDrawFile(imageNameOrText, adapter)
    else
      parseAndDrawText(imageNameOrText, adapter)
  }


}