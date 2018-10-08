package main.scala.players

/** A trait expressing the speech corresponding to each event of a battleShip */
trait Speaker {

  /** Speak when a player is initialized */
  def creationSpeak: Option[String] = None
  /** Speak when it's player's turn */
  def attackingTurnSpeak: Option[String] = None
  /** Speak when it demands to enter a position */
  def demandeEnterSquareSpeak: Option[String] = None
  /** Speak when a given player has been it */
  def hitSpeak(playerHit: Player): Option[String] = None
  /** Speak when a hit has been missed */
  def missSpeak: Option[String] = None
  /** Speak when one of a given player's ships has been sunk */
  def sunkSpeak(player: Player): Option[String] = None
}
