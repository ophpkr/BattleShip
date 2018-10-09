import org.scalatest._
import main.scala.elements._
import main.scala.players._
import helpers._
import main.scala.elements.{GridOfAttack, GridOfShips}
import main.scala.players.AI3

import scala.util.Random

class AI3Spec extends FunSuite with DiagrammedAssertions {

  /* Tests on functions overrided are not tested because they are implemented
   like in the AI1.scala file and already tested in AI1Spec.scala
   Tests on functions copy***(x) are not made because they are implemented
   with the already existing function copy
   */

  /* test on addPos */
  test("add a pos to the AI3's already hit squares") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI3("AI1", gs, ga, Set(), 0, r, Map("a1"->"0", "a2"->"0", "b1"->"0", "b2"->"0"), "", Set())
    val p2 = p1.addPos("a1")
    assert(p2.asInstanceOf[AI3].alreadyHitSquares.apply("a1"))
  }
  /* test on manage miss */
  test("after a miss the square value is -1") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI3("AI1", gs, ga, Set(), 0, r, Map("a1"->"0", "a2"->"0", "a3"->"0", "b1"->"0", "b2"->"0", "b3"->"0", "c1"->"0", "c2"->"0", "c3"->"0"), "", Set())
    val p2 = p1.asInstanceOf[AI3].manageMiss("a1")
    assert(p2.asInstanceOf[AI3].positionToMentalMap.apply("a1") === "-1")
  }
  /* tests on manage hit */
  test("after a hit the square value is -2") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI3("AI1", gs, ga, Set(), 0, r, Map("a1"->"0", "a2"->"0", "a3"->"0", "b1"->"0", "b2"->"0", "b3"->"0", "c1"->"0", "c2"->"0", "c3"->"0"), "", Set())
    val p2 = p1.asInstanceOf[AI3].manageHit("a1")
    assert(p2.asInstanceOf[AI3].positionToMentalMap.apply("a1") === "-2")
  }
  test("after a hit, if the square's initial value is 0, squares around values are 2") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI3("AI1", gs, ga, Set(), 0, r, Map("a1"->"0", "a2"->"0", "a3"->"0", "b1"->"0", "b2"->"0", "b3"->"0", "c1"->"0", "c2"->"0", "c3"->"0"), "", Set())
    val p2 = p1.asInstanceOf[AI3].manageHit("b2")
    assert(p2.asInstanceOf[AI3].positionToMentalMap.apply("b1") === "2" && p2.asInstanceOf[AI3].positionToMentalMap.apply("a2") === "2" && p2.asInstanceOf[AI3].positionToMentalMap.apply("b3") === "2" && p2.asInstanceOf[AI3].positionToMentalMap.apply("c2") === "2")
  }
  test("after a hit, if the square's initial value is 2, the square in the same alignment than the square-curFirstSquare, 'extremity' squares are at 3") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI3("AI1", gs, ga, Set(), 0, r, Map("a1"->"0", "a2"->"-2", "a3"->"2", "a4"->"0", "b1"->"0", "b2"->"0", "b3"->"0", "b4"->"0", "c1"->"0", "c2"->"0", "c3"->"0", "c4"->"0"), "a2", Set())
    val p2 = p1.asInstanceOf[AI3].manageHit("a3")
    println("heeeeeeyyyyyyyy  " + p2.asInstanceOf[AI3].positionToMentalMap.apply("a1"))
    assert(p2.asInstanceOf[AI3].positionToMentalMap.apply("a1") === "3" && p2.asInstanceOf[AI3].positionToMentalMap.apply("a4") === "3")
  }
  test("after a hit, if the square's initial value is 2, the squares perpendicular to the square-firstcurrsquare direction are at 1") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI3("AI1", gs, ga, Set(), 0, r, Map("a1"->"0", "a2"->"0", "a3"->"2", "a4"->"0","b1"->"0", "b2"->"2", "b3"->"0", "b4"->"0", "c1"->"0", "c2"->"0", "c3"->"0", "c4"->"0"), "a2", Set())
    val p2 = p1.asInstanceOf[AI3].manageHit("b2")
    assert(p2.asInstanceOf[AI3].positionToMentalMap.apply("b1") === "1" && p2.asInstanceOf[AI3].positionToMentalMap.apply("b3") === "1")
  }
  test("after a hit, if the square's initial value is 3, the square in the continuation in alignment square-curFirstSquare has the value 3") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI3("AI1", gs, ga, Set(), 0, r, Map("a1"->"0", "a2"->"-2", "a3"->"3", "a4"->"0","b1"->"0", "b2"->"0", "b3"->"0", "b4"->"0", "c1"->"0", "c2"->"0", "c3"->"0", "c4"->"0"), "a2", Set())
    val p2 = p1.asInstanceOf[AI3].manageHit("a3")
    assert(p2.asInstanceOf[AI3].positionToMentalMap.apply("a4") === "3" && p2.asInstanceOf[AI3].positionToMentalMap.apply("a1") === "0")
  }
  /* tests on manage sunk */
  test("after a sunk, all the squares supposed to be sunk have a value of -3 and extremities a value of -4") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI3("AI1", gs, ga, Set(), 0, r, Map("a1"->"0", "a2"->"-2", "a3"->"-2", "a4"->"-2","a5"->"-2", "a6"->"0", "a7"->"0", "a8"->"0", "a9"->"0", "a10"->"0", "b1"->"0", "b2"->"0", "b3"->"0", "b4"->"0","b5"->"0", "b6"->"0", "b7"->"0", "b8"->"0", "b9"->"0", "b10"->"0"), "a2", Set())
    val p2 = p1.asInstanceOf[AI3].manageSunk("a2")
    assert(p2.asInstanceOf[AI3].positionToMentalMap.apply("a2") === "-3" && p2.asInstanceOf[AI3].positionToMentalMap.apply("a3") === "-4" && p2.asInstanceOf[AI3].positionToMentalMap.apply("a4") === "-4" && p2.asInstanceOf[AI3].positionToMentalMap.apply("a5") === "-3")
  }

}