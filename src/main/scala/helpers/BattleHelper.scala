package helpers
import game._

import scala.io.StdIn

object BattleHelper {

  def startBattle(game: Battle): Battle = {
    val newPlayers = attackSquare(game.player1, game.player2)
    val newBattle = game.copy(_player1 = newPlayers.apply(0), _player2 = newPlayers(1))
    val newPLayers2 = attackSquare(game.player2, game.player1)
    val newBattle2 = newBattle.copy(_player1 = newPLayers2(1), _player2 = newPLayers2(0))
    //TODO: ajouter la verification de fin de jeu
    startBattle(newBattle2)
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
          println("You hit " + playerDefending.name)
          // modifications on the defending player
          val newSetOfShips = playerDefending.ships.map(x => x.removeSquare(pos))
          val newGridOfShipsD = playerDefending.shipsGrid.setHit(pos)
          val newDefPlayer = playerDefending.copy(_shipsGrid = newGridOfShipsD, _ships = newSetOfShips)
          // modifications on the attacking player
          val newGridOfAttackA = playerAttacking.attackGrid.setHit(pos)
          val newAttPlayer = playerAttacking.copy(_attackGrid = newGridOfAttackA)

          // return list of the two players
          List(newAttPlayer, newDefPlayer)
        } else {
          val newGridOfShips = playerDefending.shipsGrid.setMiss(pos)
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
}