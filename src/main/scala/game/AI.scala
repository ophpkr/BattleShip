package game
import scala.io.StdIn
import scala.util.Random
import helpers.GridHelper
import game._

trait AI {

  /** A random attribute making pure functions using it */
  def random: Random
  /** Provides a random square
    *
    * @param r random of the player
    * @return a square of a battleship grid
    */
  def randomPosition(r: Random): String = {
    val letterInInt = r.nextInt(10)
    val number = r.nextInt(10) + 1
    val letter = GridHelper.intToLetter(letterInInt)
    // println("position choisie : " + letter + number.toString)
    letter + number.toString
  }

  /** Put the ships of a battleShip on an AI's ships grid
    *
    * @param player the player putting the ships
    * @return this player having put his ships
    */
  def initShips(player: Player): Player = {
    val pcarrier = initCarrier(player)
    // println(pcarrier.shipsGrid.toString)
    val pbatship = initBattleShip(pcarrier)
    // println(pbatship.shipsGrid.toString)
    val pcruiser = initCruiser(pbatship)
    // println(pcruiser.shipsGrid.toString)
    val psubmarine = initSubmarine(pcruiser)
    // println(psubmarine.shipsGrid.toString)
    val pdestroyer = initDestroyer(psubmarine)
    // println(pdestroyer.shipsGrid.toString)
    pdestroyer
  }
  /** Puts the carrier ship for a given player
    *
    * @param player the player we put the carrier for
    * @return the player with a cruiser put
    */
  def initCarrier(player: Player): Player = {
    val pos = randomPosition(this.random)
    val listOfPos = chooseOrientation(player, 5, pos)
    if (listOfPos.isEmpty) {
      initCarrier(player)
    }
    else {
      val carrier = Ship("carrier", 5, listOfPos.get.toSet)
      val p = player.addShip(carrier)
      val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
      return p.copyShipsGrid(newGridShip)
    }
  }

  /** Puts the cruiser ship for a given player
    *
    * @param player the player we put the cruiser for
    * @return the player with a cruiser put
    */
  def initCruiser(player: Player): Player = {
    val pos = randomPosition(this.random)
    val listOfPos = chooseOrientation(player, 4, pos)
    if (listOfPos.isEmpty) {
      initCruiser(player)
    }
    else {
      val cruiser = Ship("cruiser", 4, listOfPos.get.toSet)
      val p = player.addShip(cruiser)
      val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
      return p.copyShipsGrid(newGridShip)
    }
  }

  /** Puts the battleship for a given player
    *
    * @param player the player we put the battleship for
    * @return the player with a battleship put
    */
  def initBattleShip(player: Player): Player = {
    val pos = randomPosition(this.random)
    val listOfPos = chooseOrientation(player, 4, pos)
    if (listOfPos.isEmpty) {
      initBattleShip(player)
    }
    else {
      val battleShip = Ship("battleShip", 4, listOfPos.get.toSet)
      val p = player.addShip(battleShip)
      val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
      return p.copyShipsGrid(newGridShip)
    }
  }

  /** Puts the submarine ship for a given player
    *
    * @param player the player we put the submarine for
    * @return the player with a submarine put
    */
  def initSubmarine(player: Player): Player = {
    val pos = randomPosition(this.random)
    val listOfPos = chooseOrientation(player, 3, pos)
    if (listOfPos.isEmpty) {
      initSubmarine(player)
    }
    else {
      val submarine = Ship("submarine", 3, listOfPos.get.toSet)
      val p = player.addShip(submarine)
      val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
      return p.copyShipsGrid(newGridShip)
    }
  }

  /** Puts the destroyer ship for a given player
    *
    * @param player the player we put the destroyer for
    * @return the player with a destroyer put
    */
  def initDestroyer(player: Player): Player = {
    val pos = randomPosition(this.random)
    val listOfPos = chooseOrientation(player, 2, pos)
    if (listOfPos.isEmpty) {
      initDestroyer(player)
    }
    else {
      val destroyer = Ship("destroyer", 2, listOfPos.get.toSet)
      val p = player.addShip(destroyer)
      val newGridShip = p.shipsGrid.addShips(listOfPos.get, player.shipsGrid, 0)
      return p.copyShipsGrid(newGridShip)
    }
  }

  /** Generates an option list of squares for a choosen orientation of a ship
    *
    * @param player the player that chooses the orientation
    * @param size the size of the ship considered
    * @param pos the initial square the ship
    * @return a list of string corresponding of all of positions of a ship if all of the squares can be placed on the player's shipsgrid else None
    */
  def chooseOrientation(player: Player, size: Int, pos: String): Option[List[String]] = {
    val or = this.random.nextInt(2)
    val orientation = or match {
      case 0 => "h"
      case 1 => "v"
    }
    orientation match {
      case "h" => {
        val listOfPos = createListOfPos("h", size, pos)
        player.shipsGrid.canOccupySquares(listOfPos) match {
          case true => {
            Some(listOfPos)
          }
          case _ => None
        }
      }
      case "v" => {
        val listOfPos = createListOfPos("v", size, pos)
        player.shipsGrid.canOccupySquares(listOfPos) match {
          case true => Some(listOfPos)
          case _ => None
        }
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
        val stop = start + size
        val listOfletters = alphabetList.slice(start, stop)
        val listOfPos = listOfletters.map(x => x + pos.tail)
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

  /** Provides the square wished to be attacked
    *
    * @return the string corresponding to this square
    */
  def attack(): String
}