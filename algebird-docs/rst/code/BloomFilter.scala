package com.twitter.algebird.docs

import org.specs2.mutable._
import com.twitter.algebird.ApproximateBoolean

object BloomFilterDemo1 {
  //#example
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  implicit val monoid = BloomFilter(200, 0.01)

  val bf1 = monoid.create("alice")
  val bf2 = monoid.create("bob")

  bf1.contains("bob")
  // definitely false

  bf2.contains("alice")
  // definitely false

  val combined = bf1 + bf2
  combined.contains("bob")
  combined.contains("alice")
  // both are true with 99% probability
  //#example
}

class BloomFilterSpec extends Specification {
  "BloomFilter" should {
    "demonstrate example usage" in {
      import BloomFilterDemo1._
      bf1.contains("bob") must be_==(ApproximateBoolean.exactFalse)
      bf2.contains("alice") must be_==(ApproximateBoolean.exactFalse)
      combined.contains("bob").isTrue must be_==(true)
      combined.contains("alice").isTrue must be_==(true)
    }
  }
}

