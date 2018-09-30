package game

/** A Battle for battleShip
*
* @constructor Create a new Battle with two players.
* @param player1 One of the two players
* @param player2 The other player
*/
class Battle(player1: Player, player2: Player) {
  private val _player1 = player1
  private val _player2 = player2
  private var ended = false

  /* getters and setters */
  def player1 = _player1
  def player2 = _player2

  def ended = _ended
  def ended_(): Unit = {_ended = true}
}