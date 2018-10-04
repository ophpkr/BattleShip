package game
import scala.io.StdIn
import helpers._

object MainGame {
  //TODO: write functions of main here instead of general helper
  def main(args: Array[String]): Unit = {


    println("------------ BATTLESHIP ------------")
    val p1 = GeneralHelper.askForName("p1")
    val p2 = GeneralHelper.askForMode
    val game = Battle(p1, p2, false)

    println(p1.name + " starts placing his/her ships : ")

    val newGame = GeneralHelper.putShips(game, p1, "player1")
    println(newGame.player1.shipsGrid.toString)
    println(newGame.player2.shipsGrid.toString)

    BattleHelper.startBattle(newGame)
  }
}

