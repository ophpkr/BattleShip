package game

/** A Ship taking part of a battleship.
  *
  *  @constructor Create a new ship with a name, a size and an array of positions (squares)
  *  @param shipName The ship's name
  *  @param shipSize The ship's size (in number of squares it occupies)
  *  @param shipSquares The squares taken by the ship (they have to succeed each other
  */
case class Ship(_name: String, _size: Int, _squaresTaken: Set[String] = Set(), _sunk: Boolean = false) {

  /* getters */
  def name = _name
  def size = _size
  def sunk = _sunk
  def squaresTaken = _squaresTaken

  /* other functions */
  /** Decrease the size of the ship by one
    *@return Ship corresponding to the initial ship with a size decreased by one
    */
  def decreaseSize(): Ship = {if(this.size > 0) this.copy(_size = this.size - 1) else this.copy(_size = 0)}

  /** Remove a square taken by the boat if square isn't occupied by the ship, nothing happens
    *@param square The square we want to remove from the ship
    *@return The new ship without the square given
    */
  def removeSquare(square: String): Ship = {
    if (squaresTaken.apply(square)) {
      val newSquaresTaken = squaresTaken.filterNot(x => x == square)
      val newShip = this.copy(_squaresTaken = newSquaresTaken)
      newShip.decreaseSize()
    }
    else this
  }

  /** Tell if the ship occupies the given square
    * @param square The square for which we want to know if the ship is on
    * @return Returns true if the ship is on the square else false
    */
  def isTakenByShip(square: String): Boolean = squaresTaken.contains(square)
}