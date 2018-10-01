package game
import helpers.GridHelper

/** The grid containing a player ships' placements and oponent's hits
  *
  *  @constructor Create a new grid of Ships with a name and size
  *  @param name The grid's name
  *  @param size The grid's size (in number of squares for a side)
  */
class GridOfShips(gName: String, gSize: Int) extends Grid(gName, gSize) {

  /* Particular functions */
  /** Tell if a ship can occupy the squares given in parameter
    * @param squares Squares we want to know if they are free and exist in the grid.
    * @return Returns true if the squares are free and in the grid else false
    */
  def canOccupySquares(squares: Array[String]): Boolean = {
    squares.map(isNotOccupiedSquare).contains(false)
  }

  /** Add a ship representation on the squares given (squares have to exist and be free)
    * @param squares Squares on which we want to place a ship
    */
  def addShip(squares: Array[String]): Unit = {
        squares.foreach(square => representation_(square, "S"))
  }

  /** Get the state of a given square (miss, hit or sunk)
    * @param square The square from which we want to know the state
    * @return Returns the state of the square :
    *         "miss" if there is no ship on the square or if a ship has already been hit or sunk,
    *         "hit" if a ship has been hit for the first time on the given square,
    *         "sunk" if a ship has been hit for the first time on the given square and this hit makes it sunk
    */
  def getStateAfterHit(square: String): String = {
    val pos = GridHelper.squareToArrayPositions(square)
    representation(pos(0))(pos(1))
  }

  /** Tell if a square is already taken by a ship
    * @param square The square we want to check it is not occupied
    * @return True if the square is free, else false
    */
  def isNotOccupiedSquare(square: String): Boolean = {
    val pos = GridHelper.squareToArrayPositions(square)
    representation(pos(0))(pos(1)) == "."
  }
}