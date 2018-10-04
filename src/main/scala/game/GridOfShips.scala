package game
import helpers._
/** The grid containing a player ships' placements and oponent's hits
  *
  *  @constructor Create a new grid of Ships with a name and size
  *  @param name The grid's name
  *  @param size The grid's size (in number of squares for a side)
  */
case class GridOfShips(private val _name: String, private val _size: Int, private val _verticalLadder: List[String] = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                       private val _horizontalLadder: List[String] = List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), private val _representation: List[List[String]]) extends Grid(_name, _size, _verticalLadder, _horizontalLadder, _representation) {

  /* Particular functions */
  /** Tell if a ship can occupy the squares given in parameter
    * @param squares Squares we want to know if they are free and exist in the grid.
    * @return Returns true if the squares are free and in the grid else false
    */
  def canOccupySquares(squares: List[String]): Boolean = {
    !(squares.map(square => isNotOccupiedSquare(square))).contains(false)
  }

  /** Add a ship representation on the squares given (squares have to exist and be free)
    * @param squares Squares on which we want to place a ship
    */
  def addShips(squares: List[String], grid: GridOfShips, index: Int = 0): GridOfShips = {
        //if (squares.isEmpty) this
        //else {
          if (index >= squares.size) grid
          else
            addShips(squares, grid.updateSquare(squares.apply(index), "S"), index + 1)
        //}

  }

  /*/** Get the state of a given square (miss, hit or sunk)
    * @param square The square from which we want to know the state
    * @return Returns the state of the square :
    *         "miss" if there is no ship on the square or if a ship has already been hit or sunk,
    *         "hit" if a ship has been hit for the first time on the given square,
    *         "sunk" if a ship has been hit for the first time on the given square and this hit makes it sunk
    */
  def getStateAfterHit(square: String): String = {
    val pos = GridHelper.squareToArrayPositions(square)
    representation(pos(0))(pos(1))
  }*/

  /** Tell if a square is already taken by a ship
    * @param square The square we want to check it is not occupied
    * @return True if the square is free, else false
    */
  def isNotOccupiedSquare(square: String): Boolean = {
    val pos = GridHelper.squareToListPositions(square)
    if (pos(0) >= size  || pos(1) >= size ) false
    else representation.apply(pos(0)).apply(pos(1)) == "."
  }

  override def updateSquare(square: String, symbol: String): GridOfShips = {
    val pos = GridHelper.squareToListPositions(square)
    /*var list = representation.map(x => x.toArray)
    var transf = list.toArray
    transf(pos(0))(pos(1)) = symbol
    val t = transf.map(x => x.toList)
    val newList = t.toList*/
    val line = representation(pos(0))
    val newList = representation.updated(pos(0), line.updated(pos(1), symbol))
    this.copy(_representation = newList)
  }

  def isHit(square: String): Boolean = {
    val pos = GridHelper.squareToListPositions(square)
    representation.apply(pos(0)).apply(pos(1)) == "S"
  }

  /** Set a square with hit representation
    * @param square The square that has to be changed by a hit
    */
  override def setHit(square: String):GridOfShips = {
    updateSquare(square, "o")
  }

  /** Set a square with miss representation
    * @param square The square that has to be changed by a miss
    */
  override def setMiss(square: String): GridOfShips = {
    updateSquare(square, "x")
  }
}