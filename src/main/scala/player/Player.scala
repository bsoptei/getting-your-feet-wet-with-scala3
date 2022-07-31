package player

import show.Show
import utils.Hand

final case class Player(id: String, hand: Hand = Nil) derives Show
