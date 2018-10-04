package helpers
import game._

import scala.io.StdIn

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
    val ga: GridOfAttack = GridOfAttack("gridOfAttack "+name, 10, _representation = rep)
    val p = Player(name, g, ga, Set(), 0)
    println("The player " + p.name + " has been created")
    p
  }

  /**
    *
    * @param game
    * @param player
    * @param numPlayer
    * @return
    */
  def putShips(game: Battle, player: Player, numPlayer: String): Battle = {
    println("Positioning " + player.name + "'s ships")
    /*val pcarrier = putCarrier(player)
    println(pcarrier.shipsGrid.toString)
    val pbatship = putBattleShip(pcarrier)
    println(pbatship.shipsGrid.toString)
    val pcruiser = putCruiser(pbatship)
    println(pcruiser.shipsGrid.toString)
    val psubmarine = putSubmarine(pcruiser)
    println(psubmarine.shipsGrid.toString)
    val pdestroyer = putDestroyer(psubmarine)
    println(pdestroyer.shipsGrid.toString)*/
    val pdestroyer = putDestroyer(player) //TODO: Let the code in comment
    println(pdestroyer.shipsGrid.toString)
    numPlayer match {
      case "player1" => {
        val battle = game.copy(_player1 = pdestroyer)
        putShips(battle, battle.player2, "player2")
      }
      case "player2" => {
        game.copy(_player2 = pdestroyer)
      }
    }


  }

  /**
    *
    * @param player
    * @return
    */
  def putCarrier(player: Player): Player = {
    println("Where do you want to put your carrier (size of 5 squares) ? ")
    val pos = StdIn.readLine()
    println(validSquare(pos))
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 5, pos)
      if (listOfPos.isEmpty) {
        putCarrier(player)
      }
      else {
        val carrier = Ship("carrier", 5, listOfPos.get.toSet, false)
        val p = player.addShip(carrier)
        val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
        return p.copy(_shipsGrid = newGridShip)
      }
    }
    else {
      println("The square given doesn't exist")
      putCarrier(player)
    }
  }

  /**
    *
    * @param player
    * @return
    */
  def putBattleShip(player: Player): Player = {
    println("Where do you want to put your battleShip (size of 4 squares) ? ")
    val pos = StdIn.readLine()
    println(validSquare(pos))
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 4, pos)
      if (listOfPos.isEmpty) {
        putBattleShip(player)
      }
      else {
        val battleShip = Ship("battleShip", 5, listOfPos.get.toSet, false)
        val p = player.addShip(battleShip)
        val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
        return p.copy(_shipsGrid = newGridShip)
      }
    }
    else {
      println("The square given doesn't exist")
      putBattleShip(player)
    }
  }

  /**
    *
    * @param player
    * @return
    */
  def putSubmarine(player: Player): Player = {
    println("Where do you want to put your submarine (size of 3 squares) ? ")
    val pos = StdIn.readLine()
    println(validSquare(pos))
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 3, pos)
      if (listOfPos.isEmpty) {
        putSubmarine(player)
      }
      else {
        val submarine = Ship("submarine", 3, listOfPos.get.toSet, false)
        val p = player.addShip(submarine)
        val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
        return p.copy(_shipsGrid = newGridShip)
      }
    }
    else {
      println("The square given doesn't exist")
      putSubmarine(player)
    }
  }

  /**
    *
    * @param player
    * @return
    */
  def putDestroyer(player: Player): Player = {
    println("Where do you want to put your destroyer (size of 2 squares) ? ")
    val pos = StdIn.readLine()
    println(validSquare(pos))
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 2, pos)
      if (listOfPos.isEmpty) {
        putDestroyer(player)
      }
      else {
        val destroyeur = Ship("destroyeur", 5, listOfPos.get.toSet, false)
        val p = player.addShip(destroyeur)
        val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
        return p.copy(_shipsGrid = newGridShip)
      }
    }
    else {
      println("The square given doesn't exist")
      putDestroyer(player)
    }
  }

  /**
    *
    * @param player
    * @return
    */
  def putCruiser(player: Player): Player = {
    println("Where do you want to put your cruiser (size of 4 squares) ? ")
    val pos = StdIn.readLine()
    println(validSquare(pos))
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 4, pos)
      if (listOfPos.isEmpty) {
        putCruiser(player)
      }
      else {
        val cruiser = Ship("cruiser", 5, listOfPos.get.toSet, false)
        val p = player.addShip(cruiser)
        val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
        return p.copy(_shipsGrid = newGridShip)
      }
    }
    else {
      println("The square given doesn't exist")
      putCruiser(player)
    }
  }

  /**
    *
    * @param square
    * @return
    */
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
  def chooseOrientation(player: Player, size: Int, pos: String): Option[List[String]] = {
    println("Please choose an orientation for your ship :\n h for horizontal or v for vertical")
    val orientation = StdIn.readLine()
    orientation match {
      case "h" => {
        val listOfPos = createListOfPos("h", size, pos)
        player.shipsGrid.canOccupySquares(listOfPos) match {
          case true => {
            println("placement ok ")
            println(listOfPos.mkString(""))
            Some(listOfPos)
          }
          case _ => {
            println("impossible to put your ship here")
            None
          }
        }
      }
      case "v" => {
        val listOfPos = createListOfPos("v", size, pos)
        player.shipsGrid.canOccupySquares(listOfPos) match {
          case true => {
            println("placement ok ")
            println(listOfPos.mkString(""))
            Some(listOfPos)
          }
          case _ => {
            println("impossible to put your ship here")
            //chooseOrientation(player, size, pos, kindOfBoat) //TODO: Renvoyer au choix de la case
            None
          }
        }
      }
      case _ => { println("The orientation given doesn't correspond to h or v")
        // chooseOrientation(player, size, pos, kindOfBoat)
        None
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
        val listOfNumbers = numberList.slice(start, start + size)
        val listOfPos = listOfNumbers.map(x => pos.head + x)
        listOfPos
      }
    }
  }
}