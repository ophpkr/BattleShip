package game

/** A player that play the battleShip
  *
  * @param _name the name of the player
  * @param _shipsGrid the grid of ships of the player
  * @param _attackGrid the attack grid of the player
  * @param _ships the collection of ships owned by the player
  * @param _score the score of the player
  */
case class Player(private val _name: String, private val _shipsGrid: GridOfShips, private val _attackGrid: GridOfAttack, private val _ships: Set[Ship], private val _score: Int = 0) {

  /* getters */
  def name = _name
  def shipsGrid = _shipsGrid
  def attackGrid = _attackGrid
  def ships = _ships
  def score = _score

  /* other functions */

  /** Increases the score of the player by one
    *
    * @return the player with his score increased
    */
  def increaseScore(): Player = {this.copy(_score = score + 1)}

  /** Adds a ship to the player
    *
    * @param ship the ship to add to the player
    * @return the player with his new ship
    */
  def addShip(ship: Ship): Player = {
    this.copy(_ships = this.ships + ship)
  }

  /** Tells if a player has no ship
    *
    * @return true if the player has no ship else false
    */
  def hasNoShip(): Boolean = {
    ships.forall(x => x.size == 0)
  }

  /** Selects only the ships still "alive" of the player
    *
    * @return the collection of ships still alive
    */
  def updateShips(): Set[Ship] = {
    ships.filter(x => x.size > 0)
  }

}