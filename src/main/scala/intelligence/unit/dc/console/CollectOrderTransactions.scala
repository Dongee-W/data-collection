package intelligence.unit.dc.console

import com.typesafe.config.ConfigFactory
import intelligence.unit.dc.common.{CSV, SaveFile, Time}
import org.apache.logging.log4j.LogManager
import org.joda.time.DateTime
import slick.jdbc.OracleProfile.api._

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.control.NonFatal

/**
  * Collect order transactions from BO database.
  */
object CollectOrderTransactions {
  private val config = ConfigFactory.load()
  private val logger = LogManager.getLogger(CollectWeatherData.getClass)

  def main(args: Array[String]): Unit = {
    logger.debug("Current environment: " + config.getString("environment"))

    val (start, end) = if (config.getString("environment") == "development") {
      ("2017-05-05 00:00:00", "2017-05-06 00:00:00")
    } else {
      args.size match {
        case 0 => {
          (Time.formatTime(new DateTime().minusDays(1).withTimeAtStartOfDay()), Time.formatTime(new DateTime().withTimeAtStartOfDay()))
        }
        case 2 => (args(0), args(1))
      }
    }
    logger.info(s"Query $start - $end")

    val db = Database.forConfig("BO")
    try {
      val sql =
        sql"""SELECT m.orderid, TO_CHAR(m.ORDERTIME,'YYYY-MM-DD HH24:MI:SS') AS ordertime, p.productid, p.houseid, p.productname
             FROM EC_APUSER.ord_order_menu m, EC_APUSER.ord_order_products p
             WHERE m.orderid = p.orderid
             AND m.ordertime >= TO_DATE('#$start','yyyy-mm-dd hh24:mi:ss')
             AND m.ordertime < TO_DATE('#$end','yyyy-mm-dd hh24:mi:ss')
          """.as[(String, String, Int, Int, String)]

      val setupFuture = db.run(sql)
      logger.info(s"Start BO connections ...")

      val criticalTask = setupFuture.map { resultSet =>
        val header = "ORDERID,ORDERTIME,PRODUCTID,HOUSEID,PRODUCTNAME\n"
        logger.info(s"Retrieve ${resultSet.size} transactions.")
        logger.info(s"Top 20 entries: \n\t ${resultSet.take(20).mkString("\n\t")}")
        val csvContent = header + resultSet.map {
          entry =>
            CSV.formatColumn(entry._1) + "," +
              CSV.formatColumn(entry._2) + "," +
              CSV.formatColumn(entry._3) + "," +
              CSV.formatColumn(entry._4) + "," +
              CSV.formatColumn(entry._5) + ","
        }.mkString("\n")


        val filenameTail = "_" + start.take(10) + "_" + end.take(10) + ".csv"
        val filename = config.getString("OutputPathBase") + filenameTail
        logger.info(s"Saving $filename ...")
        SaveFile.save(filename, csvContent)
        logger.info(s"File saved: $filename")
      }

      criticalTask onFailure {
        case NonFatal(ex) => logger.error(ex.getMessage)
        case _ => logger.error("Unexpected error...")
      }

      Await.result(criticalTask, 60 seconds)

    } finally db.close
  }
}
