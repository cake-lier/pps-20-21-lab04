package u04lab.code

import u04lab.code.Lists.List.{append, nil, reverse}
import u04lab.code.Lists._
import u04lab.code.Optionals.Option
import u04lab.code.Optionals.Option.{None, Some}
import u04lab.code.Streams.Stream._
import u04lab.code.Streams._

import scala.util.Random

trait PowerIterator[A] {
  def next(): Option[A]
  def allSoFar(): List[A]
  def reversed(): PowerIterator[A]
}

trait PowerIteratorsFactory {
  def incremental(start: Int, successive: Int => Int): PowerIterator[Int]
  def fromList[A](list: List[A]): PowerIterator[A]
  def randomBooleans(size: Int): PowerIterator[Boolean]
}

class PowerIteratorsFactoryImpl extends PowerIteratorsFactory {
  private def toStream[A](l: List[A]): Stream[A] = l match {
    case List.Cons(h, t) => cons(h, toStream(t))
    case _ => empty()
  }

  private case class PowerIteratorImpl[A](private var s: Stream[A]) extends PowerIterator[A] {
    private var pastList: List[A] = nil

    override def next(): Option[A] = s match {
      case Stream.Cons(h, t) =>
        lazy val head = h()
        pastList = append(pastList, List.Cons(head, List.nil))
        s = t()
        Some(head)
      case _ => None()
    }

    override def allSoFar(): List[A] = pastList

    override def reversed(): PowerIterator[A] = PowerIteratorImpl(toStream(reverse(pastList)))
  }

  override def incremental(start: Int, successive: Int => Int): PowerIterator[Int] = PowerIteratorImpl(iterate(start)(successive))

  override def fromList[A](list: List[A]): PowerIterator[A] = PowerIteratorImpl(toStream(list))

  override def randomBooleans(size: Int): PowerIterator[Boolean] = PowerIteratorImpl(take(generate(Random.nextBoolean()))(size))
}
