package com.twitter.algebird.docs

import org.specs2.mutable._
import com.twitter.algebird._
import com.twitter.algebird.Operators._


object Example {
  //#intro-example
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  val maxMap = Map(1 -> Max(2)) + Map(1 -> Max(3)) + Map(2 -> Max(4))
  // returns Map(2 -> Max(4), 1 -> Max(3))
  //#intro-example
}

class ExampleSpec extends Specification {
  "Example" should {
    "demonstrate summing a map" in {
      Example.maxMap must be_==(Map(2 -> Max(4), 1 -> Max(3)))
    }
  }
}
