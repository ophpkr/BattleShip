package game

/** A Ship taking part of a battleship.
  *
  *  @constructor Create a new ship with a name and size
  *  @param name The ship's name
  *  @param size The ship's size (in number of squares it occupies)
  */
class Ship(name: String, size: Int) {
  private val _name = name
  private var _size = size
  private var _sunk = false
  private var _squaresTaken = []

  /* getters and setters */
  def name = _name

  def size = _size

  def sunk = _sunk
  def sunk_():Unit = {_sunk = true}

  def squaresTaken = _squaresTaken
  def squaresTaken_(newSquares: [String]):Unit = {_squaresTaken = newSquares}

  /* other functions */
  /** Decrease the size of a ship by one
    *
    */
  def decreaseSize(): Unit = {if(_size > 0)_size = _size - 1 else 0 }

  /** Remove a square taken by the boat
    *@param square The square we want to remove from the ship
    */
  def removeSquare(square: String): Unit = ???

  /** Tell if the ship occupies the given square
    * @param square The square for which we want to know if the ship is on
    * @return Returns true if the ship is on the square else false
    */
  def isTakenByShip(square: String): Boolean = ???


}