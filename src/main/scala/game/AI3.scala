package game
import scala.annotation.tailrec
import scala.util.Random
import helpers._

/** An AI3 for a battleship game
  *
  * @param _name the name of the AI3
  * @param _shipsGrid the grid of ships of the AI3
  * @param _attackGrid the attack grid of the AI3
  * @param _ships the collection of ships of the AI3
  * @param _score the score of the AI3
  * @param _random the random value of the AI3
  * @param _positionToMentalMap the map of positions of the AI3 (for each square, a "probability" value is associated)
  * @param _currentFirstSquareHit the first hit happening until a sunk occures
  * @param _alreadyHitSquares the set of squares already hit (= handled)
  */
case class AI3(private val _name: String, private val _shipsGrid: GridOfShips, private val _attackGrid: GridOfAttack,
               private val _ships: Set[Ship], private val _score: Int = 0, private val _random: Random,
               private val _positionToMentalMap: Map[String, String], private val _currentFirstSquareHit: String,
               private val _alreadyHitSquares: Set[String] ) extends Player(_name, _shipsGrid, _attackGrid, _ships, _score) with AI {

  /* getters */

  def positionToMentalMap = _positionToMentalMap
  def alreadyHitSquares = _alreadyHitSquares
  def currentFirstSquareHit = _currentFirstSquareHit

  /* overrides */

  override def random = _random

  /** A copy of this AI3 with new ships
    *
    * @param newShips the new collection of ships of the player
    * @return A new AI3 consisting of its elements except the ships changed onto the given one
    */
  override def copyShips(newShips: Set[Ship]): AI3 = { this.copy(_ships = newShips) }

  /** A copy of this AI3 with new ships and a new grid of ships
    *
    * @param newGridOfShips the new grid of ships
    * @param newShips       the new collection of ships
    * @return A new AI3 consisting of its elements except the ships changed onto the given one and the grid of ships changed onto the given one
    */
  override def copyGridShipsAndShips(newGridOfShips: GridOfShips, newShips: Set[Ship]): AI3 = { this.copy(_shipsGrid = newGridOfShips, _ships = newShips) }

  /** A copy of this AI3 with a new grid of attack
    *
    * @param newAttackGrid the new grid of attack
    * @return A new AI3 consisting of its elements except the grid of attack changed onto the given one
    */
  override def copyAttackGrid(newAttackGrid: GridOfAttack): AI3 = { this.copy(_attackGrid = newAttackGrid) }

  /** A copy of this AI3 with a new grid of ships
    *
    * @param newGridOfShips the new grid of ships
    * @return A new AI3 consisting of its elements except the grid of ships changed onto the given one
    */
  override def copyShipsGrid(newGridOfShips: GridOfShips): AI3 = { this.copy(_shipsGrid = newGridOfShips) }

  /** A copy of this AI3 with a new grid of ships
    *
    * @param newScore the new score
    * @return A new AI3 consisting of its elements except the score changed onto the given one
    */
  override def copyScore(newScore: Int): AI3 = { this.copy(_score = newScore) }

  /** A copy of this AI3 with new grids and ships
    *
    * @param gs the new grid of ships
    * @param ga the new grid of attack
    * @param ships the new collection of ships
    * @return A new AI3 consisting of its elements except the grid of attack, grid of ships and ships changed onto the given
    */
  override def copyGridsAndShips(gs: GridOfShips, ga: GridOfAttack, ships: Set[Ship]): AI3 = { this.copy(_shipsGrid = gs, _attackGrid = ga, _ships = ships) }

  /** Provides the square wished to be attacked
    *
    * @return the string corresponding to this square
    */
  override def attack(): String = {
    val higherProbability = this.positionToMentalMap.filter((x) => x._2 == "3").keySet
    val mediumProbility = this.positionToMentalMap.filter((x) => x._2 == "2").keySet
    val simpleProbability = this.positionToMentalMap.filter((x) => x._2 == "1").keySet

    // every squares are at "0" or <0 values but there is at least one square with the value "0"
    if (higherProbability.isEmpty && mediumProbility.isEmpty && simpleProbability.isEmpty) {
      val pos = randomPosition(this.random)
      if (alreadyHitSquares.apply(pos)) this.attack()
      else pos
    }
    else {
      // there is at least one square with the "1" value. 1 being the maximum value
      if (higherProbability.isEmpty && mediumProbility.isEmpty) {
        val randint = this.random.nextInt(simpleProbability.size)
        simpleProbability.toList.apply(randint)
      }
      else {
        // there is at least one square with the "2" value. 2 being the maximum value
        if (higherProbability.isEmpty) {
          val randint = this.random.nextInt(mediumProbility.size)
          mediumProbility.toList.apply(randint)
        }
        // there is at least one square with the "3" value. 3 being the maximum value
        else {
          val randint = this.random.nextInt(higherProbability.size)
          higherProbability.toList.apply(randint)
        }
      }
    }
  }

  /* own functions */

  /** A copy of this AI3 with a "-4" value for the given square (key of position to mental map)
    *
    * @param square the square to update in the mental map
    *               The square has to exist as a key of the mental map
    * @return A new AI3 constituing of the AI3 elements except the mental map "updated" with a "-4" value for the given key (square)
    */
  private def setSunk(square: String): AI3 = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square -> "-4"))
  }

  /** A copy of this AI3 with a "-3" value for the given square (key of position to mental map)
    *
    * @param square the square to update in the mental map
    *               The square has to exist as a key of the mental map
    * @return A new AI3 constituing of the AI3 elements except the mental map "updated" with a "-3" value for the given key (square)
    */
  private def setExtremity(square: String): AI3 = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square -> "-3"))
  }

  /** A copy of this AI3 with a "-2" value for the given square (key of position to mental map)
    *
    * @param square the square to update in the mental map
    *               The square has to exist as a key of the mental map
    * @return A new AI3 constituing of the AI3 elements except the mental map "updated" with a "-2" value for the given key (square)
    */
  private def setHit(square: String): AI3 = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square -> "-2"))
  }

  /** A copy of this AI3 with a "-1" value for the given square (key of position to mental map)
    *
    * @param square the square to update in the mental map
    *               The square has to exist as a key of the mental map
    * @return A new AI3 constituing of the AI3 elements except the mental map "updated" with a "-1" value for the given key (square)
    */
  private def setMiss(square: String): AI3 = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square -> "-1"))
  }

  /** A copy of this AI3 with a "3" value for the given square (key of position to mental map)
    *
    * @param square the square to update in the mental map
    *               The square has to exist as a key of the mental map
    * @return A new AI3 constituing of the AI3 elements except the mental map "updated" with a "3" value for the given key (square)
    */
  private def setVeryHighProbability(square: String) = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square -> "3"))
  }

  /** A copy of this AI3 with a "2" value for the given square (key of position to mental map)
    *
    * @param square the square to update in the mental map
    *               The square has to exist as a key of the mental map
    * @return A new AI3 constituing of the AI3 elements except the mental map "updated" with a "2" value for the given key (square)
    */
  private def setHighProbability(square: String) = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square -> "2"))
  }

  /** A copy of this AI3 with a "1" value for the given square (key of position to mental map)
    *
    * @param square the square to update in the mental map
    *               The square has to exist as a key of the mental map
    * @return A new AI3 constituing of the AI3 elements except the mental map "updated" with a "1" value for the given key (square)
    */
  private def setNormalProbability(square: String) = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square -> "1"))
  }

  /** A copy of this AI3 with a "0" value for the given square (key of position to mental map)
    *
    * @param square the square to update in the mental map
    *               The square has to exist as a key of the mental map
    * @return A new AI3 constituing of the AI3 elements except the mental map "updated" with a "0" value for the given key (square)
    */
  private def setNeutral(square: String) = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square -> "0"))
  }

  /** A copy of this AI3 with a new current first square
    *
    * @param square the square to update in the mental map
    *               The square has to exist as a key of the mental map
    * @return A new AI3 constituing of the AI3 elements except the curFirstSquare changed onto the given one
    */
  def  setCurFirstSquare(square: String): AI3 = {
    this.copy(_currentFirstSquareHit = square)
  }

  /** A copy of this AI3 with a new collection of already hit squares
    *
    * @param set the new set
    * @return A new AI3 constituing of the AI3 elements except the set of already hit squares changed onto the given one
    */
  def setAlreadyHitSquares(set: Set[String]): AI3 = {
    this.copy(_alreadyHitSquares = set)
  }

  /** A copy of this AI3 with a new map of squares -> values
    *
    * @param map the new map
    * @return A new AI3 constituing of the AI3 elements except the map changed onto the given one
    */
  def setPositionToMentalMap(map: Map[String, String]): AI3 = {
    this.copy(_positionToMentalMap = map)
  }

  /** Manages a miss case for a square
    *
    * @param square the square missed
    *               the square has to exist as a key in the mental map
    * @return A new AI3 after a miss on the given square
    */
  def manageMiss(square: String): AI3 = {
    setMiss(square)
  }

  /** Manages a hit case for a square
    *
    * @param square the square missed
    *               the square has to exist as a key in the mental map
    * @return A new AI3 after a hit on the given square
    */
  def manageHit(square: String): AI3 = {
    positionToMentalMap.apply(square) match {
      // randomly found a ship
      case "0" => {
        val nai = setHit(square)
        val naibis = nai.setCurFirstSquare(square)
        naibis.giveSameProbabilityAround(square)
      }
      // knew there were lot of probability to hit a ship
      case "3" => {
        val nai = setHit(square)
        nai.giveMoreProbabilityForSquaresInSameDirection(square)
      }//TODO: be careful if the is more than 5
      case "2" => {
        val nai = setHit(square)
        val naibis = nai.giveMoreProbilityForExtremitySquares(square)
        naibis.giveNormalProbabilityForSidesSquares(square)
      }
      case _  => setHit(square)
    }
  }

  /** Manages a sunk case for a square
    *
    * @param square the square that triggered the sunk
    *               the square has to exist as a key in the mental map
    * @return A new AI3 after a sunk triggered by the given square
    */
  def manageSunk(square: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions(square)
    val currFirstSquareInInt = GridHelper.squareToListPositions(currentFirstSquareHit)
    // if a ship put on a column is supposed to be sunk (so the letter is the same)
    if (squareJustHitInInt.apply(0) == currFirstSquareInInt.apply(0)) {
      val line = createSetLine(Set(), List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"), square.head.toString) // the set of all of the squares of the given column
      val setShip = line.filter(x => positionToMentalMap.apply(x) == "-2") // the set of the ship supposed to be sunk
      changeAfterSunk(setShip.toList, 0)
    }
    else {
      val column = createSetColumn(Set(), List("A", "B", "C", "D", "E", "F", "G", "H", "I", "J"), square.tail) // the set of all of the squares of the given line
      val setShip = column.filter(x => positionToMentalMap.apply(x) == "-2") // the set of the ship supposed to be sunk
      changeAfterSunk(setShip.toList, 0)
    }
  }

  /** Creates the set of all of the squares of a column
    *
    * @param s the current set of squares of the column
    * @param numbers the set of numbers corresponding of numbers of a vertical ladder
    * @param column the letter corresponding to the column wanted
    * @return a Set of all squares of a column
    */
  @tailrec
  private def createSetColumn(s: Set[String], numbers: List[String], column: String): Set[String] = {
    if (numbers.isEmpty) s
    else {
      val square = column + numbers.apply(0)
      createSetColumn(s + square, numbers.drop(1), column)
    }
  }

  /** Creates the set of all of the squares of a lign
    *
    * @param s the current set of squares of the lign
    * @param numbers the set of letters corresponding of letters of an horizontal ladder
    * @param lign the letter corresponding to the lign wanted
    * @return a Set of all squares of a column
    */
  @tailrec
  private def createSetLine(s: Set[String], letters: List[String], line: String): Set[String] = {
    if (letters.isEmpty) s
    else {
      val square = letters.apply(0) + line
      createSetLine(s + square, letters.drop(1), line)
    }
  }

  /** Manages the values of the squares given after a sunk
    *
    * @param squares the list of squares to handle
    * @param pos the position of the square to handle in the list
    * @return A new AI3 consisting of elements of the AI3 except that the squares of the given list have been handled
    */
  @tailrec
  private def changeAfterSunk(squares: List[String], pos: Int): AI3 = {
    if(pos >= squares.size) this
    else {
      // extremity treatment
      if (pos == 0 || pos == squares.size - 1) {
        val ai = setExtremity(squares.apply(pos))
        ai.changeAfterSunk(squares, pos + 1)
      }
      // middle of the ship considered treatment
      else {
        var ai = setSunk(squares.apply(pos))
        changeAfterSunk(squares, pos + 1)
      }
    }
  }

  /** Changes the probabilities of squares arround a square to "2" when it is possible
    *
    * @param square the central square
    *               the square has to exist in the mental map as a key
    * @return a new AI3 with the values corresponding to the squares "updated" at "2" when it is possible
    * @usecase a hit has been done on the given square whereas it wasn't especially supposed
    * @example For the given square is b1
    *          the value of the keys a1, c1 and b2 will be passed to "2" unless they are already at "-4", "-3", "-2" or "-1"
    */
  private def giveSameProbabilityAround(square: String): AI3 = {
    val squareInIntList = GridHelper.squareToListPositions(square) // the list of position in int corresponding to the square
    val listSquaresAroundInInt = List(List(squareInIntList(0)-1, squareInIntList(1)), List(squareInIntList(0) + 1, squareInIntList(1)),
      List(squareInIntList(0), squareInIntList(1) - 1), List(squareInIntList(0), squareInIntList(1) + 1)) //The list of all squares position in int around the initial square
    val validIntPositions = listSquaresAroundInInt.filter(x => x(0)>= 0 && x(0)<10 && x(1)>=0 && x(1)<10) // List of all positions existing in a battleship grid
    val squaresRetained = validIntPositions.map(x => GridHelper.intToLetter(x(0)) + (x(1)+1).toString) // The list of the squares retained in letter (written like "a1")
    val squaresToModify = squaresRetained.filter(x => positionToMentalMap.apply(x).toInt >= 0) // Only squares having not been hit or sunk have to be modified
    val nmap = updateMap(positionToMentalMap, squaresToModify, "2")
    this.copy(_positionToMentalMap = nmap)
  }

  /** Changes the probability of squares in same direction to "3"
    *
    * @param square the central square
    *               the square has to exist in the mental map as a key
    * @return a new AI3 with the values corresponding to the squares "updated" at "3" when it is possible
    * @usecase a hit has been done on the given square and was supposed
    */
  private def giveMoreProbabilityForSquaresInSameDirection(square: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions(square)
    val currFirstSquareInInt = GridHelper.squareToListPositions(currentFirstSquareHit)
    // squares on the same column
    if (squareJustHitInInt.apply(0) == currFirstSquareInInt.apply(0)) {
      // the just hit square is higher than the first one
      if (squareJustHitInInt.apply(1) < currFirstSquareInInt.apply(1)) {
        setHigherSquare(square, "3")
      }
      // the just hit square is lower than the first one
      else {
        setLowerSquare(square, "3")
      }
    }
    // squares on the same line
    else {
      // the just hit square is to the left of the first one
      if (squareJustHitInInt.apply(0) < currFirstSquareInInt.apply(0)) {
        setLeftSquare(square, "3")
      }
      // the just hit square is to the right of the first one
      else {
        setRightSquare(square, "3")
      }
    }
  }

  /** Changes the value of extremities to "-3"
    *
    * @param square the square that triggers the change
    *               the square has to exist as a key in the mental map
    * @return a new AI3 with extremities "changed" to "-3"
    * @usecase a sunk has been triggered after a hit on the given square
    *
    */
  private def giveMoreProbilityForExtremitySquares(square: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions(square)
    val currFirstSquareInInt = GridHelper.squareToListPositions(currentFirstSquareHit)

    // squares on the same column
    if (squareJustHitInInt.apply(0) == currFirstSquareInInt.apply(0)) {
      // the current square is lower than the hit one
      if (squareJustHitInInt.apply(1) > currFirstSquareInInt.apply(1)) {
        val nai = setHigherSquare(currentFirstSquareHit, "3")
        nai.setLowerSquare(square, "3")
      }
      else {
        val nai = setHigherSquare(square, "3")
        nai.setLowerSquare(currentFirstSquareHit, "3")
      }
    }
    // squares on the same line
    else {
      // the current square is at the right of the hit one
      if (squareJustHitInInt.apply(0) > currFirstSquareInInt.apply(0)) {
        val nai = setLeftSquare(currentFirstSquareHit, "3")
        nai.setRightSquare(square, "3")
      }
      else {
        val nai = setLeftSquare(square, "3")
        nai.setRightSquare(currentFirstSquareHit, "3")
      }
    }
  }

  /** Changes the value of side squares to "1"
    *
    * @param square the square that triggers the change
    *               the square has to exist as a key in the mental map
    * @return a new AI3 with side squares's values of the given one "changed" to "1"
    */
  private def giveNormalProbabilityForSidesSquares(square: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions(square)
    val currFirstSquareInInt = GridHelper.squareToListPositions(currentFirstSquareHit)

    // squares on the same column
    if (squareJustHitInInt.apply(0) == currFirstSquareInInt.apply(0)) {
      // we change probability of the perpendicular squares to the hitSquare-CurrentSquare direction
      val nai = setLeftSquare(square, "1")
      nai.setRightSquare(square, "1")
    }
    else
    {
      // we change probability of the perpendicular squares to the hitSquare-CurrentSquare direction
      val nai = setHigherSquare(square, "1")
      nai.setLowerSquare(square, "1")
    }
  }

  /** Gives the wanted level of probability of the square above another
    *
    * @param square the square that is the reference for the change
    *               the square has to exist as a key in the mental map
    * @param level the level of the probility to give to the square above the given one
    * @return A new AI3 with the probability of the square above the given one "changed" according to the level
    */
  private def setHigherSquare(square: String, level: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions (square)
    val currFirstSquareInInt = GridHelper.squareToListPositions (currentFirstSquareHit)
    if (squareJustHitInInt.apply(1) - 1 < 0) this //out of the grid
    else {
      val potentialSquare = GridHelper.intToLetter(squareJustHitInInt.apply (0) ) + (squareJustHitInInt.apply(1) + 1 - 1).toString // the square given such as "a1"
      if (positionToMentalMap.apply(potentialSquare).toInt < 0) this // The square wanted has already been processed
      else {
        level match {
          case "3" => setVeryHighProbability(potentialSquare)
          case "2" => setHighProbability(potentialSquare)
          case "1" => setNormalProbability(potentialSquare)
          case _ => setNeutral(potentialSquare)
        }
      }
    }
  }

  /** Gives the wanted level of probability of the square under another
    *
    * @param square the square that is the reference for the change
    *               the square has to exist as a key in the mental map
    * @param level the level of the probility to give to the square under the given one
    * @return A new AI3 with the probability of the square under the given one "changed" according to the level
    */
  private def setLowerSquare(square: String, level: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions (square)
    val currFirstSquareInInt = GridHelper.squareToListPositions (currentFirstSquareHit)
    if (squareJustHitInInt.apply(1) + 1 > 9) this //out of the grid
    else {
      val potentialSquare = GridHelper.intToLetter(squareJustHitInInt.apply (0) ) + (squareJustHitInInt.apply(1) + 1 + 1).toString // the square given such as "a1"
      if (positionToMentalMap.apply(potentialSquare).toInt < 0) this // The square wanted has already been processed
      else {
        level match {
          case "3" => setVeryHighProbability(potentialSquare)
          case "2" => setHighProbability(potentialSquare)
          case "1" => setNormalProbability(potentialSquare)
          case _ => setNeutral(potentialSquare)
        }
      }
    }
  }

  /** Gives the wanted level of probability of the square at the left of another
    *
    * @param square the square that is the reference for the change
    *               the square has to exist as a key in the mental map
    * @param level the level of the probility to give to the square at the left of the given one
    * @return A new AI3 with the probability of the square at the left of the given one "changed" according to the level
    */
  private def setLeftSquare(square: String, level: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions (square)
    val currFirstSquareInInt = GridHelper.squareToListPositions (currentFirstSquareHit)
    if (squareJustHitInInt.apply(0) - 1 < 0) this //out of the grid
    else {
      val potentialSquare = GridHelper.intToLetter(squareJustHitInInt.apply(0) - 1) + (squareJustHitInInt.apply(1) + 1).toString // the square given such as "a1"
      if (positionToMentalMap.apply(potentialSquare).toInt > 9) this // The square wanted has already been processed
      else {
        level match {
          case "3" => setVeryHighProbability(potentialSquare)
          case "2" => setHighProbability(potentialSquare)
          case "1" => setNormalProbability(potentialSquare)
          case _ => setNeutral(potentialSquare)
        }

      }
    }
  }

  /** Gives the wanted level of probability of the square at the right of another
    *
    * @param square the square that is the reference for the change
    *               the square has to exist as a key in the mental map
    * @param level the level of the probility to give to the square at the right of the given one
    * @return A new AI3 with the probability of the square at the right of the given one "changed" according to the level
    */
  private def setRightSquare(square: String, level: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions (square)
    val currFirstSquareInInt = GridHelper.squareToListPositions (currentFirstSquareHit)
    if (squareJustHitInInt.apply(0) + 1 > 9) this //out of the grid
    else {
      val potentialSquare = GridHelper.intToLetter(squareJustHitInInt.apply(0) + 1) + (squareJustHitInInt.apply(1) + 1).toString // the square given such as "a1"
      if (positionToMentalMap.apply(potentialSquare).toInt < 0) this // The square wanted has already been processed
      else {
        level match {
          case "3" => setVeryHighProbability(potentialSquare)
          case "2" => setHighProbability(potentialSquare)
          case "1" => setNormalProbability(potentialSquare)
          case _ => setNeutral(potentialSquare)
        }
      }
    }
  }

  /** Generates a map giving the ésame value to the squares
    *
    * @param map the current map for which we have to "update" or create elements
    * @param listSquares the squares that have to be added and/or updated
    * @param value the value to assign for each square of the list of square
    * @return the map with all given squares associated to the value in this map
    */
  @tailrec
  private def updateMap(map: Map[String, String], listSquares: List[String], value: String): Map[String, String] = {
    if (listSquares.isEmpty) map
    else {
      val nmap = map + (listSquares.apply(0) -> value)
      updateMap(nmap, listSquares.drop(1), value)
    }
  }

  /** Adds a square to the already hit positions of the AI3
    *
    * @param square the new square to add
    * @return an AI3 consisting of all elements of the AI3 except the already hit collection for which the square has been added
    */
  def addPos(square: String): AI3 = {
    setAlreadyHitSquares(alreadyHitSquares + square)
  }
}