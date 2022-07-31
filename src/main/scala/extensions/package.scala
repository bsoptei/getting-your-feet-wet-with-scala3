package extensions 

import deck.Card
import player.*
import show.Show
import shuffle.Shuffle
import utils.Hand

import scala.util.Random

given Shuffle[Random] with
  extension (r: Random) def shuffle[T](xs: Iterable[T]): Iterable[T] = r.shuffle(xs)
  
given UpdateHand[Dealer] with
  extension (d: Dealer) def updateHand(card: Card): Dealer = d.copy(hand = d.hand :+ card)
  extension (d: Dealer) def updateHand(newHand: Hand): Dealer = d.copy(hand = d.hand ++ newHand)

given UpdateHand[Player] with
  extension (p: Player) def updateHand(card: Card): Player = p.copy(hand = p.hand :+ card)
  extension (p: Player) def updateHand(newHand: Hand): Player = p.copy(hand = p.hand ++ newHand)

given Show[Card] with
  extension (card: Card) def show: String = s"${card.rank} of ${card.suit}"
