/*import org.scalatest._
import game.GridOfShip

class GridOfShipsSpec extends FunSuite with DiagrammedAssertions {

  /* test on setHit */
  test("the square a5 has been changed into a hit symbol (o)") {
    var g1 = new GridOfShips("g1", 10)
    g1.setHit("a5")
    assert(Array((".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      ("o", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", ".")).sameElements(g1.representation))
  }
  /* test on setSunk */
  test("the square a5 has been changed into a hit symbol (-)") {
    var g1 = new GridOfShips("g1", 10)
    g1.setSunk("a5")
    assert(Array((".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      ("-", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", ".")).sameElements(g1.representation))
  }
  /* test on setMiss */
  test("the square a5 has been changed into a hit symbol (x)") {
    var g1 = new GridOfShips("g1", 10)
    g1.setMiss("a5")
    assert(Array((".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      ("x", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", ".")).sameElements(g1.representation))
  }
  /* tests on canOccupySquares */
  test("give the possibility to place a ship on b2, b3, b4") {
    var g1 = new GridOfShips("g1", 10)
    assert(g1.canOccupySquares(Array("b2", "b3", "b4")))
  }
  test("give not the possibility to place a ship on b2, b3, b4 because occupied") {
    var g1 = new GridOfShips("g1", 10)
    g1.addShip(Array("b2", "b3", "b4"))
    assert(!(g1.canOccupySquares(Array("b2", "b3", "b4"))))
  }
  test("give not the possibility to place a ship on unexisting squares") {
    var g1 = new GridOfShips("g1", 10)
    assert(!(g1.canOccupySquares(Array("b9", "b10", "b11"))))
  }
  /* test on addShip */
  test("a ship has been put on c1,c2,c3") {
    var g1 = new GridOfShips("g1", 10)
    g1.addShip(Array("c1", "c2", "c3"))
    assert(Array((".", ".", "S", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", "S", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", "S", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", "."),
      (".", ".", ".", ".", ".", ".", ".", ".", ".", ".")).sameElements(g1.representation))
  }
  /* test on getStateAfterHit */
  test("Give correct states of squares") {
    var g1 = new GridOfShips("g1", 10)
    g1.addShip(Array("c1", "c2", "c3"))
    g1.setHit("b1")
    g1.setSunk("j3")
    g1.setMiss("d4")
    //when the ship has been hit but still the hit maden't it sunk
    assert((g1.getStateAfterHit("c1"))==="hit")
    //when the ship is sunk right after the hit
    g1.setHit("c2")
    assert((g1.getStateAfterHit("c3"))==="sunk")
    //when it is a miss
    assert((g1.getStateAfterHit("e3"))==="miss")
    assert((g1.getStateAfterHit("b1"))==="miss")
    assert((g1.getStateAfterHit("j3"))==="miss")
    assert((g1.getStateAfterHit("d4"))==="miss")
  }
}
*/