package helpers

/** The helper for grids constructions and ladders */
object GridHelper {

  /** Map of letters conversion */
  def mapOfletters = Map(("a", 0), ("b", 1), ("c", 2), ("d", 3), ("e", 4), ("f", 5), ("g", 6), ("h", 7), ("i", 8), ("j", 9), ("k", 10), ("l", 11), ("m", 12),
      ("n", 13), ("o", 14), ("p", 15), ("q", 16), ("r", 17), ("s", 18), ("t", 19), ("u", 20), ("v", 21), ("w", 22), ("x", 23), ("y", 24), ("z", 25))

  /** Map of ints conversion  */
  def mapOfInt = Map((0, "a"), (1, "b"), (2, "c"), (3, "d"), (4, "e"), (5, "f"), (6, "g"), (7, "h"), (8, "i"), (9, "j"))

  /** Converts letter into its number value
    *
    * @params the letter key
    * @return the int corresponding to the letter value
    */
  def letterToInt(letter:String): Int = mapOfletters(letter)

  /** Converts int into its string value
    *
    * @param int the integer key
    * @return the letter corresponding to the integer value
    */
  def intToLetter(int: Int): String = mapOfInt(int)

  /** Decreases by one the value of an integer
    *
    * @param int the number we want to decrease by one
    * @return the result of the integer -1
    */
  def intMinusOne(int: Int):Int  = int - 1

  /** Returns the coordinates of a given square into a list of integer
    *
    * @param square the square considered
    * @return the list of two integers corresponding respectively to ordinate and abscissa
    * @example with the given square a2, we'll obtain [1, 0]
    */
  def squareToListPositions(square: String): List[Int] = {
    val letter = square.head
    val number = square.tail
    List(intMinusOne(number.toInt), letterToInt(letter.toString.toLowerCase))
  }

  /** Generates a ladder of letter according to the size given
    *
    * @param number the size of the ladder wished, it has to be positive and less than 26
    * @return the list corresponding ladder
    */
  def ladderOfLetter(number: Int): List[Char] = {
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    val ladder = alphabet.slice(0, number)
    ladder.toList
  }

  /** Creates a list of list of dots of a given size
    *
    * @param l the initial list
    * @param size the size wanted for the list of dots
    * @return a list of list of dots corresponding to the initial list to which we add a list of dots of the given size
    */
  def addListOfDot(l: List[List[String]] = List(), size: Int): List[List[String]] = {
    if (l.size >= size) l
    else {
      val newList = List.fill(size)(".")
      val l2 = l :+ newList
      addListOfDot(l2, size)
    }
  }

}
