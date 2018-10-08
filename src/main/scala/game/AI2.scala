package game
import scala.util.Random

/** An AI2 for a battleship game
  *
  * @param _name the name of the AI2
  * @param _shipsGrid the grid of ships of the AI2
  * @param _attackGrid the attack grid of the AI2
  * @param _ships the collection of ships of the AI2
  * @param _score the score of the AI2
  * @param _random the random value of the AI2
  * @param _alreadyHitSquares the set of squares already hit (= handled)
  */
case class AI2(private val _name: String, private val _shipsGrid: GridOfShips, private val _attackGrid: GridOfAttack, private val _ships: Set[Ship], private val _score: Int = 0, _random: Random, private val _alreadyHitSquares: Set[String] = Set()) extends Player(_name, _shipsGrid, _attackGrid, _ships, _score) with AI {

  /* getter */
  def alreadyHitSquares = _alreadyHitSquares

  /* overrides */
  override def random = _random

  /** A copy of this AI2 with new ships
    *
    * @param newShips the new collection of ships of the player
    * @return A new AI2 consisting of its elements except the ships changed onto the given one
    */
  override def copyShips(newShips: Set[Ship]): AI2 = {
    this.copy(_ships = newShips)
  }

  /** A copy of this AI2 with new ships and a new grid of ships
    *
    * @param newGridOfShips the new grid of ships
    * @param newShips       the new collection of ships
    * @return A new AI2 consisting of its elements except the ships changed onto the given one and the grid of ships changed onto the given one
    */
  override def copyGridShipsAndShips(newGridOfShips: GridOfShips, newShips: Set[Ship]): AI2 = {
    this.copy(_shipsGrid = newGridOfShips, _ships = newShips)
  }

  /** A copy of this AI2 with a new grid of attack
    *
    * @param newAttackGrid the new grid of attack
    * @return A new AI2 consisting of its elements except the grid of attack changed onto the given one
    */
  override def copyAttackGrid(newAttackGrid: GridOfAttack): AI2 = {
    this.copy(_attackGrid = newAttackGrid)
  }

  /** A copy of this AI2 with a new grid of ships
    *
    * @param newAttackGrid the new grid of ships
    * @return A new AI2 consisting of its elements except the grid of ships changed onto the given one
    */
  override def copyShipsGrid(newGridOfShips: GridOfShips): AI2 = {
    this.copy(_shipsGrid = newGridOfShips)
  }

  /** A copy of this AI2 with a new grid of ships
    *
    * @param newScore the new score
    * @return A new AI2 consisting of its elements except the score changed onto the given one
    */
  override def copyScore(newScore: Int): AI2 = {
    this.copy(_score = newScore)
  }

  /** A copy of this AI2 with new grids and ships
    *
    * @param gs the new grid of ships
    * @param ga the new grid of attack
    * @param ships the new collection of ships
    * @return A new AI2 consisting of its elements except the grid of attack, grid of ships and ships changed onto the given
    */
  override def copyGridsAndShips(gs: GridOfShips, ga: GridOfAttack, ships: Set[Ship]): AI2 = {
    this.copy(_shipsGrid = gs, _attackGrid = ga, _ships = ships)
  }

  /** Provides the square wished to be attacked
    *
    * @return the string corresponding to this square
    */
  override def attack(): String = {
    val pos = randomPosition(this.random)
    if (alreadyHitSquares.apply(pos)) attack()
    else pos
  }

  /* own functions */

  /** A copy of this AI2 with new grids and ships
    *
    * @param newSquares a Set of squares
    * @return an AI2 consisting of all elements of the AI2 except the _alreadyHitSquare changed to the Set given
    */
  def copySquaresHit(newSquares: Set[String]): AI2 = { this.copy(_alreadyHitSquares = newSquares) }

  /** Adds a square to the already hit positions of the AI2
    *
    * @param square the new square to add
    * @return an AI2 consisting of all elements of the AI2 except the already hit collection for which the square has been added
    */
  def addPos(square: String): Player = {
    val newSquares = alreadyHitSquares+ square
    copySquaresHit(newSquares)
  }
}