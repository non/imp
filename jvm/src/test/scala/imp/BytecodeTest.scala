package imp

import org.scalatest._

class BytecodeTest extends PropSpec with Matchers {

  val b = Decompile.decompile("imp.Methods")

  property("Invocation on instance summoned with imp[A] is equivalent to direct invocation on an implicit parameter") {
    b("incr2") shouldBe b("incr1")
  }

  property("Invocation on instance created from an apply method implemented using macro summon, is equivalent to invocation on an implicit parameter") {
    b("incr3") shouldBe b("incr1")
  }

  property("Invocation on imp[A], implicit parameter or apply with macro summon is less bytecode than implicitly[A]") {
    b("incr4").length should be > b("incr1").length
  }

  property("Invocation on imp[A], implicit parameter, or apply with macro summon is less bytecode than normal apply pattern, even with @inline final") {
    b("incr5").length should be > b("incr1").length
  }

  property("Apply implemented with macro summon is less bytecode than Apply implemented with imp[A]") {
    b("incr6").length should be > b("incr3").length
  }

  property("Apply implemented with imp[A] generates the same amount of bytecode as normal apply pattern") {
    b("incr6").length shouldBe b("incr5").length
  }

  property("Summoning with imp[A] inside method generates the same bytecode as an explicit parameter") {
    b("withImp") shouldBe b("withExplicitParam")
  }

  property("Summoning with imp[A] generates less bytecode than summoning with implicitly[A]") {
    b("withImplicitly").length should be > b("withImp").length
  }

}
