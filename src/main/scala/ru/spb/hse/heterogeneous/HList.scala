package ru.spb.hse.heterogeneous

sealed trait HList

object HList {

  case class HCons[+H, +T <: HList](head: H, tail: T) extends HList

  case object HNil extends HList

  implicit class HListExt[R <: HList](private val list: R) extends AnyVal {

    def ::[H](head: H) = HCons(head, list)

    def :::[L <: HList, Result <: HList](left: L)
                                        (implicit appendable: Appendable[L, R, Result]): Result =
      appendable(left, list)

    def zip[RType <: HList, Result <: HList](right: RType)
                                            (implicit zippable: Zippable[R, RType, Result]): Result =
      zippable(list, right)

    def splitAt[N <: PeanoNumber, LResult <: HList, RResult <: HList](idx: N)
                                                                     (implicit splittable: Splittable[R, N, LResult, RResult]): (LResult, RResult) =
      splittable(list, idx)
  }

}
