package main.scala.elements

import helpers.GridHelper

/** An attack grid given to a player for a battleship
  *
  * @param _name the name of the grid
  * @param _size the size of the grid
  * @param _verticalLadder the vertical ladder for the display of the grid
  * @param _horizontalLadder the horizontal ladder for the display of the grid
  * @param _representation the main representation of the grid
  */
case class GridOfAttack(private val _name: String, private val _size: Int, private val _verticalLadder: List[String] = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                       private val _horizontalLadder: List[String] = List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), private val _representation: List[List[String]]) extends Grid(_name, _size, _verticalLadder, _horizontalLadder, _representation) {

  /** Overrides the updateSquare function of Grid
    *
    * @param square the square for which we have to change the symbol, the square has to exist
    * @param symbol the symbole to put
    * @return the grid with symbol changed in the given square
    */
  override def updateSquare(square: String, symbol: String): GridOfAttack = {
    val pos = GridHelper.squareToListPositions(square)
    val line = representation(pos(0))
    val newList = representation.updated(pos(0), line.updated(pos(1), symbol))
    this.copy(_representation = newList)
  }

  /** Overrides the setHit function of Grid
    *
    * @param square the square for which we have to put a hit symbol, the square has to exist
    * @return the grid with a hit symbol in the given square
    */
  override def setHit(square: String):GridOfAttack = {
    updateSquare(square, "o")
  }

  /** Overrides the setMiss function of Grid
    *
    * @param square the square for which we have to put a miss symbol, the square has to exist
    * @return the grid with a miss symbol in the given square
    */
  override def setMiss(square: String): GridOfAttack = {
    val pos = GridHelper.squareToListPositions(square)
    if(representation.apply(pos(0)).apply(pos(1)) != "o") {
      updateSquare(square, "x")
    }
    else this
  }
}
