import org.specs2.mutable._

object CountMinSketchDemo1 {
  //#example
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  implicit val cms = CMS.monoid(0.01, 0.01, 12345)

  val samples = Seq.fill(100)(12345L) ++ Seq.fill(100)(54321L) ++ Seq.fill(1000)(scala.math.random.toLong)
  val soFar = samples.map(cms.create(_)).monoidSum

  soFar.heavyHitters
  // Set(12345L, 54321L, ...)

  soFar.frequency(12345L).estimate
  // 100
  //#example
}

class CountMinSketchSpec extends Specification {
  "CountMinSketch" should {
    "demonstrate usage" in {
      import CountMinSketchDemo1._
      soFar.heavyHitters must contain(12345L)
      soFar.heavyHitters must contain(54321L)
      soFar.frequency(12345L).estimate must be_==(100)
    }
  }
}