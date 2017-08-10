package intelligence.unit.dc.common

import java.io.{File, PrintWriter}

/**
  * Created by bradleywu on 2017/4/24.
  */
object SaveFile {
  def save(filePath: String, content: String) = {
    val writer = new PrintWriter(new File(filePath))
    writer.write(content)
    writer.close()
  }
}
