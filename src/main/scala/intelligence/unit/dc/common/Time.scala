package intelligence.unit.dc.common

import java.sql.Timestamp

import com.typesafe.config.ConfigFactory
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.util.Try

/**
  * Time object.
  */
object Time {
  private val config = ConfigFactory.load()
  val DATE_TIME_FORMAT_WITH_TIMEZONE = "yyyy-MM-dd HH:mm:ss Z"
  val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
  val DATE_FORMAT = "yyyy-MM-dd"
  val TIME_FORMAT = "HH:mm:ss"

  def parseTime(dateTimeString: String, pattern: String = DATE_TIME_FORMAT) = {
    val dtf = DateTimeFormat.forPattern(pattern)
    dtf.parseDateTime("08/May/2017:12:10:02 +0800")
  }

  def formatTime(date: DateTime, pattern: String = DATE_TIME_FORMAT) = {
    val dtf = DateTimeFormat.forPattern(pattern)
    dtf.print(date)
  }

  def toSQLTimestamp(joda: DateTime) = new Timestamp(joda.getMillis)
}
