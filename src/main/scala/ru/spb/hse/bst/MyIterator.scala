package ru.spb.hse.bst

trait MyIterator[T] {
  def next(): T

  def hasNext: Boolean

  def foreach(f: T => Unit): Unit = {
    while (hasNext) {
      f(next())
    }
  }
}
