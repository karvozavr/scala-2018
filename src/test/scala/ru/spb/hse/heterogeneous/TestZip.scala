package ru.spb.hse.heterogeneous

import org.scalatest._
import ru.spb.hse.heterogeneous.HList._

class TestZip extends FlatSpec with Matchers {

  "HList zip" should "work with empty lists" in {
    val l = HNil
    val r = HNil
    l zip r shouldBe HNil
    r zip l shouldBe HNil
  }

  "HList splitAt" should "work with one empty list" in {
    val l = HNil
    val r = "2" :: "3" :: "9" :: HNil
    l zip r shouldBe HNil
    r zip l shouldBe HNil
  }

  "HList splitAt" should "work with lists of different size" in {
    val l = 2 :: 3 :: HNil
    val r = "2" :: "3" :: "9" :: HNil
    l zip r shouldBe (2, "2") :: (3, "3") :: HNil
    r zip l shouldBe ("2", 2) :: ("3", 3) :: HNil
  }

  "HList splitAt" should "work with lists of equal size" in {
    val l = 2 :: 3 :: 9 :: HNil
    val r = "2" :: "3" :: "9" :: HNil
    l zip r shouldBe (2, "2") :: (3, "3") :: (9, "9") :: HNil
    r zip l shouldBe ("2", 2) :: ("3", 3) :: ("9", 9) :: HNil
  }
}
