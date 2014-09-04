import com.twitter.algebird.AveragedValue
import java.io.{PrintWriter, StringWriter}

import org.specs2.mutable.Specification

object DecayedValueDemo1 {
  //#usage
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

object DecayedValueDemo2 {
  //#averages
  import scala.math.log
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  // Generate the list of cumulative sums using the implicit monoid
  def cumulativeSum[T : Monoid](list: List[T]) = {
    list.tail.scanLeft(list.head)(_ + _)
  }

  // S&P 500 closing prices for April 2014
  val raw = List(
    99.74, 100.25, 99.81, 96.61, 95.09, 95.96, 98.42, 96.28,
    96.2, 96.49, 97.36, 99.74, 99.94, 99.39, 99.8, 102.31,
    101.1, 99.17, 97.81, 98.85, 101.84
  )

  val days = 5 // window for moving average/decayed value
  val warmup = Seq.fill(days - 1)(Double.NaN) // no moving avgs until n days

  val simpleAverages = raw.map(AveragedValue(_))

  // a simple, cumulative average using the AveragedValue monoid
  val simpleAvgs = cumulativeSum(simpleAverages).map(_.value)

  // Moving average requires keeping O(N) memory
  val moving5DayAvgs = warmup ++ simpleAverages.sliding(days, 1).map{ (window) =>
    window.monoidSum.value
  }

  // DecayedValue can represent a moving average with O(1) memory
  implicit val monoid = (DecayedValue.monoidWithEpsilon(0.99))
  val decayedValues = raw.zipWithIndex.map { case (value, atTime) =>
    DecayedValue.build(value, atTime, days)
  }

  val decayedValueAvgs = warmup ++ cumulativeSum(decayedValues).drop(days - 1).map(_.average(days))
  //#averages
}

class DecayedValueSpec extends Specification {
  "DecayedValue" should {
    "demonstrate usage" in {
      ok
    }

    "show different moving averages" in {
      import DecayedValueDemo2._

      println("%-10s\t%-10s\t%-10s\t%-10s".format("Close", "Cumulative", "Moving", "Decayed"))
      for (i <- 0 until raw.size) {
        val (a, b, c, d) = (raw(i), simpleAvgs(i), moving5DayAvgs(i), decayedValueAvgs(i))
        println("%10.2f\t%10.2f\t%10.2f\t%10.2f".format(a, b, c, d))
      }

      simpleAvgs.size must be_==(moving5DayAvgs.size)
      moving5DayAvgs.size must be_==(decayedValueAvgs.size)
    }

    "show different moving averages" in {
      import DecayedValueDemo2._

      println("%-10s\t%-10s\t%-10s\t%-10s".format("Close", "Cumulative", "Moving", "Decayed"))
      for (i <- 0 until raw.size) {
        val (a, b, c, d) = (raw(i), simpleAvgs(i), moving5DayAvgs(i), decayedValueAvgs(i))
        println("%10.2f\t%10.2f\t%10.2f\t%10.2f".format(a, b, c, d))
      }

      simpleAvgs.size must be_==(moving5DayAvgs.size)
      moving5DayAvgs.size must be_==(decayedValueAvgs.size)
    }
  }
}