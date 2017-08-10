package intelligence.unit.dc.common

import intelligence.unit.dc.console.CollectOrderTransactions

/**
  * Some CSV-related functions
  */
object CSV {
  def formatColumn(columnContent: Number): String = columnContent.toString

  def formatColumn(columnContent: String): String = "\"" + columnContent.replace("\"", "\\\"") + "\""

  def main(args: Array[String]): Unit = {
  }
}
