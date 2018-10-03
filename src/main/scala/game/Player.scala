package game

/** A Player taking part of a battleship.
  *
  *  @constructor create a new player with a name.
  *  @param pName the player's name
  */
case class Player(private val _name: String, private val _shipsGrid: GridOfShips, private val _ships: Set[Ship] = Set(), private val _score: Int = 0) {

  /* getters */
  def name = _name
  def shipsGrid = _shipsGrid
  // def attackGrid = _attackGrid
  def score = _score

  /** Increase score by one
    *
    */
  def increaseScore(): Player = {this.copy(_score = score + 1)}

}