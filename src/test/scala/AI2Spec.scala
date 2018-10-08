import org.scalatest._
import game._
import helpers._
import scala.util.Random

class AI2Spec extends FunSuite with DiagrammedAssertions {

  /* Tests on functions overrided are not tested because they are implemented
   like in the AI1.scala file and already tested in AI1Spec.scala
   Tests on functions copy***(x) are not made because they are implemented
   with the already existing function copy
   */

  /* test on addPos */
  test("add a pos to the AI2's already hit squares") {
    val gs = GridOfShips("gridOfShips", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val ga = GridOfAttack("gridOfAttack ", 10, _representation = GridHelper.addListOfDot(List(), 10))
    val r = Random
    val p1 = AI2("AI1", gs, ga, Set(), 0, r, Set())
    val p2 = p1.addPos("a1")
    assert(p2.asInstanceOf[AI2].alreadyHitSquares.apply("a1"))
  }
}