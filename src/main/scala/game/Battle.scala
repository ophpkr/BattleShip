package game

/** A Battle for battleShip
*
* @constructor Create a new Battle with two players.
* @param player1 One of the two players
* @param player2 The other player
*/

case class Battle(private val _player1: Player, private val _player2: Player, private val _ended: Boolean = false) {

  /* getters*/
  def player1 = _player1
  def player2 = _player2
  def ended = _ended

  def finished(): Battle = this.copy(_ended = true)

}