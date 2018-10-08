import org.scalatest._
import game.Ship
/*
class ShipSpec extends FunSuite with DiagrammedAssertions {

  /* tests on decreaseSize() */
  test("decrease by one the size of s1") {
    val s1 = Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    val s2 = s1.decreaseSize
    assert(s2.size===3)
  }
  test("returns 0 for bad size") {
    val s1 = Ship("s1", -1, Set())
    val s2 = s1.decreaseSize
    assert(s2.size===0)
  }

  /* tests on removeSquare() */
  test("remove a2 from the squares occupied by s1") {
    val s1 = Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    val s2 = s1.removeSquare("a2")
    assert(List("a1","a3","a4").sameElements(s2.squaresTaken))
  }
  test("the size of the ship has dicreased by one after having removed a2") {
    val s1 = Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    val s2 = s1.removeSquare("a2")
    assert(s2.size===3)
  }
  test("remove a square not occupied by the ship doesn't change the ship's occupied squares") {
    val s1 = Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    val s2 = s1.removeSquare("a5")
    assert(Array("a1", "a2", "a3","a4").sameElements(s2.squaresTaken))
  }
  test("remove a square not occupied by the ship doesn't change the ship's size") {
    val s1 = Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    val s2 = s1.removeSquare("a5")
    assert(s2.size===4)
  }

  /* tests on isTakenByShip */
  test("assert a1 is taken by the boat s1") {
    val s1 = Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    assert(s1.isTakenByShip("a1"))
  }
  test("assert a5 isn't taken by the boat s1") {
    val s1 = Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    assert(!(s1.isTakenByShip("a5")))
  }
}*/