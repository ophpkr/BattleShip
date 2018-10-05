package game

/** A Player taking part of a battleship.
  *
  *  @constructor create a new player with a name.
  *  @param pName the player's name
  */
case class Player(private val _name: String, private val _shipsGrid: GridOfShips, private val _attackGrid: GridOfAttack, private val _ships: Set[Ship], private val _score: Int = 0) {

  /* getters */
  def name = _name
  def shipsGrid = _shipsGrid
  def attackGrid = _attackGrid
  def ships = _ships
  def score = _score

  /** Increase score by one
    *
    */
  def increaseScore(): Player = {this.copy(_score = score + 1)}

  /**
    *
    */
  def addShip(ship: Ship): Player = {
    this.copy(_ships = this.ships + ship)
  }

  def hasNoShip(): Boolean = {
    ships.forall(x => x.size == 0)
  }

  def updateShips(): Set[Ship] = {
    ships.filter(x => x.size > 0)
  }

}