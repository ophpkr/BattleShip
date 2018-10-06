package game
import helpers._

/** A grid of ships given to a player for a battleship
  *
  * @param _name the name of the grid
  * @param _size the size of the grid
  * @param _verticalLadder the vertical ladder for the display of the grid
  * @param _horizontalLadder the horizontal ladder for the display of the grid
  * @param _representation the main representation of the grid
  */
case class GridOfShips(private val _name: String, private val _size: Int, private val _verticalLadder: List[String] = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                       private val _horizontalLadder: List[String] = List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), private val _representation: List[List[String]]) extends Grid(_name, _size, _verticalLadder, _horizontalLadder, _representation) {

  /** Overrides the updateSquare function of Grid
    *
    * @param square the square for which we have to change the symbol, the square has to exist
    * @param symbol the symbole to put
    * @return the grid with symbol changed in the given square
    */
  override def updateSquare(square: String, symbol: String): GridOfShips = {
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
  override def setHit(square: String):GridOfShips = {
    updateSquare(square, "o")
  }

  /** Overrides the setMiss function of Grid
    *
    * @param square the square for which we have to put a hit symbol, the square has to exist
    * @return the grid with a miss symbol in the given square
    */
  override def setMiss(square: String): GridOfShips = {
    updateSquare(square, "x")
  }

  /** Tells if a square is not occupied and exists
    *
    * @param square the square for which we wonder if it isn't already taken
    * @return true if the square is free and exists else false
    */
  def isNotOccupiedSquare(square: String): Boolean = {
    val pos = GridHelper.squareToListPositions(square)
    if (pos(0) >= size  || pos(1) >= size ) false
    else representation.apply(pos(0)).apply(pos(1)) == "."
  }

  /** Tells if a list of squares given can occupy the grid
    *
    * @param squares the list of squares we want to add
    * @return true if all of the squares exist and are not occupied else false
    */
  def canOccupySquares(squares: List[String]): Boolean = {
    !(squares.map(square => isNotOccupiedSquare(square))).contains(false)
  }

  /** Adds a ship representation to all of the squares wished
    *
    * @param squares the list of squares we want to put the ship symbol in
    * @param grid the list being changed
    * @param index the index of the square treated
    * @return the grid in which the squares given are updated to ship symbol
    */
  def addShips(squares: List[String], grid: GridOfShips, index: Int = 0): GridOfShips = {
          if (index >= squares.size) grid
          else addShips(squares, grid.updateSquare(squares.apply(index), "S"), index + 1)
  }


  /** Tells if a square is taken by a ship
    *
    * @param square the square for which we want to know if it's taken by a ship, the square has to exist
    * @return true if the square is taken by a ship
    */
  def isHit(square: String): Boolean = {
    val pos = GridHelper.squareToListPositions(square)
    representation.apply(pos(0)).apply(pos(1)) == "S"
  }
}