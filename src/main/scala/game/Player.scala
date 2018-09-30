package game
/*
/** A Player taking part of a battleship.
  *
  *  @constructor create a new player with a name.
  *  @param pName the player's name
  */
class Player(pName: String) {
  private val _name = pName
  var _shipsGrid: GridOfShips
  var _attackGrid: GridOfShips
  private var _score = 0

  /* getters and setters */
  def name(): String = _name

  def shipsGrid(): GridOfShips = _shipsGrid
  def shipsGrid_(newGrid: GridOfShips): Unit = {_shipsGrid = newGrid}

  def attackGrid(): Grid = _attackGrid
  def attackGrid_(newGrid: GridOfShips): Unit = {_attackGrid = newGrid}

  def score = _score

  /** Increase score by one
    *
    */
  def increaseScore(): Unit = {_score = score + 1}

}*/