package game
import helpers._
import sun.java2d.loops.DrawGlyphListAA.General

import scala.io.StdIn

object MainGame {

  def main(args: Array[String]): Unit = {

    if (args.isEmpty) {

      println("------------ BATTLESHIP ------------")
      val p1 = GeneralHelper.askForName("p1")
      val p2 = GeneralHelper.askForMode
      val game = Battle(p1, p2)

      println(p1.name + " starts placing his/her ships : ")

      val newGame = GeneralHelper.putShips(game, p1, "player1")

      BattleHelper.startBattle(newGame)
    }
    else {
      println("you will choose a confrontation between two AIs 100 times")
      println("Select a ai between ai1, ai2 and ai3")
      val p1 = GeneralHelper.askType
      println("Choose the AI oponent between ai1, ai2, ai3")
      val p2 = GeneralHelper.askType
      val game = Battle(p1, p2)
      val game1 = GeneralHelper.putShips(game, p1, "player1")
      val game2 = GeneralHelper.putShips(game, p2, "player2")
      BattleHelper.startBattle(game2)
    }
  }
}

