package game

/** A Ship taking part of a battleship.
  *
  *  @constructor Create a new ship with a name, a size and an array of positions (squares)
  *  @param shipName The ship's name
  *  @param shipSize The ship's size (in number of squares it occupies)
  *  @param shipSquares The squares taken by the ship (they have to succeed each other
  */
class Ship(shipName: String, shipSize: Int, shipSquares: Set[String]=Set()) {
  private val _name = shipName
  private var _size = shipSize
  private var _sunk = false
  private var _squaresTaken = shipSquares

  /* getters and setters */
  def name = _name

  def size = _size

  def sunk = _sunk
  def sunk_():Unit = {_sunk = true}

  def squaresTaken = _squaresTaken
  def squaresTaken_(newSquares: Set[String]):Unit = {_squaresTaken = newSquares}

  /* other functions */
  /** Decrease the size of a ship by one
    *
    */
  def decreaseSize(): Unit = {if(_size > 0)_size = _size - 1 else _size = 0 }

  /** Remove a square taken by the boat if square isn't occupied by the ship, nothing happens
    *@param square The square we want to remove from the ship
    */
  def removeSquare(square: String): Unit = {
    if(squaresTaken.contains(square))
      squaresTaken_(squaresTaken-square)
      decreaseSize()
  }

  /** Tell if the ship occupies the given square
    * @param square The square for which we want to know if the ship is on
    * @return Returns true if the ship is on the square else false
    */
  def isTakenByShip(square: String): Boolean = squaresTaken.contains(square)
}