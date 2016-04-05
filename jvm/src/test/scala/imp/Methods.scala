package imp

class Methods {

  def withImplicitly[A: Ordering]: String =
    implicitly[Ordering[A]].getClass.getName

  def withExplicitParam[A](implicit ev: Ordering[A]): String =
    ev.getClass.getName

  def withImp[A: Ordering]: String =
    imp[Ordering[A]].getClass.getName

  def incr1[A](a: A)(implicit ev: Incr[A]): A = ev.incr(a)
  def incr2[A: Incr](a: A): A = imp[Incr[A]].incr(a)
  def incr3[A: Incr](a: A): A = Incr[A].incr(a)

  // these three have extra bytecode compared to the first 3
  def incr4[A: Incr](a: A): A = implicitly[Incr[A]].incr(a)
  def incr5[A: Incr](a: A): A = Incr.apply2[A].incr(a)
  def incr6[A: Incr](a: A): A = Incr.apply3[A].incr(a)
}
