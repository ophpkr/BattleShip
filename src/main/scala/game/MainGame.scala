package game
import helpers._
import main.scala.elements.Battle

object MainGame {

  def main(args: Array[String]): Unit = {

    if (args.isEmpty) {

      println("------------ BATTLESHIP ------------")
      val p1 = GeneralHelper.askForName("p1")
      println("\u001b[2J")
      val p2 = GeneralHelper.askForMode
      println("\u001b[2J")
      val game = Battle(p1, p2)

      println(p1.name + " starts placing his/her ships : ")

      val newGame = GeneralHelper.putShips(game, p1, "player1")

      BattleHelper.startBattle(newGame)
    }
    else {
      /* proof */
      if (args(0) == "test") {
        GeneralHelper.createCSV()
        // ia1 vs ia2
        val pai1 = GeneralHelper.initializePlayer("UI beginner", 0, 1)
        val pai2 = GeneralHelper.initializePlayer("UI medium", 0, 2)
        val game1 = Battle(pai1, pai2)
        val game2 = GeneralHelper.putShips(game1, pai1, "player1")
        val game3 = GeneralHelper.putShips(game2, pai2, "player2")
        BattleHelper.startBattle(game3)
        BattleHelper.loop_(0)

        // ia2 vs ia3
        val pai1bis = GeneralHelper.initializePlayer("UI medium", 0, 2)
        val pai2bis = GeneralHelper.initializePlayer("UI hard", 0, 3)
        val game1bis = Battle(pai1bis, pai2bis)
        val game2bis = GeneralHelper.putShips(game1bis, pai1bis, "player1")
        val game3bis = GeneralHelper.putShips(game2bis, pai2bis, "player2")
        BattleHelper.startBattle(game3bis)
        BattleHelper.loop_(0)

        // ia1 vs ia3
        val pai1bisbis = GeneralHelper.initializePlayer("UI beginner", 0, 1)
        val pai2bisbis = GeneralHelper.initializePlayer("UI hard", 0, 3)
        val game1bisbis = Battle(pai1bisbis, pai2bisbis)
        val game2bisbis = GeneralHelper.putShips(game1bisbis, pai1bisbis, "player1")
        val game3bisbis = GeneralHelper.putShips(game2bisbis, pai2bisbis, "player2")
        BattleHelper.startBattle(game3bisbis)
        BattleHelper.loop_(0)
      }
      else {
        println("you will choose a confrontation between two AIs 100 times")
        println("Select a ai between ai1, ai2 and ai3")
        val p1 = GeneralHelper.askType
        println("\u001b[2J")
        println("Choose the AI oponent between ai1, ai2, ai3")
        val p2 = GeneralHelper.askType
        val game = Battle(p1, p2)
        val game1 = GeneralHelper.putShips(game, p1, "player1")
        val game2 = GeneralHelper.putShips(game, p2, "player2")
        BattleHelper.startBattle(game2)
      }
    }
  }
}

