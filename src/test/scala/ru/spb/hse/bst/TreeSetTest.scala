package ru.spb.hse.bst

import org.scalatest._

class TreeSetTest extends FlatSpec with Matchers {

  def testSetEquals[T](set: TreeSet[T], iterable: Iterable[T]): Unit = {
    val it1 = set.iterator
    val it2 = iterable.iterator
    set should have size iterable.size
    while (it1.hasNext && it2.hasNext) {
      it1.next shouldBe it2.next
    }
  }

  "TreeSet iterator" should " iterate over elements ordered way" in {
    val set = TreeSet.of(5, 1, 2, 4, 3, 7, 6)
    val iter = set.iterator
    var x = 1
    while (iter.hasNext) {
      iter.next() shouldBe x
      x += 1
    }
    x shouldBe 8
  }

  "TreeSet" should "have correct default values if empty" in {
    val set = TreeSet[Int]()
    set should have size 0
    set.isEmpty shouldBe true
    set.isNotEmpty shouldBe false
    val iter = set.iterator
    iter.hasNext shouldBe false
  }

  it should "calculate size correctly" in {
    val set = TreeSet.of(5, 1, 2, 4, 3, 7, 6)
    set should have size 7
  }

  it should "create intersection of sets and stay immutable" in {
    val set1 = TreeSet.of(1, 2, 3)
    val set2 = TreeSet.of(2, 3, 9)
    val set3 = set1 & set2
    testSetEquals(set3, List(2, 3))
    testSetEquals(set1, List(1, 2, 3))
    testSetEquals(set2, List(2, 3, 9))
  }

  it should "create union of sets and stay immutable" in {
    val set1 = TreeSet.of(1, 2, 3)
    val set2 = TreeSet.of(2, 3, 9)
    val set3 = set1 | set2
    testSetEquals(set3, List(1, 2, 3, 9))
    testSetEquals(set1, List(1, 2, 3))
    testSetEquals(set2, List(2, 3, 9))
  }

  it should "create xor of sets and stay immutable" in {
    val set1 = TreeSet.of(1, 2, 3)
    val set2 = TreeSet.of(2, 3, 9)
    val set3 = set1 ^ set2
    testSetEquals(set3, List(1, 9))
    testSetEquals(set1, List(1, 2, 3))
    testSetEquals(set2, List(2, 3, 9))
  }

  it should "add value and stay immutable" in {
    val set1 = TreeSet.of(1, 2, 3)
    val set2 = set1 + 4
    testSetEquals(set1, List(1, 2, 3))
    testSetEquals(set2, List(1, 2, 3, 4))
  }

  it should "remove value and stay immutable" in {
    val set1 = TreeSet.of(1, 2, 3)
    val set2 = set1 - 2
    testSetEquals(set1, List(1, 2, 3))
    testSetEquals(set2, List(1, 3))
  }

  "TreeSet filter" should "be correct and leave tree unchanged" in {
    val set1 = TreeSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val set2 = set1.filter(_ % 2 == 1)
    testSetEquals(set1, List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    testSetEquals(set2, List(1, 3, 5, 7, 9))
  }

  "TreeSet map" should "be correct, save order and leave tree unchanged" in {
    val set1 = TreeSet.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val set2 = set1.map(20 - _)
    testSetEquals(set1, List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    testSetEquals(set2, List(10, 11, 12, 13, 14, 15, 16, 17, 18, 19))
  }

  "TreeSet contains" should "be correct" in {
    val set = TreeSet.of(1, 3, 5, 7, 9)
    for (i <- 1 until 10) {
      set(i) shouldBe (i % 2 == 1)
    }
  }

  "TreeSet withFilter" should "work fine" in {
    val set1 = TreeSet.of(1, 2, 3, 4, 5, 6)
    val filter = set1
      .withFilter(_ != 1)
      .withFilter(_ != 2)
      .withFilter(_ != 3)

    val set2 = filter.map(_ * 2)
    testSetEquals(set1, List(1, 2, 3, 4, 5, 6))
    testSetEquals(set2, List(8, 10, 12))
  }
}
