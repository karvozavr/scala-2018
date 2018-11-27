package ru.spb.hse.bst

sealed abstract class MyList[+T] {
  def isEmpty: Boolean

  def isNotEmpty: Boolean = !isEmpty

  def tail: MyList[T]

  def head: T

  def ::[U >: T](value: U): MyList[U] = MyListNode(value, this)
}

case object MyNil extends MyList[Nothing] {
  override def isEmpty: Boolean = true

  override def tail = throw new NoSuchElementException()

  override def head = throw new NoSuchElementException()
}

case class MyListNode[T](private val _head: T, private val _tail: MyList[T]) extends MyList[T] {
  override def isEmpty: Boolean = false

  override def tail: MyList[T] = _tail

  override def head: T = _head
}