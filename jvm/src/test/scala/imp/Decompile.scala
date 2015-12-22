package imp

import scala.collection.mutable.ArrayBuffer
import scala.sys.process._

trait DecompileBase {

  val Prototype = """^  public .+ ([^ ]+)\(.+\);$""".r
  val Line = """^    ( *[0-9]+.+)$""".r
  val Empty = """^ *$""".r

  def ClassPath: String

  def decompile(cls: String, cp: String = ClassPath): Map[String, String] = {
    val output = Seq("javap", "-c", "-classpath", cp, "imp.Methods").!!

    var curr = ""
    var lines = Vector.empty[String]
    var bytecode = Map.empty[String, String]

    output.split('\n').foreach {
      case Prototype(name) =>
        curr = name
      case Empty() =>
        bytecode = bytecode.updated(curr, lines.mkString("", "\n", "\n"))
        curr = ""
        lines = Vector.empty[String]
      case Line(line) =>
        lines = lines :+ line
      case _ =>
        ()
    }

    bytecode
  }
}
