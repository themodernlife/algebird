import java.io.{PrintWriter, StringWriter}

import org.specs2.mutable.Specification

object DecayedValueDemo1 {
  import com.twitter.algebird._

  val data = {
    val rand = new scala.util.Random(12345)
    (1 to 100).map { _ => rand.nextInt(1000).toDouble }.toSeq
  }

  val halfLife = 10.0
  val normalization = halfLife / math.log(2)

  implicit val monoid = DecayedValue.monoidWithEpsilon(1e-3)

  data.zipWithIndex.scanLeft(DecayedValue.zero) { case (previous, (value, time)) =>
    val decayed = Monoid.plus(previous, DecayedValue.build(value, time, halfLife))
    println("At %d: decayed=%f", time, (decayed.value / normalization))
    decayed
  }
  // prints At 0: decayed=17.397994...
  //#usage
}

class DecayedValueSpec extends Specification {
  "DecayedValue" should {
    "demonstrate usage" in {
      ok
    }
  }
}