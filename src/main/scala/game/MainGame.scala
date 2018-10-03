package game
import scala.io.StdIn
import helpers._

object MainGame {
  def main(args: Array[String]): Unit = {

    println("------------ BATTLESHIP ------------")
    val p1 = GeneralHelper.askForName("p1")
    val p2 = GeneralHelper.askForMode
    val game = Battle(p1, p2, false)

    println(p1.name + " starts placing his/her ships : ")
  }
}

