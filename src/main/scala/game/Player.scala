package game

/** A Player taking part of a battleship.
  *
  *  @constructor create a new player with a name.
  *  @param name the player's name
  */
class Player(name: String) {
  private val _name = name
  private var _shipsGrid: GridOfShips
  private var _attackGrid: GridOfAttack
  private var _score = 0

  /* getters and setters */
  def name(): String = _name

  def shipsGrid(): GridOfShips = _shipsGrid
  def shipsGrid_(newGrid: GridOfShips): Unit = {_shipsGrid = newGrid}

  def attackGrid(): Grid = _attackGrid
  def attackGrid_(newGrid: GridOfAttack): Unit = {_attackGrid = newGrid}

  def score = _score

  /** Increase score by one
    *
    */
  increaseScore(): Unit = {_score = score + 1}

}