package game
import scala.io.StdIn

object MainGame {
  def main(args: Array[String]): Unit = {
    println("READY FOR A BATTLESHIP GAME ?")
    val init = initializeGame(10)
    var p1 = init(0)
    var p2 = init(1)
    println(p1.name)
    println(p2.name)

  }

  def initializeGame(gSize: Int): Array[Player] = {
    // Initialisation of grids
    var gsp1 = new GridOfShips("gsp1", gSize)
    var gsp2 = new GridOfShips("gsp2", gSize)

    // Initialisation of ships
    var s1p1 = new Ship("carrier1", 5)
    var s2p1 = new Ship("battleship1", 4)
    var s3p1 = new Ship("cruiser1", 3)
    var s4p1 = new Ship("submarine1", 3)
    var s5p1 = new Ship("destroyer1", 2)
    var s1p2 = new Ship("carrier2", 5)
    var s2p2 = new Ship("battleship2", 4)
    var s3p2 = new Ship("cruiser2", 3)
    var s4p2 = new Ship("submarine2", 3)
    var s5p2 = new Ship("destroyer2", 2)

    // initialisation of players
    var p1 = new Player("player1", gsp1)
    var p2 = new Player("player2", gsp2)
    Array(p1, p2)
  }
}

