package main.scala.elements

/** A ship for a battleShip
  *
  * @param _name the name of the ship
  * @param _size the size of the ship
  * @param _squaresTaken the collection of squares taken by the ship
  */
case class Ship(_name: String, _size: Int, _squaresTaken: Set[String] = Set()) {

  /* getters */
  def name = _name
  def size = _size
  def squaresTaken = _squaresTaken

  /* other functions */
  /** Decreases the size of a ship by one
    *
    * @return a ship having the size decreased by one
    */
  def decreaseSize(): Ship = {if(this.size > 0) this.copy(_size = this.size - 1) else this.copy(_size = 0)}

  /** Removes a square from a ship's squaresTaken collection
    *
    * @param square the square to remove from the ship's squares collection
    * @return if the square exists in the collection : a ship with the square removed from its collection and with a size decreased by one else the initial ship
    */
  def removeSquare(square: String): Ship = {
    if (squaresTaken.apply(square)) {
      val newSquaresTaken = squaresTaken.filterNot(x => x == square)
      val newShip = this.copy(_squaresTaken = newSquaresTaken)
      newShip.decreaseSize()
    }
    else this
  }

  /** Tells if the ship occupies a given square
    *
    * @param square the square we want to know if it's occupied by the ship
    * @return true if the square is taken by the ship else false
    */
  def isTakenByShip(square: String): Boolean = squaresTaken.apply(square)


}
