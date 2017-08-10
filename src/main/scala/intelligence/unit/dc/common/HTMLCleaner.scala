package intelligence.unit.dc.common

import org.htmlcleaner.{CleanerProperties, HtmlCleaner, PrettyXmlSerializer}

/**
  * Created by bradleywu on 4/6/2017.
  */
object HTMLCleaner {

  def cleanse(rawHtml: String) = {
    val props = new CleanerProperties()

    // set some properties to non-default values
    props.setTranslateSpecialEntities(true)
    props.setTransResCharsToNCR(true)
    props.setOmitComments(true)

    val cleaner = new HtmlCleaner()
    val node = cleaner.clean(rawHtml)

    new PrettyXmlSerializer(props).getAsString(node, "utf-8")
  }

  def main(args: Array[String]): Unit = {
    println(cleanse(HttpRequest.get(HttpRequest.urlBuilder("2017-03"))))

  }
}
