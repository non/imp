## Imp

> Imps have also been described as being *bound* or contained in some
> sort of object, such as a sword or crystal ball. In other cases,
> imps were simply kept in a certain object and summoned only when
> their masters had need of them. Some even had the ability to grant
> their owners wishes, much like a *genie*.
>
> -- Wikipedia entry for "Imp"

### Overview

This is a very small library that exists to scratch one itch.

It provides a zero-cost macro to summon implicit values.

One use of this is the `imp` method, which is similar to `implicitly`
(but without any of the latter's indirection). Previously, there were
cases where context-bound syntax [1] or other indirection was not
optimal due to the cost associated with `implicitly`.

There is a convention when creating type classes in Scala to provide
an `apply` method on the companion object, to access an implicit type
class instance. The `summon` macro can be used here to create a more
efficient form of this method.

([1] By context-bound, I mean declaring a type parameter as
`[A: Magma]`, which corresponds to `[A]` with an `(implicit ev:
Magma[A])` parameter also.)

### Getting Imp

Imp supports Scala 2.10 and 2.11 (as well as Scala.js). If you use
SBT, you can include Imp via the following `build.sbt` snippets:

```scala
libraryDependencies += "org.spire-math" %% "imp" % "0.2.1" % "provided"

// if you want to use the imp.summon macro you'll need this too:
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided"
```

These dependencies are both *compile-time only* -- there is no runtime
cost (in jars, size, execution time, etc) imposed by Imp.

### Examples

Here's an example which creates a reversed ordering instance:

```scala
import imp.imp

val reversed = imp[Ordering[Int]].reverse
// equivalent to: Ordering.Int.reverse
// better than: implicitly[Ordering[Int]].reverse
```

Here's a definition of the `Magma` type class which uses `imp.summon`:

```scala
import imp.summon
import language.experimental.macros

trait Magma[A] {
  def combine(x: A, y: A): A
}

object Magma  {
  def apply[A: Magma]: Magma[A] = macro summon[Magma[A]]
  // better than: def apply[A](implicit ev: Magma[A]): Magma[A] = ev
  // (even using @inline and final)

  implicit val IntMagma: Magma[Int] =
    new Magma[Int] {
      def combine(x: Int, y: Int): Int = x + y
    }
}

Magma[Int].combine(3, 4)
// equivalent to: Magma.IntMagma.combine(3, 4)
// also equivalent to: imp[Magma[Int]].combine(3, 4)
// better than: implicitly[Magma[Int]].combine(3, 4)
```

### Copyright and License

All code is available to you under the MIT license, available at
http://opensource.org/licenses/mit-license.php and also in the
[COPYING](COPYING) file.

Copyright Erik Osheim, 2015.
