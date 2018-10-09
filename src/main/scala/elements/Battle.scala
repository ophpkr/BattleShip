package main.scala.elements

import main.scala.players.Player

/** A Battle for battleShip
*
* @constructor Creates a new Battle with two players.
* @param player1 One of the two players
* @param player2 The other player
*/

case class Battle(private val _player1: Player, private val _player2: Player){

  /* getters*/
  def player1 = _player1
  def player2 = _player2

}
