package ru.spb.hse.heterogeneous

sealed trait PeanoNumber

object PeanoNumber {

  case object Zero extends PeanoNumber

  case class S[T <: PeanoNumber](number: T) extends PeanoNumber

  def _1 = S(Zero)

  def _2 = S(_1)

  def _3 = S(_2)

  def _4 = S(_3)

  def _5 = S(_4)

  def _6 = S(_5)

  def _7 = S(_6)

  def _8 = S(_7)

  def _9 = S(_8)

  def _10 = S(_9)

}