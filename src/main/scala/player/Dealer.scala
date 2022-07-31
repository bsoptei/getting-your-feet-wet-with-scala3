package player

import show.Show
import utils.Hand

final case class Dealer(id: String, hand: Hand = Nil) derives Show
