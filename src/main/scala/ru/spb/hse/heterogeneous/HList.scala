package ru.spb.hse.heterogeneous
package HList

sealed trait HList 

object HList {
  
  case class HCons[+H, +T <: HList](head: H, tail: T) extends HList

  case object HNil extends HList

  implicit class HListExt[R <: HList](list: R) {

    def ::[H](head: H) = HCons(head, list)
  }
}

