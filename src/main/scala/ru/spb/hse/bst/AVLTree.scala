package ru.spb.hse.bst

import java.util.NoSuchElementException

import scala.annotation.tailrec

sealed trait AVLTree[+T] {

  def balance: Int

  def size: Int

  def depth: Int
}

private case class Node[T](data: T, left: AVLTree[T], right: AVLTree[T]) extends AVLTree[T] {
  override def balance: Int = right.depth - left.depth

  override def size: Int = left.size + right.size + 1

  override def depth: Int = math.max(left.depth, right.depth) + 1

  val prev: AVLTree[T] = Leaf
  val next: AVLTree[T] = Leaf
}

private case object Leaf extends AVLTree[Nothing] {
  override def balance: Int = 0

  override def size: Int = 0

  override def depth: Int = -1
}

/**
  * AVLTree implementation.
  */
private[bst] object AVLTree {

  def insert[T](value: T, tree: AVLTree[T])(implicit ordering: Ordering[T]): AVLTree[T] = tree match {
    case Leaf => Node(value, Leaf, Leaf)
    case node@Node(data, left, right) => ordering.compare(value, data) match {
      case 0 => node
      case -1 => rebalance(Node(data, insert(value, left), right))
      case 1 => rebalance(Node(data, left, insert(value, right)))
    }
  }

  @tailrec
  def contains[T](value: T, tree: AVLTree[T])(implicit ordering: Ordering[T]): Boolean = tree match {
    case Leaf => false
    case Node(data, left, right) => ordering.compare(value, data) match {
      case 0 => true
      case -1 => contains(value, left)
      case 1 => contains(value, right)
    }
  }

  def copy[T](tree: AVLTree[T]): AVLTree[T] = tree match {
    case Leaf => Leaf
    case Node(data, left, right) => Node(data, copy(left), copy(right))
  }

  def remove[T](value: T, tree: AVLTree[T])(implicit ordering: Ordering[T]): AVLTree[T] = tree match {
    case Leaf => throw new NoSuchElementException()
    case Node(data, Leaf, Leaf) =>
      if (ordering.compare(value, data) == 0)
        Leaf
      else
        throw new NoSuchElementException()
    case Node(data, left, right) => ordering.compare(value, data) match {
      case 0 => (left, right) match {
        case (_, right@Node(_, _, _)) =>
          val (min, newRight) = removeMin(right)
          rebalance(Node(min, left, newRight))
        case (left@Node(_, _, _), _) =>
          val (max, newLeft) = removeMax(left)
          rebalance(Node(max, newLeft, right))
        case _ => throw new IllegalStateException()
      }
      case -1 => rebalance(Node(data, remove(value, left), right))
      case 1 => rebalance(Node(data, left, remove(value, right)))
      case _ => throw new IllegalStateException()
    }
  }

  def removeMin[T](tree: Node[T]): (T, AVLTree[T]) = tree match {
    case Node(data, Leaf, Leaf) => (data, Leaf)
    case Node(data, Leaf, right) => (data, right)
    case Node(data, left@Node(_, _, _), right) =>
      val (min, newLeft) = removeMin(left)
      (min, rebalance(Node(data, newLeft, right)))
  }

  def removeMax[T](tree: Node[T]): (T, AVLTree[T]) = tree match {
    case Node(data, Leaf, Leaf) => (data, Leaf)
    case Node(data, left, Leaf) => (data, left)
    case Node(data, left, right@Node(_, _, _)) =>
      val (min, newRight) = removeMax(right)
      (min, rebalance(Node(data, left, newRight)))
  }

  private def rebalance[T](tree: AVLTree[T]): AVLTree[T] = (tree, tree.balance) match {
    case (node@Node(_, left, _), -2) => left.balance match {
      case 1 => AVLTree.bigRightRotation(node)
      case _ => smallRightRotation(node)
    }
    case (node@Node(_, _, right), 2) => right.balance match {
      case -1 => bigLeftRotation(node)
      case _ => smallLeftRotation(node)
    }
    case _ => tree
  }

  private def smallLeftRotation[T](tree: Node[T]): AVLTree[T] = tree.right match {
    case Node(data, left, right) => Node(data, Node(tree.data, tree.left, left), right)
    case _ => tree
  }

  private def smallRightRotation[T](tree: Node[T]): AVLTree[T] = tree.left match {
    case Node(data, left, right) => Node(data, left, Node(tree.data, right, tree.right))
    case _ => tree
  }

  private def bigLeftRotation[T](tree: Node[T]): AVLTree[T] = tree.right match {
    case node@Node(_, _, _) => smallLeftRotation(Node(tree.data, tree.left, smallRightRotation(node)))
    case _ => tree
  }

  private def bigRightRotation[T](tree: Node[T]): AVLTree[T] = tree.left match {
    case node@Node(_, _, _) => smallRightRotation(Node(tree.data, smallLeftRotation(node), tree.right))
    case _ => tree
  }
}
