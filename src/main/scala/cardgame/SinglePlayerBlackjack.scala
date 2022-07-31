package cardgame

import deck.*
import Rank.*
import player.*
import shuffle.Shuffle
import SinglePlayerCardGameState.*
import extensions.{given}
import utils.Hand

object SinglePlayerBlackjack:
  import Decision.*

  def apply[S: Shuffle](s: S, playerName: String): SinglePlayerCardGameState =
    val deck = Deck(s)
    val (playerCards, deckUpdated1) = deck.drawN(2)
    val (dealerCards, deckUpdated2) = deckUpdated1.drawN(2)
    
    PlayerDecides(
      deckUpdated2, 
      Player(playerName, playerCards), 
      Dealer("The Dealer", dealerCards)
    )
  
  enum Decision:
    case Hit, Stick

  type Strategy = Hand => Decision
  
  given UpdateGameState[SinglePlayerCardGameState] with
    type UpdateInput = Decision
    extension (state: SinglePlayerCardGameState) def updateGameState(decision: UpdateInput): SinglePlayerCardGameState =
      state match 
        case PlayerDecides(deck, player, dealer) => 
          decision match
            case Hit => 
              val (newCard, newDeck) = deck.draw
              val newPlayer = player.updateHand(newCard.get)
              if isBust(newPlayer) then DealerWon(newPlayer, dealer)
              else PlayerDecides(newDeck, newPlayer, dealer)
            case Stick => DealerDecides(deck, player, dealer)
        case DealerDecides(deck, player, dealer) => 
          lazy val playerScore = score(player)
          lazy val dealerScore = score(dealer)
          
          decision match
            case Hit =>
              val (newCard, newDeck) = deck.draw
              val newDealer = dealer.updateHand(newCard.get)
              if isBust(newDealer) then PlayerWon(player, newDealer)
              else DealerDecides(newDeck, player, newDealer)
            case Stick => 
              if playerScore > dealerScore then PlayerWon(player, dealer)
              else if dealerScore > playerScore then DealerWon(player, dealer)
              else Draw(player, dealer)
        case _ => state

  def score(hand: Hand): Int = 
    val (aces, nonAces) = hand.partition(_.rank == Ace)
    val nonAcesSum = nonAces.map(score).sum
    val numberOfAces = aces.size
    val acesSum = numberOfAces match 
      case 0 => 0
      case 1 => if hand.size > 2 then 11
                else if nonAcesSum <= 10 then 11 else 1
      case _ => 11 + numberOfAces - 1
    
    nonAcesSum + acesSum

  def score(card: Card): Int = 
    card.rank match 
      case Two => 2
      case Three => 3
      case Four => 4
      case Five => 5
      case Six => 6
      case Seven => 7
      case Eight => 8
      case Nine => 9
      case Ten | Jack | Queen | King => 10
      case _ => ???
    
  def score(pd: Player | Dealer): Int = pd match
    case p: Player => score(p.hand)
    case d: Dealer => score(d.hand)

  def isBust(pd: Player | Dealer): Boolean = score(pd) > 21
