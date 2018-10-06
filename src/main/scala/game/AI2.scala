package game
import scala.util.Random

case class AI2(private val _name: String, private val _shipsGrid: GridOfShips, private val _attackGrid: GridOfAttack, private val _ships: Set[Ship], private val _score: Int = 0, _random: Random) extends Player(_name, _shipsGrid, _attackGrid, _ships, _score) with AI {

  private val _alreadyHitSquares: Set[String] = Set()

  def alreadyHitSquares = _alreadyHitSquares
  /* overrides */

  override def random = _random

  /** Copies the player with new ships
    *
    * @param newShips the new collection of ships of the player
    * @return the player "updated" with the new collection given
    */
  override def copyShips(newShips: Set[Ship]): AI2 = {
    this.copy(_ships = newShips)
  }

  /** Copies the player with a new grid of ships and a new collection of ships
    *
    * @param newGridOfShips the new grid of ships
    * @param newShips       the new collection of ships
    * @return the player "updated" with the new grid and the new collection of ships given
    */
  override def copyGridShipsAndShips(newGridOfShips: GridOfShips, newShips: Set[Ship]): AI2 = {
    this.copy(_shipsGrid = newGridOfShips, _ships = newShips)
  }

  /** Copies the player with a new grid of attack
    *
    * @param newAttackGrid the new grid of attack
    * @return the player "updated" with the new grid of attack given
    */
  override def copyAttackGrid(newAttackGrid: GridOfAttack): AI2 = {
    this.copy(_attackGrid = newAttackGrid)
  }

  /** Copies the player with a new grid of ships
    *
    * @param newAttackGrid the new grid of ships
    * @return the player "updated" with the new grid of ships given
    */
  override def copyShipsGrid(newGridOfShips: GridOfShips): AI2 = {
    this.copy(_shipsGrid = newGridOfShips)
  }

  /** Copies the player with a new score
    *
    * @param newScore the new score
    * @return the player "updated" with a new score
    */
  override def copyScore(newScore: Int): AI2 = {
    this.copy(_score = newScore)
  }

  /** Copies a player with new grids and ships
    *
    * @param gs the new grid of ships
    * @param ga the new grid of attack
    * @param ships the new collection of ships
    * @return the player "updated" with the anterior parameters
    */
  override def copyGridsAndShips(gs: GridOfShips, ga: GridOfAttack, ships: Set[Ship]): AI2 = {
    this.copy(_shipsGrid = gs, _attackGrid = ga, _ships = ships)
  }

  override def attack(): String = {
    val pos = randomPosition(this.random)
    if (alreadyHitSquares.apply(pos)) attack()
    else pos
  }
}