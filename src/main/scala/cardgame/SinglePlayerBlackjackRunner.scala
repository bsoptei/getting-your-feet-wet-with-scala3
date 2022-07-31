package cardgame

import deck.Deck
import player.*
import shuffle.Shuffle
import SinglePlayerBlackjack.{Decision, Strategy, score, given}
import SinglePlayerCardGameState.*
import utils.Hand

import scala.annotation.tailrec

class SinglePlayerBlackjackRunner[S: Shuffle](
  playerName: String,
  s: S, 
  dealerStrategy: Strategy,
  playerStrategy: SinglePlayerCardGameState => Decision,
  process: SinglePlayerCardGameState => SinglePlayerCardGameState = identity
):
 import SinglePlayerBlackjackRunner._

  def run: SinglePlayerCardGameState = 
    @tailrec
    def inner(current: SinglePlayerCardGameState): SinglePlayerCardGameState = 
      val _ = process(current)
      current match 
        case pd @ PlayerDecides(_, player, dealer) => 
            val default = () => pd.updateGameState(playerStrategy(pd))
            if player.hand.size == 2 then inner(checkBlackjack(player, dealer, default))
            else inner(default())
        case dd @ DealerDecides(_, _, dealer) => inner(dd.updateGameState(dealerStrategy(dealer.hand)))
        case _ => current

    inner(SinglePlayerBlackjack(s, playerName))

object SinglePlayerBlackjackRunner:
  def checkBlackjack(
    player: Player, 
    dealer: Dealer, 
    default: () => SinglePlayerCardGameState
  ): SinglePlayerCardGameState = 
    val hasBlackJack = (hand: Hand) => score(hand) == 21
    (hasBlackJack(player.hand), hasBlackJack(dealer.hand)) match
      case (true, false) => PlayerWon(player, dealer)
      case (false, true) => DealerWon(player, dealer)
      case (true, true) => Draw(player, dealer)
      case _ => default()
