package main.scala.elements

import scala.annotation.tailrec

/** A grid for a battleShip
  *
  * @param _name the name of the grid
  * @param _size the size of the grid
  * @param _verticalLadder the vertical ladder for the display of the grid
  * @param _horizontalLadder the horizontal ladder for the display of the grid
  * @param _representation the main representation of the grid
  */
abstract class Grid(private val _name: String, private val _size: Int, private val _verticalLadder: List[String] = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                private val _horizontalLadder: List[String] = List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), private val _representation: List[List[String]]) {

  /* getters */
  def name = _name
  def size = _size
  def verticalLadder = _verticalLadder
  def horizontalLadder = _horizontalLadder
  def representation = _representation

  /* other functions */

  /** Updates the symbol of a square of the grid
    *
    * @param square the square for which we have to change the symbol, the square has to exist
    * @param symbol the symbole to put
    * @return the grid with symbol changed in the given square
    */
  def updateSquare(square: String, symbol: String): Grid

  /** Sets a hit symbol in a given square
    *
    * @param square the square for which we have to put a hit symbol, the square has to exist
    * @return the grid with a hit symbol in the given square
    */
  def setHit(square: String):Grid

  /** Sets a miss symbol in a given square
    *
    * @param square the square for which we have to put a miss symbol, the square has to exist
    * @return the grid with a miss symbol in the given square
    */
  def setMiss(square: String): Grid

  /** Overrides the toString function
    *
    * @return the string corresponding to the way to print an instance of the class
    */
  override def toString(): String = {
    val hLadder = horizontalLadder.mkString("")
    val letters = hLadder.flatMap(x => x.toString + "\t").toString + "\n"
    "\t" + letters + this.mainGridToString()
  }

  /** Creates the string correspond to the main grid
    *
    * @param s the initial string to which we have to add the string corresponding of the stepth line of the grid
    * @param step the line considered
    * @return the string corresponding to the concatanation of the initial string and the string of the stepth line.
    *         If the line doesn't exist in the grid, the initial string is returned
    */
  @tailrec
  private def mainGridToString(s: String = "", step: Int = 0): String = {
    if (step >= 10 || step < 0) s
    else {
      val l = this.representation.apply(step).mkString("")
      val stg = s + (step + 1).toString + "\t" + l.flatMap(x => x.toString + "\t").toString + "\n"
      mainGridToString(stg, step + 1)
    }
  }


}