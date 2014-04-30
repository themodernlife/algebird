
import org.specs2.mutable._


object ApproximateDemo {
  import com.twitter.algebird.Approximate

  val a = Approximate(150, 150, 150, 1.0)
  val b = Approximate(250, 250, 250, 1.0)
  a + b
}

class ApproximateSpec extends Specification {
  "Apprxomiate" should {
    "demonstrate creating an HLL instance via create or apply is the same" in {
    }
  }
}

