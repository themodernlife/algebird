package com.twitter.algebird.docs

import org.specs2.mutable._

object HyperLogLogDemo1 {
  //#create-monoid
  import com.twitter.algebird._

  implicit val hllMonoid = new HyperLogLogMonoid(12)
  //#create-monoid
}

object HyperLogLogDemo2 {

  import com.twitter.algebird._

  //#create-hll
  implicit def toBytes(s: String) = s.getBytes("utf-8")

  implicit val hllMonoid = new HyperLogLogMonoid(12)

  val a = hllMonoid("hello world")
  val b = hllMonoid.create("hello world")
  //a == b
  //#create-hll
}

object HyperLogLogDemo3 {
  //#distinct
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  implicit def toBytes(s: String) = s.getBytes("utf-8")
  implicit val hllMonoid = new HyperLogLogMonoid(12)

  val sample = List("user1", "user2", "user2", "user2", "user1", "user3")
  val finalHll = sample.map(hllMonoid(_)).monoidSum
  val size = finalHll.estimatedSize
  // 3
  //#distinct
}

object HyperLogLogDemo4 {
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  implicit def toBytes(s: String) = s.getBytes("utf-8")
  implicit val hllMonoid = new HyperLogLogMonoid(12)

  val sample = List("user1", "user2", "user2", "user2", "user1", "user3")
  val finalHll = sample.map(hllMonoid(_)).monoidSum

  //#approximate
  val approx = finalHll.approximateSize
  val (min, estimate, max, probWithinBounds) = (approx.min, approx.estimate, approx.max, approx.probWithinBounds)
  // (2, 3, 4, 0.9972)
  //#approximate

  val hll = finalHll

  //#serialize
  val bytes = HyperLogLog.toBytes(finalHll)
  val newHll = HyperLogLog.fromBytes(bytes)
  // newHll == finalHll
  //#serialize
}

object HyperLogLogDemo5 {
  import com.twitter.algebird._
  import com.twitter.algebird.Operators._

  //#num-bits
  def sumWithNBits(samples: Seq[String], nBits: Int) = {
    implicit def toBytes(s: String) = s.getBytes("utf-8")
    implicit val hllMonoid = new HyperLogLogMonoid(nBits)

    samples.map(hllMonoid(_)).monoidSum.estimatedSize
  }

  val samples = List(
    "TcqE", "ERGp", "zvPL", "oNRf", "UMdG", "nlvN", "cDun", "bxTD", "gHAy", "XLtM",
    "OaHR", "vxCz", "olFU", "xObA", "AHyz", "ryUd", "CcBt", "vrpQ", "HsdJ", "qDCp",
    "TcqE", "ERGp", "zvPL", "oNRf", "UMdG", "nlvN", "cDun", "bxTD", "gHAy", "XLtM",
    "rjyW", "MTdy", "GnuV", "TSUi", "qCgX", "VsZo", "zHgs", "YpCW", "znBt", "TaKk",
    "vUtJ", "TqEz", "HnVN", "VhTd", "nDSY", "UCWs", "bBzC", "AIgt", "KLQM", "MVSR"
  )

  // too costly for large data sets - use the HLL instead
  val trueUniques = samples.distinct.size
  // 40

  val estimate8 = sumWithNBits(samples, 8)
  // 38.0

  val estimate12 = sumWithNBits(samples, 12)
  // 40.0
  //#num-bits
}


class HyperLogLogSpec extends Specification {
  "HyperLogLog" should {
    "demonstrate creating an HLL instance via create or apply is the same" in {
      HyperLogLogDemo2.a must be_==(HyperLogLogDemo2.b)
    }

    "demonstrate retrieving the estimated cardinality" in {
      HyperLogLogDemo3.size must be_==(3)
    }

    "demonstrate retrieving the error bounds" in {
      import HyperLogLogDemo4._
      (min, estimate, max, probWithinBounds) must be_==((2, 3, 4, 0.9972))
    }

    "demonstrate writing to/from bytes" in {
      import HyperLogLogDemo4._
      newHll must be_==(finalHll)
    }

    "demonstrate choosing the number of bits" in {
      import HyperLogLogDemo5._
      trueUniques must be_==(40)
      estimate8 must be_==(38.0)
      estimate12 must be_==(40.0)
    }

    "demonstrate using implicit views from something to Array[Byte]" in {
      //
    }
  }
}
