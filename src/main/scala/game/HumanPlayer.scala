package game

case class HumanPlayer(private val _name: String, private val _shipsGrid: GridOfShips, private val _attackGrid: GridOfAttack, private val _ships: Set[Ship], private val _score: Int = 0) extends Player(_name, _shipsGrid, _attackGrid, _ships, _score) {

  /* overrides */
  /** Copies the player with new ships
    *
    * @param newShips the new collection of ships of the player
    * @return the player "updated" with the new collection given
    */
  override def copyShips(newShips: Set[Ship]): HumanPlayer = { this.copy(_ships = newShips) }

  /** Copies the player with a new grid of ships and a new collection of ships
    *
    * @param newGridOfShips the new grid of ships
    * @param newShips the new collection of ships
    * @return the player "updated" with the new grid and the new collection of ships given
    */
  override def copyGridShipsAndShips(newGridOfShips: GridOfShips, newShips: Set[Ship]): HumanPlayer = { this.copy(_shipsGrid = newGridOfShips, _ships = newShips) }

  /** Copies the player with a new grid of attack
    *
    * @param newAttackGrid the new grid of attack
    * @return the player "updated" with the new grid of attack given
    */
  override def copyAttackGrid(newAttackGrid: GridOfAttack): HumanPlayer = { this.copy(_attackGrid = newAttackGrid) }

  /** Copies the player with a new grid of ships
    *
    * @param newAttackGrid the new grid of ships
    * @return the player "updated" with the new grid of ships given
    */
  override def copyShipsGrid(newGridOfShips: GridOfShips): HumanPlayer = { this.copy(_shipsGrid = newGridOfShips) }

  /** Copies the player with a new score
    *
    * @param newScore the new score
    * @return the player "updated" with a new score
    */
  override def copyScore(newScore: Int): HumanPlayer = { this.copy(_score = newScore) }

  /** Copies a player with new grids and ships
    *
    * @param gs the new grid of ships
    * @param ga the new grid of attack
    * @param ships the new collection of ships
    * @return the player "updated" with the anterior parameters
    */
  override def copyGridsAndShips(gs: GridOfShips, ga: GridOfAttack, ships: Set[Ship]): HumanPlayer = { this.copy(_shipsGrid = gs, _attackGrid = ga, _ships = ships) }

  /** Speak when a given player is initialized */
  override def creationSpeak: Option[String] = Some("The player " + this.name + " has been created")
  /** Speak when it's player's turn */
  override def attackingTurnSpeak: Option[String] = Some("It's " + this.name +" turn")
  /** Speak when it demands to enter a position */
  override def demandeEnterSquareSpeak: Option[String] = Some(this.name + " has to enter a position (ex: A1)")
  /** Speak when a given player has been it */
  override def hitSpeak(playerHit: Player): Option[String] = Some("You hit " + playerHit.name)
  /** Speak when a hit has been missed */
  override def missSpeak: Option[String] = Some("You missed the attack")
  /** Speak when one of a given player's ships has been sunk */
  override def sunkSpeak(player: Player): Option[String] = Some("you sunk a ship of " + player.name)
}