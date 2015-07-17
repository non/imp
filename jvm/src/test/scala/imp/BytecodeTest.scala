package imp

import org.scalatest._

class BytecodeTest extends PropSpec with Matchers {

  val b = Decompile.decompile("imp.Methods")

  property("incr2 = incr1") {
    b("incr2") shouldBe b("incr1")
  }

  property("incr3 = incr1") {
    b("incr3") shouldBe b("incr1")
  }

  property("incr4 > incr1") {
    b("incr4").length should be > b("incr1").length
  }

  property("incr5 > incr1") {
    b("incr5").length should be > b("incr1").length
  }

  property("withImp = withExplicitParam") {
    b("withImp") shouldBe b("withExplicitParam")
  }

  property("withImplicitly > withImp") {
    b("withImplicitly").length should be > b("withImp").length
  }
}
