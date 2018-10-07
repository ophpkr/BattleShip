package game

/** A player that play the battleShip
  *
  * @param _name the name of the player
  * @param _shipsGrid the grid of ships of the player
  * @param _attackGrid the attack grid of the player
  * @param _ships the collection of ships owned by the player
  * @param _score the score of the player
  */
abstract class Player(private val _name: String, private val _shipsGrid: GridOfShips, private val _attackGrid: GridOfAttack, private val _ships: Set[Ship], private val _score: Int = 0) extends Speaker{

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
  def increaseScore(): Player = { copyScore(score + 1)}

  /** Adds a ship to the player
    *
    * @param ship the ship to add to the player
    * @return the player with his new ship
    */
  def addShip(ship: Ship): Player = {copyShips(ships + ship)}

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

  /** Copies the player with new ships
    *
    * @param newShips the new collection of ships of the player
    * @return the player "updated" with the new collection given
    */
  def copyShips(newShips: Set[Ship]): Player

  /** Copies the player with a new grid of ships and a new collection of ships
    *
    * @param newGridOfShips the new grid of ships
    * @param newShips the new collection of ships
    * @return the player "updated" with the new grid and the new collection of ships given
    */
  def copyGridShipsAndShips(newGridOfShips: GridOfShips, newShips: Set[Ship]): Player

  /** Copies the player with a new grid of attack
    *
    * @param newAttackGrid the new grid of attack
    * @return the player "updated" with the new grid of attack given
    */
  def copyAttackGrid(newAttackGrid: GridOfAttack): Player

  /** Copies the player with a new grid of ships
    *
    * @param newAttackGrid the new grid of ships
    * @return the player "updated" with the new grid of ships given
    */
  def copyShipsGrid(newGridOfShips: GridOfShips): Player

  /** Copies the player with a new score
    *
    * @param newScore integer corresponding to the new score
    * @return the player "updated" with a new score
    */
  def copyScore(newScore: Int): Player

  /** Copies a player with new grids and ships
    *
    * @param gs the new grid of ships
    * @param ga the new grid of attack
    * @param ships the new collection of ships
    * @return the player "updated" with the anterior parameters
    */
  def copyGridsAndShips(gs: GridOfShips, ga: GridOfAttack, ships: Set[Ship]): Player

}