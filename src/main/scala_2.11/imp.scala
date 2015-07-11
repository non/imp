package object imp {

  import language.experimental.macros
  import scala.reflect.macros.whitebox.Context

  /**
   * Summon an implicit parameter with no overhead.
   *
   * One common use for this method is to acquire an implicit value
   * from the environment without having to reference it by name, or
   * call a method.
   *
   * For example, an implicit ClassTag[Int] value can be found in
   * `scala.reflect.ClassTag.Int`. If you were to say
   * `imp[ClassTag[Int]]` it would be as if you had referenced the
   * value directly by name.
   *
   * (This is in contrast to the `implicitly[]` method which involves
   * an extra call at runtime.)
   */
  def imp[Ev](implicit ev: Ev): Ev = macro summon[Ev]

  /**
   * Macro to obtain an implicit parameter directly.
   *
   * This macro is used by `imp[X]` to provide implicit `X` values
   * directly. It can also be used to implement the apply method on a
   * type class' companion, e.g.
   *
   * object Monoid {
   *   def apply[A: Monoid]: Monoid[A] = macro summon[Monoid[A]]
   * }
   */
  def summon[Ev](c: Context)(ev: c.Tree): c.Tree = ev
}
