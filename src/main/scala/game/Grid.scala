package game

import helpers.GridHelper

/** Basics of grids for a game of battleship.
  *
  *  @constructor Create a new grid with a name and size
  *  @param name The grid's name
  *  @param size The grid's size (in number of squares for a side)
  */
abstract class Grid(private val _name: String, private val _size: Int, private val _verticalLadder: List[String] = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                private val _horizontalLadder: List[String] = List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), private val _representation: List[List[String]]) {

  /* getters */
  def name = _name
  def size = _size
  def verticalLadder = _verticalLadder
  def horizontalLadder = _horizontalLadder
  def representation = _representation
/*def representation_(square: String, symbol: String): Unit = {
    val pos = GridHelper.squareToArrayPositions(square)
    _representation(pos(0))(pos(1)) = symbol
  }*/

  /* other functions */
  /**
    *
    */
  def updateSquare(square: String, symbol: String): Grid = {
    this
    /*val pos = GridHelper.squareToListPositions(square)
    /*var list = representation.map(x => x.toArray)
    var transf = list.toArray
    transf(pos(0))(pos(1)) = symbol
    val t = transf.map(x => x.toList)
    val newList = t.toList*/
    val line = representation(pos(0))
    val newList = representation.updated(pos(0), line.updated(pos(1), symbol))
    this.copy(_representation = newList)*/
  }

  /** Set a square with hit representation
    * @param square The square that has to be changed by a hit
    */
  def setHit(square: String):Grid = {
    updateSquare(square, "o")
  }

  /** Set a square with miss representation
    * @param square The square that has to be changed by a miss
    */
  def setMiss(square: String): Unit = {
    updateSquare(square, "x")
  }

  /** Override toString for Grid
    */
  override def toString(): String = {
    val hLadder = horizontalLadder.mkString("")
    val letters = hLadder.flatMap(x => x.toString + "\t").toString + "\n"
    "\t" + letters + this.mainGridToString()
  }

  /**
    *
    * @param s
    * @param step
    * @return
    */
  def mainGridToString(s: String = "", step: Int = 0): String = {
    if (step >= 10) s
    else {
      val l = this.representation.apply(step).mkString("")
      // val numbersAdded = (step + 1).toString + l
      val stg = s + (step + 1).toString + "\t" + l.flatMap(x => x.toString + "\t").toString + "\n"
      mainGridToString(stg, step + 1)
    }
  }


}