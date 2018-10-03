package helpers
import scala.io.StdIn
import game._

object GeneralHelper {

  /**
    *
    * @return
    */
  def initialGrid = GridHelper.addListOfDot(List(), 10)

  def askForMode: Player = {
    println("Please choose a play mode :\n - p for player VS player\n - b to play VS AI level beginner" +
    "\n - m to play VS Ai level medium\n - h to play VS AI level hard")
    val mode = StdIn.readLine()
    modeGame(mode)
  }
  /** According to the mode choosen, different actions will be executed
    *
    * @param mode
    */
  def modeGame(mode: String): Player = {
    mode match {
      case "p" => { println("You chose the mode player VS player")
        askForName("p2") }
      case "b" => {println("You chose to play against the AI level begginer")
        askForMode}
      case "m" => {println("You chose to play against the AI level medium")
        askForMode}
      case "h" => {println("You chose to play against the AI level hard")
        askForMode}
      case _ => {println("Your entry doesn't correspond to any mode")
        askForMode }
    }
  }

  /** Ask for the name of a player
    *
    */
  def askForName(player: String): Player = {
    player match {
      case "p1" => {
        println("Please enter your name")
        val name = StdIn.readLine()
        (initializePlayer(name))
      }
      case "p2" => {
        println("Please enter the name of player2")
        val name = StdIn.readLine()
        (initializePlayer(name))
      }
      case _ => {
        println("pas bon")
        askForName(player)
      }
    }
  }

  /***
    *
    * @param name
    * @return
    */
  def initializePlayer(name: String): Player = {
    println("initialisation " + name)
    val rep = initialGrid
    val g: GridOfShips = GridOfShips("gridOfShips "+name, 10, _representation = rep)
    val p = Player(name, g, Set(), 0)
    println("The player " + p.name + " has been created")
    p

  }
}