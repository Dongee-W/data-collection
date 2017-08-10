package intelligence.unit.dc.common

/**
  * Created by bradleywu on 4/6/2017.
  */
object HttpRequest {

  val stationCode = Map("TAIPEI" -> "466920", "NEW TAIPEI CITY" -> "466880",
    "TAICHUNG" -> "467490", "TAINAN" -> "467410", "KAOHSIUNG" -> "467440")

  def get(url: String) = scala.io.Source.fromURL(url).mkString.replace("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">", "")

  def urlBuilder(month: String, stationCode: String = "466920") = {
    s"http://e-service.cwb.gov.tw/HistoryDataQuery/MonthDataController.do?command=viewMain&station=$stationCode&stname=%25E8%2587%25BA%25E5%258C%2597&datepicker=$month"
  }

  def main(args: Array[String]): Unit = {
    println(get(urlBuilder("2017-03", "466920")))
  }
}
