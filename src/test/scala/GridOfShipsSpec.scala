import org.scalatest._
import helpers._
import main.scala.elements.{Grid, GridOfShips}

class GridOfShipsSpec extends FunSuite with DiagrammedAssertions {

  /* test on setHit (test the updateSquare function too) */
  test("the square a5 has been changed into a hit symbol (o)") {
    val g1 = GridOfShips("g1", 10, List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), GridHelper.addListOfDot(List(), 10))
    val g2 = g1.setHit("a5")
    assert(g2.representation.apply(4).apply(0) == "o")
  }
  /* test on setMiss (test the updateSquare function too) */
  test("the square a5 has been changed into a hit symbol (x)") {
    val g1 = GridOfShips("g1", 10, List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), GridHelper.addListOfDot(List(), 10))
    val g2 = g1.setMiss("a5")
    assert(g2.representation.apply(4).apply(0) == "x")
  }
  /* tests on canOccupySquares */
  test("give the possibility to place a ship on b2, b3, b4") {
    val g1 = GridOfShips("g1", 10, List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), GridHelper.addListOfDot(List(), 10))
    assert(g1.canOccupySquares(List("b2", "b3", "b4")))
  }
  test("give not the possibility to place a ship on b2, b3, b4 because occupied") {
    val g1 = GridOfShips("g1", 10, List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), GridHelper.addListOfDot(List(), 10))
    val g2 = g1.addShips(List("b2", "b3", "b4"), g1, 0)
    assert(!(g2.canOccupySquares(List("b2", "b3", "b4"))))
  }
  test("give not the possibility to place a ship on unexisting squares") {
    val g1 = GridOfShips("g1", 10, List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), GridHelper.addListOfDot(List(), 10))
    assert(!(g1.canOccupySquares(List("b9", "b10", "b11"))))
  }
  /* test on addShip */
  test("a ship has been put on c1,c2,c3") {
    val g1 = GridOfShips("g1", 10, List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), GridHelper.addListOfDot(List(), 10))
    val g2 = g1.addShips(List("c1", "c2", "c3"), g1, 0)
    assert(g2.representation.apply(0).apply(2) == "S" && g2.representation.apply(1).apply(2) == "S" && g2.representation.apply(2).apply(2) == "S")
  }
}
