package imp

import language.experimental.macros
import org.scalatest._

trait Incr[A] {
  def incr(a: A): A
}

object Incr {

  def apply[A: Incr]: Incr[A] = macro summon[Incr[A]]

  @inline final def apply2[A](implicit ev: Incr[A]): Incr[A] = ev

  @inline final def apply3[A: Incr]: Incr[A] = imp[Incr[A]]

  implicit object IntIncr extends Incr[Int] {
    def incr(n: Int): Int = n + 1
  }
}
