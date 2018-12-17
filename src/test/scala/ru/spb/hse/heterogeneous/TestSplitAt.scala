package ru.spb.hse.heterogeneous

import org.scalatest._
import ru.spb.hse.heterogeneous.HList._
import ru.spb.hse.heterogeneous.PeanoNumber._

class TestSplitAt extends FlatSpec with Matchers {

  "HList splitAt" should "return (HNil, list) for list splitAt Zero" in {
    val list = 2 :: "3" :: 9 :: HNil
    list splitAt Zero shouldBe(HNil, 2 :: "3" :: 9 :: HNil)
  }

  "HList splitAt" should "work with empty list" in {
    val list = HNil
    list splitAt Zero shouldBe(HNil, HNil)
  }

  "HList splitAt" should "work 1" in {
    val list = 2 :: "3" :: 9 :: HNil
    list splitAt _1 shouldBe(2 :: HNil, "3" :: 9 :: HNil)
  }

  "HList splitAt" should "work 2" in {
    val list = 2 :: "3" :: 9 :: HNil
    list splitAt _2 shouldBe(2 :: "3" :: HNil, 9 :: HNil)
  }

  "HList splitAt" should "work 3" in {
    val list = 2 :: "3" :: 9 :: HNil
    list splitAt _3 shouldBe(2 :: "3" :: 9 :: HNil, HNil)
  }
}
