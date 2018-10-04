package game
import helpers._
/** The grid containing a player anterior hits on the oponent's grid of ships
  *
  *  @constructor Create a new grid of Attack with a name and size
  *  @param name The grid's name
  *  @param size The grid's size (in number of squares for a side)
  */
case class GridOfAttack(private val _name: String, private val _size: Int, private val _verticalLadder: List[String] = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"),
                       private val _horizontalLadder: List[String] = List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), private val _representation: List[List[String]]) extends Grid(_name, _size, _verticalLadder, _horizontalLadder, _representation) {

  override def updateSquare(square: String, symbol: String): GridOfAttack = {
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

  /** Set a square with hit representation
    * @param square The square that has to be changed by a hit
    */
  override def setHit(square: String):GridOfAttack = {
    updateSquare(square, "o")
  }

  /** Set a square with miss representation
    * @param square The square that has to be changed by a miss
    */
  override def setMiss(square: String): GridOfAttack = {
    updateSquare(square, "x")
  }
}