package deck

import shuffle.Shuffle

final class Deck[S: Shuffle](shuffle: S, deck: Seq[Card] = Deck.deck):
  lazy val shuffled: Seq[Card] = shuffle.shuffle(deck).toSeq

  def isEmpty: Boolean = deck.isEmpty

  def nonEmpty: Boolean = !isEmpty

  def size: Int = deck.size

  def draw: (Option[Card], Deck[S]) = 
    shuffled match
      case head +: tail => (Some(head), Deck(shuffle, tail))
      case _ => (None, this)

  def drawN(n: Int): (Seq[Card], Deck[S]) = 
    if isEmpty || n > deck.size then (Nil, this) 
    else 
      val (drawn, remaining) = shuffled.splitAt(n)
      (drawn, Deck(shuffle, remaining))


object Deck:
  lazy val deck: Seq[Card] = 
    for suit <- Suit.values
        rank <- Rank.values
    yield Card(suit, rank)
