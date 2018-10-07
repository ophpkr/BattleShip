package game
import scala.util.Random

case class AI3(private val _name: String, private val _shipsGrid: GridOfShips, private val _attackGrid: GridOfAttack, private val _ships: Set[Ship], private val _score: Int = 0, private val _random: Random, private val _positionToMentalMap: Map ) extends Player(_name, _shipsGrid, _attackGrid, _ships, _score) with AI {

  /* overrides */

  override def random = _random
  def mentalGrid = _mentalGrid //TODO: Delete it
  def positionToMentalMap = _positionToMentalMap
  def alreadyHitSquares = _alreadyHitSquares
  def currentFirstSquareHit = _currentFirstSquareHit

  /** Copies the player with new ships
    *
    * @param newShips the new collection of ships of the player
    * @return the player "updated" with the new collection given
    */
  override def copyShips(newShips: Set[Ship]): AI3 = { this.copy(_ships = newShips) }

  /** Copies the player with a new grid of ships and a new collection of ships
    *
    * @param newGridOfShips the new grid of ships
    * @param newShips the new collection of ships
    * @return the player "updated" with the new grid and the new collection of ships given
    */
  override def copyGridShipsAndShips(newGridOfShips: GridOfShips, newShips: Set[Ship]): AI3 = { this.copy(_shipsGrid = newGridOfShips, _ships = newShips) }

  /** Copies the player with a new grid of attack
    *
    * @param newAttackGrid the new grid of attack
    * @return the player "updated" with the new grid of attack given
    */
  override def copyAttackGrid(newAttackGrid: GridOfAttack): AI3 = { this.copy(_attackGrid = newAttackGrid) }

  /** Copies the player with a new grid of ships
    *
    * @param newAttackGrid the new grid of ships
    * @return the player "updated" with the new grid of ships given
    */
  override def copyShipsGrid(newGridOfShips: GridOfShips): AI3 = { this.copy(_shipsGrid = newGridOfShips) }

  /** Copies the player with a new score
    *
    * @param newScore the new score
    * @return the player "updated" with a new score
    */
  override def copyScore(newScore: Int): AI3 = { this.copy(_score = newScore) }

  /** Copies a player with new grids and ships
    *
    * @param gs the new grid of ships
    * @param ga the new grid of attack
    * @param ships the new collection of ships
    * @return the player "updated" with the anterior parameters
    */
  override def copyGridsAndShips(gs: GridOfShips, ga: GridOfAttack, ships: Set[Ship]): AI3 = { this.copy(_shipsGrid = gs, _attackGrid = ga, _ships = ships) }

  override def attack(): String = {
    val higherProbability = this.positionToMentalMap.filter((x, y) => y == "3").keySet
    val mediumProbility = this.positionToMentalMap.filter((x, y) => y == "2").keySet
    val simpleProbability = this.positionToMentalMap.filter((x, y) => y == "1").keySet

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
        higherProbability.apply(randint)
      }
      else {
        // there is at least one square with the "2" value. 2 being the maximum value
        if (higherProbability.isEmpty) {
          val randint = this.random.nextInt(mediumProbility.size)
          mediumProbility.apply(randint)
        }
        // there is at least one square with the "3" value. 3 being the maximum value
        else {
          val randint = this.random.nextInt(higherProbability.size)
          higherProbability.apply(randint)
        }
      }
    }
  }

  def higherProbabilitySquares(): List[(Int, Int)] = {
    mentalGrid.rapresentation.flatMap((x, y) => { if (mentalGrid.reprensentation.apply(x).apply(y) == "3") List(x, y)})
  }

  def (): List[(Int, Int)] = {
    mentalGrid.rapresentation.flatMap((x, y) => { if (mentalGrid.reprensentation.apply(x).apply(y) == "2") List(x, y)})
  }

  def initMap(map: Map, letters: List[String], numbers: List[String]): Map = {
    // all positions have been created
    if (letters.isEmpty) map
    else {
      // the entire colomn has been created
      if (numbers.isEmpty) {
        val nletters = letters.drop(0)
        val nnumbers = List("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        initMap(map, nletters, nnumbers)
      }
      else {
        // add a new key value
        val nmap = map + (letters.apply(0) + numbers(0), "0")
        val nnumbers = numbers.drop(0)
        initMap(nmap, letters, nnumbers)
      }
    }
  }

  def setSunk(square: String): IA3 = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square, 4))
  }

  def setExtremity(square: String): IA3 = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square, 4))
  }

  def setHit(square: String): IA3 = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square, "-2"))
  }

  def setMiss(square: String): IA3 = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square, "-1"))
  }

  def setVeryHighProbability(square: String) = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square, "3"))
  }

  def setHighProbability(square: String) = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square, "2"))
  }

  def setNormalProbability(square: String) = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square, "1"))
  }

  def setNeutral(square: String) = {
    this.copy(_positionToMentalMap = positionToMentalMap + (square, "0"))
  }

  def manageMiss(square: String): AI3 = {
    setMiss(square)
    /*positionToMentalMap.apply(square) match {
      case "0" | "1"| "2" => setMiss(square)
      case "3" => {
        val newmap = setMiss(square)
        // if there are no more square with a "3" value so the squares having a "2" have the higher probability to hit a ship
        if(newmap.positionToMentalMap.filter((x, y) => y == "3").keySet.isEmpty)
      }
    }*/
  }

  def manageHit(square: String): AI3 = {
    //TODO: are val really necessary
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
    }
  }

  // put at 2 proba around the square when it is poss
  def giveSameProbabilityAround(square: String): AI3 = {
    val squareInIntList = GridHelper.squareToListPositions(square) // the list of position in int corresponding to the square
    val listSquaresAroundInInt = List(List(squareInIntList(0)-1, squareInIntList(1)), List(squareInIntList(0) + 1, squareInIntList(1)),
      List(squareInIntList(0), squareInIntList(1) - 1), List(squareInIntList(0), squareInIntList(1) + 1)) //The list of all squares position in int around the initial square
    val validIntPositions = listSquaresAroundInInt.filter(x => x(0)>= 0 && x(0)<10 && x(1)>=0 && x(1)<10) // List of all positions existing in a battleship grid
    val squaresRetained = validIntPositions.map(x => GridHelper.intToLetter(x(0)) + (x(1)+1).toString) // The list of the squares retained in letter (written like "a1")
    val squaresToModify = squaresRetained.map(x => if(positionToMentalMap.apply(x).toInt >= 0) x) // Only squares having not been hit or sunk have to be modified
    val nmap = updateMap(positionToMentalMap, squaresToModify, "2")
    this.copy(_positionToMentalMap = nmap)
  }

  def giveMoreProbabilityForSquaresInSameDirection(square): AI3 = {
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

  def giveMoreProbilityForExtremitySquares(square: String): AI3 = {
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
        nai.setLowerSquare(currentFirstSquareHit)
      }
    }
    // squares on the same line
    else {
      // the current square is at the right of the hit one
      if (squareJustHitInInt.apply(0) > currFirstSquareInInt.apply(0)) {
        val nai = setLeft(currentFirstSquareHit, "3")
        nai.setRightSquare(square, "3")
      }
    }
  }

  def setHigherSquare(square: String, level: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions (square)
    val currFirstSquareInInt = GridHelper.squareToListPositions (currentFirstSquareHit)
    if (squareJustHitInInt.apply(1) - 1 < 0) this //out of the grid
    else {
      val potentialSquare = GridHelper.intToLetter(squareJustHitInInt.apply (0) ) + (squareJustHitInInt.apply(1) + 1 - 1).toString // the square given such as "a1"
      if (positionToMentalMap.apply(x).toInt < 0) this // The square wanted has already been processed
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

  def setLowerSquare(square: String, level: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions (square)
    val currFirstSquareInInt = GridHelper.squareToListPositions (currentFirstSquareHit)
    if (squareJustHitInInt.apply(1) + 1 > 9) this //out of the grid
    else {
      val potentialSquare = GridHelper.intToLetter(squareJustHitInInt.apply (0) ) + (squareJustHitInInt.apply(1) + 1 + 1).toString // the square given such as "a1"
      if (positionToMentalMap.apply(x).toInt < 0) this // The square wanted has already been processed
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

  def setLeftSquare(square: String, level: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions (square)
    val currFirstSquareInInt = GridHelper.squareToListPositions (currentFirstSquareHit)
    if (squareJustHitInInt.apply(0) - 1 < 0) this //out of the grid
    else {
      val potentialSquare = GridHelper.intToLetter(squareJustHitInInt.apply(0) - 1) + (squareJustHitInInt.apply(1) + 1).toString // the square given such as "a1"
      if (positionToMentalMap.apply(x).toInt > 9) this // The square wanted has already been processed
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

  def setRightSquare(square: String, level: String): AI3 = {
    val squareJustHitInInt = GridHelper.squareToListPositions (square)
    val currFirstSquareInInt = GridHelper.squareToListPositions (currentFirstSquareHit)
    if (squareJustHitInInt.apply(0) + 1 < 0) this //out of the grid
    else {
      val potentialSquare = GridHelper.intToLetter(squareJustHitInInt.apply(0) + 1) + (squareJustHitInInt.apply(1) + 1).toString // the square given such as "a1"
      if (positionToMentalMap.apply(x).toInt > 9) this // The square wanted has already been processed
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

  def updateMap(map: Map, listSquares: List[String], value: String): Map = {
    if (listSquares.isEmpty) map
    else {
      val nmap = map + (listSquares.apply(0), value)
      updateMap(nmap, listSquares.drop(0), value)
    }
  }
}