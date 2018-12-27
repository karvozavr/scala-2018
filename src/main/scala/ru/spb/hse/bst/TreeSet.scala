package ru.spb.hse.bst

/**
  * Immutable TreeSet
  *
  * @param tree AVLTree object - inner implementation
  * @tparam T type of stored values
  */
class TreeSet[T] private(private var tree: AVLTree[T] = Leaf)(implicit val ordering: Ordering[T]) {

  def contains(elem: T): Boolean = AVLTree.contains(elem, tree)(ordering)

  def apply(elem: T): Boolean = contains(elem)

  def add(elem: T): TreeSet[T] = this + elem

  def +(elem: T): TreeSet[T] = {
    val set = new TreeSet[T](AVLTree.copy(tree))
    set += elem
    set
  }

  def remove(elem: T): TreeSet[T] = this - elem

  def -(elem: T): TreeSet[T] = {
    val set = new TreeSet[T](AVLTree.copy(tree))
    set -= elem
    set
  }

  def &(other: TreeSet[T]): TreeSet[T] = filter(other.contains)

  def |(other: TreeSet[T]): TreeSet[T] = this ++ other

  def ^(other: TreeSet[T]): TreeSet[T] = (this | other) -- (this & other)

  private def +=(elem: T): Boolean = {
    if (!contains(elem)) {
      tree = AVLTree.insert(elem, tree)(ordering)
      _size += 1
      return true
    }
    false
  }

  private def -=(elem: T): Boolean = {
    if (contains(elem)) {
      tree = AVLTree.remove(elem, tree)(ordering)
      _size -= 1
      return true
    }
    false
  }

  def copy(): TreeSet[T] =
    new TreeSet[T](AVLTree.copy(tree))

  def ++[U <: T](that: TreeSet[U]): TreeSet[T] = {
    val result = copy()
    for (y <- that)
      result += y
    result
  }

  def --[U <: T](that: TreeSet[U]): TreeSet[T] = {
    val result = copy()
    for (x <- that)
      result -= x
    result
  }

  private var _size: Int = tree.size

  def size: Int = _size

  def isEmpty: Boolean = tree == Leaf

  def isNotEmpty: Boolean = !isEmpty

  def iterator: MyIterator[T] = new TreeSetIterator(tree)

  def foreach[U](f: T => U): Unit = {
    val iter = iterator
    while (iter.hasNext) {
      f(iter.next())
    }
  }

  def map[U](f: T => U)(implicit ordering: Ordering[U]): TreeSet[U] = {
    val result = TreeSet[U]()
    val iter = iterator
    while (iter.hasNext) {
      result += f(iter.next())
    }
    result
  }

  def flatMap[U](f: T => TreeSet[U])(implicit ordering: Ordering[U]): TreeSet[U] = {
    val result = TreeSet[U]()
    for (x <- this)
      for (y <- f(x))
        result += y
    result
  }

  def filter(p: T => Boolean): TreeSet[T] = {
    val result = new TreeSet[T]()
    for (x <- this)
      if (p(x)) result += x
    result
  }

  def withFilter(p: T => Boolean): WithFilter = new WithFilter(p)

  class WithFilter(private val p: T => Boolean) {
    def map[U](f: T => U)(implicit ordering: Ordering[U]): TreeSet[U] = {
      val result = TreeSet[U]()
      for (x <- TreeSet.this)
        if (p(x))
          result += f(x)
      result
    }

    def flatMap[U](f: T => TreeSet[U])(implicit ordering: Ordering[U]): TreeSet[U] = {
      val result = TreeSet[U]()
      for (x <- TreeSet.this)
        if (p(x))
          for (y <- f(x))
            result += y
      result
    }

    def foreach[U](f: T => U): Unit = {
      for (x <- TreeSet.this)
        if (p(x))
          f(x)
    }

    def withFilter(q: T => Boolean): WithFilter = new WithFilter(x => p(x) && q(x))
  }

  /**
    * TreeSet iterator
    *
    * @param tree AVL tree storage of TreeSet
    */
  class TreeSetIterator(private var tree: AVLTree[T]) extends MyIterator[T] {
    private var descendStack: MyList[AVLTree[T]] = MyNil

    {
      descendToLeftmost(tree)
    }

    private def descendToLeftmost(tree: AVLTree[T]): Unit = {
      var node = tree
      while (node != Leaf) {
        descendStack = node :: descendStack
        val Node(_, left, _) = node
        node = left
      }
    }

    override def next(): T = {
      val node = descendStack.head
      descendStack = descendStack.tail
      val Node(value, _, right) = node
      descendToLeftmost(right)
      value
    }

    override def hasNext: Boolean = descendStack.nonEmpty
  }

}

object TreeSet {
  def apply[T]()(implicit ordering: Ordering[T]): TreeSet[T] = new TreeSet[T](Leaf)

  def apply[T](elems: T*)(implicit ordering: Ordering[T]): TreeSet[T] = {
    var set = new TreeSet[T]()
    elems.foreach(elem => set += elem)
    set
  }
}
