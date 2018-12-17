package ru.spb.hse.heterogeneous

import ru.spb.hse.heterogeneous.HList.{HCons, HNil}

trait Zippable[L <: HList, R <: HList, Result <: HList] {
  def apply(left: L, right: R): Result
}

object Zippable {

  implicit def rNilZippable[L <: HList]: Zippable[L, HNil.type, HNil.type] =
    (_: L, _: HNil.type) => HNil

  implicit def lNilZippable[R <: HCons[_, _]]: Zippable[HNil.type, R, HNil.type] =
    (_: HNil.type, _: R) => HNil

  implicit def zippable[L <: HList, LH, R <: HList, RH, Result <: HList](implicit zippable: Zippable[L, R, Result]): Zippable[HCons[LH, L], HCons[RH, R], HCons[(LH, RH), Result]] =
    (left: HCons[LH, L], right: HCons[RH, R]) => HCons((left.head, right.head), zippable(left.tail, right.tail))
}
