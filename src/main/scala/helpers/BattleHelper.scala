package helpers
import game._

import scala.io.StdIn

/** Helper for the attack part of a battleship */
object BattleHelper {

  /** Constitutes the main loop of the attack part of a battleShip
    *
    * @param game the game to launch
    */
  def startBattle(game: Battle): Unit = {
    // start with the player who has to start attck the oponent
    val newPlayers = attackSquare(game.player1, game.player2)
    val newBattle = game.copy(_player1 = newPlayers.apply(0), _player2 = newPlayers.apply(1))
    // case: the player 2 lost the game
    if (newBattle.player2.hasNoShip) {
      println(newBattle.player1.name + " won ! ")
      // "update" score and propose to restart
      val p1 = newBattle.player1.increaseScore
      val ng = newBattle.copy(_player1 = p1)
      displayScores(ng)
      manageRestart(ng)
    }
    // case: the player 2 still in the battle => it is his turn
    else {
      val newPlayers2 = attackSquare(newBattle.player2, newBattle.player1)
      val newBattle2 = newBattle.copy(_player1 = newPlayers2.apply(1), _player2 = newPlayers2.apply(0))
      // case: the player lost the game
      if (newBattle2.player1.hasNoShip) {
        println(newBattle2.player2.name + " won !")
        // "update" score and propose to restart
        val p2 = newBattle.player2.increaseScore
        val ng = newBattle.copy(_player2 = p2)
        displayScores(ng)
        manageRestart(ng)
      }
      // case: the player 1 still in the battle => it is his turn
      else {
        startBattle(newBattle2)
      }
      //TODO: ajouter la verification de fin de jeu
    }
  }

  /** Gives the two players corresponding to initial players having "changed" after a player's attack
    *
    * @param playerAttacking the player attacking (that enters a square to attack)
    * @param playerDefending the player receiving an attack from the attacking player
    * @return a list of two players, the first one corresponding to the attacking player "modified" and the second one to the defending player "modified"
    */
  def attackSquare(playerAttacking: Player, playerDefending: Player): List[Player] = {
    println(playerAttacking.name + " has to enter a position (ex: A1)")
    val pos = StdIn.readLine().toLowerCase()
    // if the square doesn't exist, the player has to give another square
    if(!validSquare(pos)) {
      println("the square attacked doesn't exist")
      attackSquare(playerAttacking, playerDefending) }
    else {
      val isHit = playerDefending.shipsGrid.isHit(pos)
      // if the attacking player hit a defending player's ship
        if (isHit) {
          val initialNumberOfAliveShips = playerDefending.updateShips.size
          println("You hit " + playerDefending.name)
          // "modifications" on the defending player
          val newSetOfShips = playerDefending.ships.map(x => x.removeSquare(pos))
          val pDef = playerDefending.copy(_ships = newSetOfShips)
          val newGridOfShipsD = playerDefending.shipsGrid.setHit(pos)
          val numberOfShipsStillAlive = pDef.updateShips.size
          val newDefPlayer = pDef.copy(_shipsGrid = newGridOfShipsD, _ships = newSetOfShips)

          // checks if a ship has been sunk in consequence to the hit
          if (initialNumberOfAliveShips - numberOfShipsStillAlive != 0) println ("you sunk a ship of " + playerDefending.name)

          // "modifications" on the attacking player
          val newGridOfAttackA = playerAttacking.attackGrid.setHit(pos)
          val newAttPlayer = playerAttacking.copy(_attackGrid = newGridOfAttackA)

          // return list of the two players
          List(newAttPlayer, newDefPlayer)
        // if the attacking player missed his hit
        } else {
          println("You missed the attack")

          // "modifications" on the defending player
          val newGridOfShipsD = playerDefending.shipsGrid.setMiss(pos)
          val newDefPlayer = playerDefending.copy(_shipsGrid = newGridOfShipsD)

          // "modifications on the attacking player
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

  /** Checks if a square is valid for a size of 10
    *
    * @param square the square we want to check the existence of
    * @return true if the square exist else false
    */
  def validSquare(square: String): Boolean = {
    List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j").contains(square.head.toString.toLowerCase) &&
      List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10").contains(square.tail)
  }

  /** Displays the players' score of a battle
    *
    * @param game the game for which its players' score have to be dispayed
    */
  def displayScores(game: Battle): Unit = {
    println("Scores :\n - " + game.player1.name + " : " + game.player1.score + "\n - " + game.player2.name + " : " + game.player2.score)
  }

  /** Manages the restart of a battle conserving player's scores
    * In the case the battle is restarted, the players keep their scores
    * and the player that was the last to play the anterior battle becomes the first one of the new battle
    * In the other case, the program finishes
    *
    * @param nbattle the battle we propose to restart
    */
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

  /** Proposes to restart
    *
    * @return true if the player decided to play again else false
    */
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