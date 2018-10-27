package ru.spb.hse.calculator

import org.junit.Assert.assertEquals
import org.junit.Test

class CalculatorTest {

  private def testHelper(input: String, result: Int): Unit =
    assertEquals(result, Calculator.eval(input))

  @Test
  def test1(): Unit = testHelper("2 + 2", 4)

  @Test
  def test2(): Unit = testHelper("2 - 2", 0)

  @Test
  def test3(): Unit = testHelper("15", 15)

  @Test
  def test4(): Unit = testHelper("-156", -156)

  @Test
  def test5(): Unit = testHelper("2 + 8 * (440 - 33) / 5", 653)

  @Test
  def test6(): Unit = testHelper("9 % 3", 0)

  @Test
  def test7(): Unit = testHelper("41 % 3", 2)

  @Test
  def test8(): Unit = testHelper("41 % 3", 2)

  @Test
  def test9(): Unit = testHelper("1 == 0", 0)

  @Test
  def test10(): Unit = testHelper("11 == 11", 1)

  @Test
  def test11(): Unit = testHelper("(5 > 2) || (1 < -100)", 1)
}