package helpers
import game._

import scala.io.StdIn

object BattleHelper {

  def startBattle(game: Battle): Unit = {
    val newPlayers = attackSquare(game.player1, game.player2)
    val newBattle = game.copy(_player1 = newPlayers.apply(0), _player2 = newPlayers.apply(1))
    if (newBattle.player2.hasNoShip) {
      println(newBattle.player1.name + " won ! ")
      val p1 = newBattle.player1.increaseScore
      val ng = newBattle.copy(_player1 = p1)
      displayScores(ng)
      manageRestart(ng)
    }
    else {
      val newPlayers2 = attackSquare(newBattle.player2, newBattle.player1)
      val newBattle2 = newBattle.copy(_player1 = newPlayers2.apply(1), _player2 = newPlayers2.apply(0))
      if (newBattle2.player1.hasNoShip) {
        println(newBattle2.player2.name + " won !")
        val p2 = newBattle.player2.increaseScore
        val ng = newBattle.copy(_player2 = p2)
        displayScores(ng)
        manageRestart(ng)
      }
      else {
        startBattle(newBattle2)
      }
      //TODO: ajouter la verification de fin de jeu
    }
  }

  def attackSquare(playerAttacking: Player, playerDefending: Player): List[Player] = {
    //TODO: Ajouter le cas coulé
    println(playerAttacking.name + " has to enter a position (ex: a1")
    val pos = StdIn.readLine()
    if(!validSquare(pos)) {
      println("the square attacked doesn't exist")
      attackSquare(playerAttacking, playerDefending) }
    else {
      val isHit = playerDefending.shipsGrid.isHit(pos)
        if (isHit) {
          val initialNumberOfAliveShips = playerDefending.updateShips.size
          println("You hit " + playerDefending.name)
          // modifications on the defending player
          val newSetOfShips = playerDefending.ships.map(x => x.removeSquare(pos))
          val pDef = playerDefending.copy(_ships = newSetOfShips)
          val newGridOfShipsD = playerDefending.shipsGrid.setHit(pos)
          val numberOfShipsStillAlive = pDef.updateShips.size

          if (initialNumberOfAliveShips - numberOfShipsStillAlive != 0) println ("you sunk a ship of " + playerDefending.name)


          val newDefPlayer = pDef.copy(_shipsGrid = newGridOfShipsD, _ships = newSetOfShips)
          // modifications on the attacking player
          val newGridOfAttackA = playerAttacking.attackGrid.setHit(pos)
          val newAttPlayer = playerAttacking.copy(_attackGrid = newGridOfAttackA)

          // return list of the two players
          List(newAttPlayer, newDefPlayer)
        } else {
          // val newGridOfShips = playerDefending.shipsGrid.setMiss(pos)
          println("You missed the attack")

          val newGridOfShipsD = playerDefending.shipsGrid.setMiss(pos)
          val newDefPlayer = playerDefending.copy(_shipsGrid = newGridOfShipsD)

          val newGridOfAttackA = playerAttacking.attackGrid.setMiss(pos)
          val newAttPlayer = playerAttacking.copy(_attackGrid = newGridOfAttackA)

          println("attack : ")
          println(newAttPlayer.shipsGrid.toString)
          println(newAttPlayer.attackGrid.toString)
          println("def : ")
          println(newDefPlayer.shipsGrid.toString)
          println(newDefPlayer.attackGrid.toString)
          List(newAttPlayer, newDefPlayer)
        }
    }
  }

  def validSquare(square: String): Boolean = {
    List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j").contains(square.head.toString.toLowerCase) &&
      List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10").contains(square.tail)
  }

  def displayScores(game: Battle): Unit = {
    println("Scores :\n - " + game.player1.name + " : " + game.player1.score + "\n - " + game.player2.name + " : " + game.player2.score)
  }

  def manageRestart(nbattle: Battle): Unit = {
    val restart = proposeToRestart
    restart match {
      case true => {
        val np1 = GeneralHelper.initializePlayer(nbattle.player1.name, nbattle.player1.score)
        val np2 = GeneralHelper.initializePlayer(nbattle.player2.name, nbattle.player2.score)
        val ngame = nbattle.copy(_player1 = np2, _player2 = np1)
        val game = GeneralHelper.putShips(ngame, ngame.player1, "player1")
        startBattle(game)
      }
      case false => println("The End")
    }
  }

  def proposeToRestart(): Boolean = {
    println("Do you want a revange ? (y/n)")
    val restart = StdIn.readLine().toLowerCase()
    restart match {
      case "y" => true
      case "n" => false
      case _ => {
        println("Bad entry")
        proposeToRestart()
      }
    }
  }

}