package helpers
import main.scala.elements.{Battle, GridOfAttack, GridOfShips}
import main.scala.players._

import scala.io.StdIn

/** Manager the attack part of a battleship */
object BattleHelper {

  private var _loop: Int = 0 // used for loops for AIs try
  def loop = _loop
  def loop_ = { _loop = loop + 1 }
  /** Constitutes the main loop of the attack part of a battleShip
    *
    * @param game the game to launch
    */
  def startBattle(game: Battle): Unit = {
    // start with the player who has to start attck the oponent
    val newPlayers = attackSquare(game.player1, game.player2)
    val newBattle = game.copy(_player1 = newPlayers.apply(0), _player2 = newPlayers.apply(1))
    println("\u001b[2J")
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
    }
  }

  /** Gives the two players corresponding to initial players having "changed" after a player's attack
    *
    * @param playerAttacking the player attacking (that enters a square to attack)
    * @param playerDefending the player receiving an attack from the attacking player
    * @return a list of two players, the first one corresponding to the attacking player "modified" and the second one to the defending player "modified"
    */
  def attackSquare(playerAttacking: Player, playerDefending: Player): List[Player] = {
    GeneralHelper.printer(playerAttacking.attackingTurnSpeak)
    // display grids of the player
    println(playerAttacking.attackGrid.toString)
    println(playerAttacking.shipsGrid.toString)
    GeneralHelper.printer(playerAttacking.demandeEnterSquareSpeak)
    // According to the kind of player, ask from console or ask hidden
    val pos = playerAttacking match {
      case HumanPlayer(_,_,_,_,_) => StdIn.readLine().toLowerCase()
      case AI1(_,_,_,_,_,_) => playerAttacking.asInstanceOf[AI1].attack
      case AI2(_,_,_,_,_,_,_) => playerAttacking.asInstanceOf[AI2].attack
      case AI3(_,_,_,_,_,_,_,_,_) => playerAttacking.asInstanceOf[AI3].attack
    }
    // if the square doesn't exist, the player has to give another square
    if(!validSquare(pos)) {
      println("the square attacked doesn't exist") //no need of GeneralHelper.printer because always true for AI
      attackSquare(playerAttacking, playerDefending)
    }
    else {
      val isHit = playerDefending.shipsGrid.isHit(pos)
      // if the attacking player hit a defending player's ship
      if (isHit) {
        val initialNumberOfAliveShips = playerDefending.updateShips.size
        // println("You hit " + playerDefending.name)
        GeneralHelper.printer(playerAttacking.hitSpeak(playerDefending))
        // "modifications" on the defending player
        val newSetOfShips = playerDefending.ships.map(x => x.removeSquare(pos))
        //val pDef = playerDefending.copyShips(_ships = newSetOfShips) : already changed 3 lines down
        val newGridOfShipsD = playerDefending.shipsGrid.setHit(pos)
        val numberOfShipsStillAlive = playerDefending.updateShips.size
        val newDefPlayer = playerDefending.copyGridShipsAndShips(newGridOfShipsD, newSetOfShips)

        // "modifications" on the attacking player
        val newGridOfAttackA = playerAttacking.attackGrid.setHit(pos)
        val newAttPlayer = playerAttacking match {
          case AI2(_, _, _, _, _, _, _) => {
            val pai2 = playerAttacking.asInstanceOf[AI2].addPos(pos)
            pai2.copyAttackGrid(newGridOfAttackA)
            }
          // playerAttacking.copyAttackGrid(newGridOfAttackA)
          case AI3(_, _, _, _, _, _, _, _, _) => {
            val ai = playerAttacking.asInstanceOf[AI3].addPos(pos)
            val pai3 = ai.asInstanceOf[AI3].manageHit(pos)
            pai3.copyAttackGrid(newGridOfAttackA)
          }

          case _ => {
            playerAttacking.copyAttackGrid(newGridOfAttackA)
          }
        }


        // checks if a ship has been sunk in consequence to the hit
        if (initialNumberOfAliveShips - numberOfShipsStillAlive != 0) {
          GeneralHelper.printer(playerAttacking.sunkSpeak(playerDefending))
          val newAttPl = playerAttacking match {
            case AI3(_,_,_,_,_,_,_,_,_) => newAttPlayer.asInstanceOf[AI3].manageSunk(pos)
            case _ => newAttPlayer
          }
          List(newAttPl, newDefPlayer)
        }
        else {
          // return list of the two players
          List(newAttPlayer, newDefPlayer)
        }


        // if the attacking player missed his hit
      } else {
        GeneralHelper.printer(playerAttacking.missSpeak)

        // "modifications" on the defending player
        val newGridOfShipsD = playerDefending.shipsGrid.setMiss(pos)
        val newDefPlayer = playerDefending.copyShipsGrid(newGridOfShipsD)

        // "modifications on the attacking player
        val newGridOfAttackA = playerAttacking.attackGrid.setMiss(pos)
        val natt = playerAttacking match {
          case AI3(_,_,_,_,_,_,_,_,_) => {
            val ai = playerAttacking.asInstanceOf[AI3].addPos(pos)
            ai.asInstanceOf[AI3].manageMiss(pos)
          }
          case _ => playerAttacking
        }
        val newAttPlayer = natt.copyAttackGrid(newGridOfAttackA)

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
    val restart = this.askRestart(nbattle)
    restart match {
      case true => {
        val np1 = resetPlayer(nbattle.player1)
        val np2 = resetPlayer(nbattle.player2)
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

  /** A copy of the player with every elements "updated" during the game reseted
    *
    * @param player the player to reset
    * @return a new player consisting of the same elements except those that are proper to a bettlehip round
    */
  def resetPlayer(player: Player): Player = {
    val rep = GeneralHelper.initialGrid
    val g: GridOfShips = GridOfShips("gridOfShips "+player.name, 10, _representation = rep)
    val ga: GridOfAttack = GridOfAttack("gridOfAttack "+player.name, 10, _representation = rep)
    player match {
      case AI2(_,_,_,_,_,_,_) =>  {
        val pnorm = player.copyGridsAndShips(g, ga, Set())
        pnorm.asInstanceOf[AI2].copySquaresHit(Set())
      }
      case AI3(_,_,_,_,_,_,_,_,_) => {
        val pnorm = player.copyGridsAndShips(g, ga, Set())
        pnorm.asInstanceOf[AI3].setAlreadyHitSquares(Set()).setCurFirstSquare("").setPositionToMentalMap(GeneralHelper.initMapAI3(Map[String, String](), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")))
      }
      case _ =>  player.copyGridsAndShips(g, ga, Set())
    }
  }

  /** Manages the restart of a game (make a revange)
    *
    * @param game the game proposed to be restarted
    * @return true if the boolean
    */
  def askRestart(game: Battle): Boolean = {
    // A human player is in the game, he chooses the restart of it or not
    if (game.player1.isInstanceOf[HumanPlayer] || game.player1.isInstanceOf[HumanPlayer]) {
      val restart = this.proposeToRestart
      restart
    }
    // no human player, the game restart unless 100 game have already been played
    else {
      if (this.loop < 99) {
        println("loop " + this.loop)
        this.loop_
        val restart = true
        restart
      }
      else {
        val restart = false
        restart
      }
    }
  }
}