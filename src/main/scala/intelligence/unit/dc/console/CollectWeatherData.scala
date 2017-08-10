package intelligence.unit.dc.console

import java.io.{File, PrintWriter}

import intelligence.unit.dc.common.{HTMLCleaner, HttpRequest, SaveFile}
import org.apache.logging.log4j.LogManager

import scala.xml.XML

/**
  * Collect Weather Data in Taiwan's major cities.
  */
object CollectWeatherData {

  private val logger = LogManager.getLogger(CollectWeatherData.getClass)

  def download(month: String, station: String = "TAIPEI") = {
    logger.info(s"GET PAGE WITH month: $month  station: $station")
    val node = XML.loadString(HTMLCleaner.cleanse(HttpRequest.get(HttpRequest.urlBuilder(month, HttpRequest.stationCode(station)))))
    val tableRows = node \\ "tr" drop 2 // First two rows are useless
    val headerRow = tableRows(0) \ "th" map (node => node.buildString(false).split("\n")(1).drop(7))
    val dataRow = tableRows.drop(1).map(row => row \ "td" map (node => node.text))

    val firstColumnUpdated = dataRow.map {
      row => (month + "-" + f"${row.head.toInt}%02d") +: row.drop(1)
    }

    val headerString = headerRow.mkString(",") + "\n"
    val dataString = firstColumnUpdated.map(rowList => rowList.mkString(",")).mkString("\n")

    val filename = s"output\\weather_${month}_$station.csv"
    logger.info(s"START WRITING FILE: $filename")
    val writer = new PrintWriter(new File(filename))
    writer.write(headerString + dataString)
    writer.close()
    logger.info(s"$filename WRITTEN.")
  }

  def loadData(month: String, station: String = "TAIPEI") = {
    logger.info(s"GET PAGE WTIH time: $month  station: $station")
    val node = XML.loadString(HTMLCleaner.cleanse(HttpRequest.get(HttpRequest.urlBuilder(month, HttpRequest.stationCode(station)))))
    val tableRows = node \\ "tr" drop 2 // First two rows are useless
    val headerRow = tableRows(0) \ "th" map (node => node.buildString(false).split("\n")(1).drop(7))
    val dataRow = tableRows.drop(1).map(row => row \ "td" map (node => node.text))

    val firstColumnUpdated = dataRow.map {
      row => (month + "-" + f"${row.head.toInt}%02d") +: row.drop(1)
    }

    val headerString = headerRow.mkString(",") + "\n"
    val dataString = firstColumnUpdated.map(rowList => rowList.mkString(",")).mkString("\n")

    (headerString, dataString)
  }

  def main(args: Array[String]): Unit = {
    val station = "TAINAN"
    download("2016-03", "TAINAN")
    /*
    val data1 = (1 to 12) map (a => loadData(f"2016-$a%02d", station)._2) mkString ("\n")
    val data2 = (1 to 3) map (a => loadData(f"2017-$a%02d", station)._2) mkString ("\n")
    SaveFile.save(s"output\\weather_$station.csv", loadData(f"2016-01", station)._1 + "\n" + data1 + " \n" + data2)*/


  }
}
