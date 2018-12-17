package ru.spb.hse.heterogeneous

import ru.spb.hse.heterogeneous.HList.{HCons, HNil}
import ru.spb.hse.heterogeneous.PeanoNumber.{S, Zero}

trait Splittable[L <: HList, N <: PeanoNumber, LResult <: HList, RResult <: HList] {
  def apply(list: L, idx: N): (LResult, RResult)
}

object Splittable {

  implicit def zeroSplittable[L <: HList]: Splittable[L, Zero.type, HNil.type, L] =
    (list: L, _: Zero.type) => (HNil, list)

  implicit def splittable[L <: HList, H, N <: PeanoNumber, LResult <: HList, RResult <: HList](implicit splittable: Splittable[L, N, LResult, RResult]): Splittable[HCons[H, L], S[N], HCons[H, LResult], RResult] =
    (list: HCons[H, L], idx: S[N]) => {
      val (lResult, rResult) = splittable(list.tail, idx.number)
      (HCons(list.head, lResult), rResult)
    }
}