package helpers
import scala.io.StdIn
import scala.util.Random
import game._
import main.scala.elements.{Battle, GridOfAttack, GridOfShips, Ship}
import main.scala.players._

/** The manager of the preparation of a battle */
object GeneralHelper {

  /** Initializes a grid of dots of size of 10
    *
    * @return a list of list of dots having a size of 10
    */
  def initialGrid = GridHelper.addListOfDot(List(), 10)

  /** Asks the player the game mode he wishes
    *
    * @return a player better defined in the modeGame function
    */
  def askForMode: Player = {
    println("Please choose a play mode :\n - p for player VS player\n - b to play VS AI level beginner" +
      "\n - m to play VS Ai level medium\n - h to play VS AI level hard")
    val mode = StdIn.readLine().toLowerCase()
    modeGame(mode)
  }
  /** According to the mode choosen, different actions will be executed
    *
    * @param mode the mode choosen
    * @return a player better defined in the askForName function
    */
  def modeGame(mode: String): Player = {
    mode match {
      case "p" => { println("You chose the mode player VS player")
        askForName("p2") }
      case "b" => {println("You chose to play against the AI level begginer")
        initializePlayer("AI beginner", 0, 1)}
      case "m" => {println("You chose to play against the AI level medium")
        initializePlayer("AI medium", 0, 2)}
      case "h" => {println("You chose to play against the AI level hard")
        initializePlayer("AI hard", 0, 3)}
      case _ => {println("Your entry doesn't correspond to any mode")
        askForMode }
    }
  }

  /** Asks the player his name
    *
    * @param player the "number" he is for a game ("p1" for player1, "p2" for player 2), it has to be in ["p1", "p2"]
    * @return a player better described in initializePlayer function
    */
  def askForName(player: String): Player = {
    player match {
      case "p1" => {
        println("Please enter your name")
        val name = StdIn.readLine().toLowerCase()
        (initializePlayer(name, 0, 0))
      }
      case "p2" => {
        println("Please enter the name of player2")
        val name = StdIn.readLine().toLowerCase()
        (initializePlayer(name, 0, 0))
      }
    }
  }

  /** Generates a player according to its parameters
    *
    * @param name the name of the player
    * @param score the score of the player
    * @param level the level of the player
    * @return a player generates according to the given parameters
    */
  def initializePlayer(name: String, score:Int, level: Int): Player = {
    val rep = initialGrid
    val g: GridOfShips = GridOfShips("gridOfShips "+name, 10, _representation = rep)
    val ga: GridOfAttack = GridOfAttack("gridOfAttack "+name, 10, _representation = rep)
    val p = level match {
      case 0 => HumanPlayer(name, g, ga, Set(), 0)
      case 1 => {
        val r = Random
        AI1(name, g, ga, Set(), 0, r)
      }
      case 2 => {
        val r = Random
        AI2(name, g, ga, Set(), 0, r)
      }
      case 3 => {
        val r = Random
        AI3(name, g, ga, Set(), 0, r, initMapAI3(Map(), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")), "", Set())
      }
    }
    printer(p.creationSpeak)
    p
  }


  /** Creates a battle once both of the players have put their ships
    *
    * @param game the game for which players create ships
    * @param player the player we put ships for
    * @param numPlayer the player corresponding to a player having blank grids (attack and ships) and the given name and score
    * @return the battle having the two players' ships put
    */
  def putShips(game: Battle, player: Player, numPlayer: String): Battle = {
    println("Positioning " + player.name + "'s ships")//TODO: speaker
    val pdestroyer = player match {
      case HumanPlayer(_, _, _, _, _) => {
        // creation of the five different ships for the battle
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
        this.putDestroyer(player)
        // println(pdestroyer.shipsGrid.toString)
      }
      case AI1(_, _, _, _, _, _) | AI2(_, _, _, _, _, _, _) | AI3(_, _, _, _, _, _, _, _, _) => {
        player.asInstanceOf[AI].initShips(player)
      }
    }
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

  /** Puts the carrier ship for a given player
    *
    * @param player the player we put the carrier for
    * @return the player with a cruiser put
    */
  def putCarrier(player: Player): Player = {
    println("Where do you want to put your carrier (size of 5 squares) ? ")
    val pos = StdIn.readLine().toLowerCase()
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 5, pos)
      if (listOfPos.isEmpty) {
        putCarrier(player)
      }
      else {
        val carrier = Ship("carrier", 5, listOfPos.get.toSet)
        val p = player.addShip(carrier)
        val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
        return p.copyShipsGrid(newGridShip)
      }
    }
    else {
      println("The square given doesn't exist")
      putCarrier(player)
    }
  }

  /** Puts the cruiser ship for a given player
    *
    * @param player the player we put the cruiser for
    * @return the player with a cruiser put
    */
  def putCruiser(player: Player): Player = {
    println("Where do you want to put your cruiser (size of 4 squares) ? ")
    val pos = StdIn.readLine().toLowerCase()
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 4, pos)
      if (listOfPos.isEmpty) {
        putCruiser(player)
      }
      else {
        val cruiser = Ship("cruiser", 4, listOfPos.get.toSet)
        val p = player.addShip(cruiser)
        val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
        return p.copyShipsGrid(newGridShip)
      }
    }
    else {
      println("The square given doesn't exist")
      putCruiser(player)
    }
  }

  /** Puts the battleship for a given player
    *
    * @param player the player we put the battleship for
    * @return the player with a battleship put
    */
  def putBattleShip(player: Player): Player = {
    println("Where do you want to put your battleShip (size of 4 squares) ? ")
    val pos = StdIn.readLine().toLowerCase()
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 4, pos)
      if (listOfPos.isEmpty) {
        putBattleShip(player)
      }
      else {
        val battleShip = Ship("battleShip", 5, listOfPos.get.toSet)
        val p = player.addShip(battleShip)
        val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
        return p.copyShipsGrid(newGridShip)
      }
    }
    else {
      println("The square given doesn't exist")
      putBattleShip(player)
    }
  }

  /** Puts the submarine ship for a given player
    *
    * @param player the player we put the submarine for
    * @return the player with a submarine put
    */
  def putSubmarine(player: Player): Player = {
    println("Where do you want to put your submarine (size of 3 squares) ? ")
    val pos = StdIn.readLine().toLowerCase()
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 3, pos)
      if (listOfPos.isEmpty) {
        putSubmarine(player)
      }
      else {
        val submarine = Ship("submarine", 3, listOfPos.get.toSet)
        val p = player.addShip(submarine)
        val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
        return p.copyShipsGrid(newGridShip)
      }
    }
    else {
      println("The square given doesn't exist")
      putSubmarine(player)
    }
  }

  /** Puts the destroyer ship for a given player
    *
    * @param player the player we put the destroyer for
    * @return the player with a destroyer put
    */
  def putDestroyer(player: Player): Player = {
    println("Where do you want to put your destroyer (size of 2 squares) ? ")
    val pos = StdIn.readLine().toLowerCase()
    if (validSquare(pos)) {
      val listOfPos = chooseOrientation(player, 2, pos)
      if (listOfPos.isEmpty) {
        putDestroyer(player)
      }
      else {
        val destroyer = Ship("destroyer", 2, listOfPos.get.toSet)
        val p = player.addShip(destroyer)
        val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
        return p.copyShipsGrid(newGridShip)
      }
    }
    else {
      println("The square given doesn't exist")
      putDestroyer(player)
    }
  }

  /** Validates the existence of a square for a grid's size of 10
    *
    * @param square the square we want to know if it exists
    * @return true if the square exists else false
    */
  def validSquare(square: String): Boolean = {
    List("a", "b", "c", "d", "e", "f", "g", "h", "i", "j").contains(square.head.toString) &&
      List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10").contains(square.tail)
  }

  /** Generates an option list of squares for a choosen orientation of a ship
    *
    * @param player the player that chooses the orientation
    * @param size the size of the ship considered
    * @param pos the initial square the ship
    * @return a list of string corresponding of all of positions of a ship if all of the squares can be placed on the player's shipsgrid else None
    */
  def chooseOrientation(player: Player, size: Int, pos: String): Option[List[String]] = {
    println("Please choose an orientation for your ship :\n h for horizontal or v for vertical")
    val orientation = StdIn.readLine().toLowerCase()
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

  /** Generates a list of squares corresponding to the given parameters
    *
    * @param orientation the orientation of the ship wanted
    * @param size the size of the ship wanted so the size of the list of squares to return
    * @param pos the initial position for the ship
    * @return the list of squares corresponding to the placement wanted
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

  /** Prints a speak
    *
    * @param speak the speak wanted to be printed
    *              if it is at None, nothing is printed
    */
  def printer(speak: Option[String]): Unit = {
    if(!speak.isEmpty) {
      println(speak.get)
    }
  }

  /** Generates a player according to its type
    *
    * @param typeAI the type wanted
    * @return a player of the given type initialized
    */
  def preparePlayer(typeAI: String): Player = {
    val rep = initialGrid
    typeAI match {
      case "ai1" => {
        val g: GridOfShips = GridOfShips("gridOfShips AI beginer", 10, _representation = rep)
        val ga: GridOfAttack = GridOfAttack("gridOfAttack AI begginer", 10, _representation = rep)
        val r = Random
        AI1("AI begginer", g, ga, Set(), 0, r)
      }
      case "ai2" => {
        val r = Random
        val g: GridOfShips = GridOfShips("gridOfShips AI medium", 10, _representation = rep)
        val ga: GridOfAttack = GridOfAttack("gridOfAttack AI medium", 10, _representation = rep)
        AI2("AI medium", g, ga, Set(), 0, r, Set())
      }
      case "ai3" => {
        println("case ai3")
        val r = Random
        val g: GridOfShips = GridOfShips("gridOfShips AI hard", 10, _representation = rep)
        val ga: GridOfAttack = GridOfAttack("gridOfAttack AI hard", 10, _representation = rep)
        AI3("AI hard", g, ga, Set(), 0, r, this.initMapAI3(Map[String, String](), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")), "", Set())
      }
      case _ => {
        println("pb")
        askType
      }
    }
  }

  /** Asks the type of player (AI) wanted
    *
    * @return the player initialized according to his type choosen
    */
  def askType(): Player = {
    val typeAI = StdIn.readLine().toLowerCase
    preparePlayer(typeAI)
  }

  /** Inits an blank AI3's map
    *
    * @param map the current map where squares are already associated to "0" value
    * @param letters the list of letters of the horizontal ladder of a battleship
    * @param numbers the list of numbers of the vertical ladder of a battleship
    * @return a map with all squares existing in a grid of battleship associated to the value "0"
    */
  def initMapAI3(map: Map[String, String], letters: List[String], numbers: List[String]): Map[String, String] = {
    //println("initMapAI3")
    //println(BattleHelper.loop.toString)
    // all positions have been created
    if (letters.isEmpty) map
      else {
      // the entire colomn has been created
      if (numbers.isEmpty) {
        val nletters = letters.drop(1)
        val nnumbers = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        initMapAI3(map, nletters, nnumbers)
      }
      else {
        // add a new key value
        println(letters.apply(0).toLowerCase() + numbers.apply(0))
        val nmap = map + (letters.apply(0).toLowerCase() + numbers.apply(0) -> "0")
        val nnumbers = numbers.drop(1)
        println(nnumbers.mkString(""))
        initMapAI3(nmap, letters, nnumbers)
      }
    }
  }
}