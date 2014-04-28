package com.twitter.algebird.docs

import org.specs2.mutable._

object HyperLogLog {
  //#create-monoid
  import com.twitter.algebird._

  implicit val hllMonoid = new HyperLogLogMonoid(12)
  //#create-monoid
}

object HyperLogLogCreate {

  import com.twitter.algebird._

  //#create-hll
  implicit def toBytes(s: String) = s.getBytes("utf-8")

  implicit val hllMonoid = new HyperLogLogMonoid(12)
  val a = hllMonoid("hello world")
  val b = hllMonoid.create("hello world")
  //a == b
  //#create-hll
}


class HyperLogLogSpec extends Specification {
  "HyperLogLog" should {
    "demonstrate creating an HLL instance via create or apply is the same" in {
      HyperLogLogCreate.a must be_==(HyperLogLogCreate.b)
    }

    "demonstrate adding items using the monoid" in {
      //
    }

    "demonstrate retrieving the estimated cardinality" in {
      //
    }

    "demonstrate writing to/from bytes" in {
      //
    }

    "demonstrate using implicit views from something to Array[Byte]" in {
      //
    }
  }
}
