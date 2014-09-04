package com.twitter.algebird.docs

import com.twitter.algebird._
import org.specs2.mutable._

object AveragedValueDemo1 {
  //#example
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  val sample: List[Double] = List(8, 3, 6, 6, 8, 1, 7, 2, 2, 5)
  val average = sample.map { num => AveragedValue(num) }.reduce(_ + _).value
  // returns 4.8
  //#example
}

object AveragedValueDemo2 {
  //#increased-accuracy
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  val sample: List[Double] = List(
    4, 4, 2, 1, 1, 5, 5, 1, 4, 1, 8, 3, 1, 7, 8, 5, 8, 4, 2, 4,
    2, 4, 2, 6, 4, 5, 6, 3, 6, 10, 1, 1, 0, 10, 7, 7, 5, 9, 4, 9,
    8, 3, 6, 6, 8, 1, 7, 2, 2, 5, 8, 4, 9, 10, 9, 3, 6, 2, 1, 4,
    4, 5, 7, 4, 5, 5, 2, 8, 10, 5, 7, 6, 2, 6, 1, 1, 8, 4, 7, 8,
    7, 7, 6, 7, 8, 7, 6, 5, 6, 6, 9, 10, 7, 6, 9, 5, 6, 9, 5, 10
  )

  val naiveAverage = sample.reduce(_ + _) / sample.size
  // returns 5.29

  val algebirdAverage = sample.map(AveragedValue(_)).reduce(_ + _).value
  // returns 5.289999999999999
  //#increased-accuracy
}

class AveragedValueSpec extends Specification {
  "AveragedValue" should {
    "average a sample" in {
      AveragedValueDemo1.average must be_==(4.8)
    }

    "demonstrate increased accuracy" in {
      AveragedValueDemo2.naiveAverage must not be_==(AveragedValueDemo2.algebirdAverage)
    }
  }
}