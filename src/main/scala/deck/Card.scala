package deck

final case class Card(suit: Suit, rank: Rank):
  override def toString(): String = s"$rank of $suit"
