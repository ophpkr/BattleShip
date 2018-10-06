package example
import game._
import helpers._

object Hello extends App {
  val g1 = GridOfShips("g1", 10, List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), GridHelper.addListOfDot(List(), 10))
  val g2 = g1.setHit("a5")
  println(g2.representation.map(x => x.map(y => y.toString)).mkString(""))
  println("------------------------------")
  println(g2.representation.toString) //.mkString(""))
  println("------------------------------")
  println((List(List(".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
    List(".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
    List(".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
    List(".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
    List("o", ".", ".", ".", ".", ".", ".", ".", ".", "."),
    List(".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
    List(".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
    List(".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
    List(".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
    List(".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
    List(".", ".", ".", ".", ".", ".", ".", ".", ".", "."))).toString.intersect(g2.representation.toString))
}