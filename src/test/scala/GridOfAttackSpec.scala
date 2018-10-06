import org.scalatest._
import game.GridOfAttack
import game.Grid
import helpers._

class GridOfAttackSpec extends FunSuite with DiagrammedAssertions {

  /* test on setHit (test the updateSquare function too) */
  test("the square a5 has been changed into a hit symbol (o)") {
    val g1 = GridOfAttack("g1", 10, List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), GridHelper.addListOfDot(List(), 10))
    val g2 = g1.setHit("a5")
    assert(g2.representation.apply(4).apply(0) == "o")
  }
  /* test on setMiss (test the updateSquare function too) */
  test("the square a5 has been changed into a hit symbol (x)") {
    val g1 = GridOfAttack("g1", 10, List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), GridHelper.addListOfDot(List(), 10))
    val g2 = g1.setMiss("a5")
    assert(g2.representation.apply(4).apply(0) == "x")
  }
}