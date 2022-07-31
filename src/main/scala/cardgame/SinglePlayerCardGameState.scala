package cardgame

import deck.Deck
import player.*

enum SinglePlayerCardGameState:
  case PlayerDecides(deck: Deck[?], player: Player, dealer: Dealer) 
  case DealerDecides(deck: Deck[?], player: Player, dealer: Dealer)
  case DealerWon(player: Player, dealer: Dealer)
  case PlayerWon(player: Player, dealer: Dealer)
  case Draw(player: Player, dealer: Dealer)
