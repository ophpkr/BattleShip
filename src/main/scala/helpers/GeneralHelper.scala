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

  def putShips(game: Battle, player: Player, numPlayer: String): Battle = {
    println("Positioning " + player.name + "'s ships")
    val newPlayer = putCarrier(player)
    numPlayer match {
      case "player1" => {
        game.copy(_player1 = newPlayer)
      }
      case "player2" => {
        game.copy(_player2 = newPlayer)
      }
    }


  }

  def putCarrier(player: Player): Player = {
    println("Where do you want to put your carrier (size of 5 squares) ? ")
    val pos = StdIn.readLine()
    println(validSquare(pos))
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 5, pos)
      val carrier = Ship("carrier", 5, listOfPos.toSet, false)
      player.addShip(carrier)
      return player.copy(_shipsGrid = player.shipsGrid.addShips(listOfPos))
    }
    else {
      println("The square given doesn't exist")
      putCarrier(player)
    }
  }

  def validSquare(square: String): Boolean = {
    List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j").contains(square.head.toString) &&
    List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10").contains(square.tail)
  }

  /**
    *
    * @param player
    * @param size
    * @param pos
    * @return
    */
  def chooseOrientation(player: Player, size: Int, pos: String): List[String] = {
    println("Please choose an orientation for your ship :\n h for horizontal or v for vertical")
    val orientation = StdIn.readLine()
    orientation match {
      case "h" => {
        val listOfPos = createListOfPos("h", size, pos)
        player.shipsGrid.canOccupySquares(listOfPos) match {
          case true => {
            println("placement ok ")
            println(listOfPos.mkString(""))
            listOfPos
          }
          case _ => {
            println("impossible to put your ship here")
            chooseOrientation(player, size, pos) //TODO: Renvoyer au choix de la case
          }
        }
      }
      case "v" => {
        val listOfPos = createListOfPos("v", size, pos)
        player.shipsGrid.canOccupySquares(listOfPos) match {
          case true => {
            println("placement ok ")
            println(listOfPos.mkString(""))
            listOfPos
          }
          case _ => {
            println("impossible to put your ship here")
            chooseOrientation(player, size, pos) //TODO: Renvoyer au choix de la case
          }
        }
      }
      case _ => { println("The orientation given doesn't correspond to h or v")
        chooseOrientation(player, size, pos)
      }
    }
  }

  /**
    *
    * @param orientation
    * @param size
    * @param pos
    * @return
    */
  def createListOfPos(orientation: String, size: Int, pos: String): List[String] = {
    orientation match {
      case "h" => {
        val alphabetList = List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p","q")
        val start = alphabetList.indexOf(pos.head.toString)
        println("index of pos est " + start.toString)
        val stop = start + size
        println("stop is "+ stop.toString)
        val listOfletters = alphabetList.slice(start, stop)
        println(listOfletters.mkString(""))
        val listOfPos = listOfletters.map(x => x + pos.tail)
        println(listOfPos.mkString(""))
        listOfPos
      }
      case "v" => {
        val numberList = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
        val start = numberList.indexOf(pos.tail)
        val listOfNumbers = numberList.slice(start, start + size -1)
        val listOfPos = listOfNumbers.map(x => pos.head + x)
        listOfPos
      }
    }
  }
}