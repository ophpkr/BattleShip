import org.scalatest._
import game.Ship

class ShipSpec extends FunSuite with DiagrammedAssertions {

  /* tests on decreaseSize() */
  test("decrease by one the size of b1") {
    var s1 = new Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    s1.decreaseSize
    assert(s1.size===3)
  }
  test("returns 0 for bad size") {
    var s1 = new Ship("s1", 0, Set())
    s1.decreaseSize
    assert(s1.size===0)
  }

  /* tests on removeSquare() */
  test("remove the a2 of the squares occupied by s1") {
    var s1 = new Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    s1.removeSquare("a2")
    assert(Array("a1","a3","a4").sameElements(s1.squaresTaken))
  }
  test("the size of the ship has dicreased by one after having removed a2") {
    var s1 = new Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    s1.removeSquare("a2")
    assert(s1.size===3)
  }
  test("remove a square not occupied by the ship doesn't change the ship's occupied squares") {
    var s1 = new Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    s1.removeSquare("a5")
    assert(Array("a1", "a2", "a3","a4").sameElements(s1.squaresTaken))
  }
  test("remove a square not occupied by the ship doesn't change the ship's size") {
    var s1 = new Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    s1.removeSquare("a5")
    assert(s1.size===3)
  }

  /* tests on isTakenByShip */
  test("assert a1 is taken by the boat s1") {
    var s1 = new Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    assert(s1.isTakenByShip("a1"))
  }
  test("assert a5 isn't taken by the boat s1") {
    var s1 = new Ship("s1", 4, Set("a1", "a2", "a3", "a4"))
    assert(!(s1.isTakenByShip("a5")))
  }
}