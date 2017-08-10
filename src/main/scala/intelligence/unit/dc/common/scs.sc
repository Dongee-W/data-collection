import intelligence.unit.dc.common.Time
import org.joda.time.DateTime
val args = List()

val (start, end) = args.size match {
  case 0 => {
    (Time.formatTime(new DateTime().minusDays(1)), Time.formatTime(new DateTime()))
  }
  case 2 => (args(0), args(1))
}
