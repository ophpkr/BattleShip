package game

import helpers.GridHelper

/** Basics of grids for a game of battleship.
  *
  *  @constructor Create a new grid with a name and size
  *  @param name The grid's name
  *  @param size The grid's size (in number of squares for a side)
  */
abstract class Grid(gridName: String, gridSize: Int) {
  private val _name = gridName
  private val _size = gridSize
  private var _verticalLadder: Array[Int] = Array.range(1, gridSize)
  private var _horizontalLadder: Array[Char] = GridHelper.ladderOfLetter(gridSize)
  private var _representation = Array.fill(gridSize)(Array.fill(gridSize)(".")) // Initialized with dots

  /* getters and setters */
  def name = _name

  def size = _size


  def representation = _representation
 /* def representation_(square: String, symbole: String): Unit = ???

  /* other functions */
  /** Set a square with hit representation
    * @param square The square that has to be changed by a hit
    */
  def setHit(square: String):Unit = ???

  /** Set a square with sunk representation
    * @param square The square that has to be changed by a sunk
    */
  def setSunk(square: String): Unit = ???

  /** Set a square with miss representation
    * @param square The square that has to be changed by a miss
    */
  def setMiss(square: String): Unit = ???
*/
}