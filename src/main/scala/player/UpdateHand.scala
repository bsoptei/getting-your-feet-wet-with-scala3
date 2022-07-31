package player

import deck.Card
import utils.Hand

trait UpdateHand[A]:
  extension (a: A) def updateHand(card: Card): A
  extension (a: A) def updateHand(newHand: Hand): A
