import org.scalatest._
import game._
import helpers._
import scala.util.Random
/*
class AI1Spec extends FunSuite with DiagrammedAssertions {

  /* test on addShip */
  test("add a ship to the AI1's ships") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI1("AI1", gs, ga, Set(), 0, r)
    val s = Ship("ship", 2, Set("a1", "a2"))
    val p2 = p1.addShip(s)
    assert(p2.ships.size === 1) // && (p1.ships.head).getClass == Ship)

  }
  /* test on increaseScore */
  test("increase by one the score of the AI1") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI1("AI1", gs, ga, Set(), 0, r)
    val p2 = p1.increaseScore
    assert(p2.score === 1)
  }
  /* tests on hasNoShips */
  test("assert there is no ship") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI1("AI1", gs, ga, Set(), 0, r)
    assert(p1.hasNoShip)
  }
  test("assert there is at least one ship") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI1("AI1", gs, ga, Set(), 0, r)
    val s = Ship("ship", 2, Set("a1", "a2"))
    val p2 = p1.addShip(s)
    assert(!p2.hasNoShip)
  }
  /* tests on updateShips */
  test("Select no ships when ships have a size of 0") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI1("AI1", gs, ga, Set(), 0, r)
    val s1 = Ship("ship", 0, Set())
    val s2 = Ship("ship", 0, Set())
    val p2 = p1.addShip(s1)
    val p3 = p2.addShip(s2)
    assert(p3.updateShips === Set())
  }
  test("Select a ship when a ship is still 'alive' ") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI1("AI1", gs, ga, Set(), 0, r)
    val s1 = Ship("ship", 0, Set())
    val s2 = Ship("ship", 2, Set("a1", "a2"))
    val p2 = p1.addShip(s1)
    val p3 = p2.addShip(s2)
    assert(p3.updateShips === Set(s2))
  }

}*/