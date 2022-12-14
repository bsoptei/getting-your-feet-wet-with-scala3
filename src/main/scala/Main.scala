import cardgame.{SinglePlayerBlackjack, SinglePlayerBlackjackRunner, SinglePlayerCardGameState}
import extensions.{given}
import player.*
import SinglePlayerBlackjack.{Decision, score}
import SinglePlayerCardGameState.*
import utils.Hand

import scala.annotation.tailrec
import scala.io.StdIn.readLine
import scala.util.Random

@main def main: Unit =
  println("Your name:")
  val playerName = readLine() 
  val runner = SinglePlayerBlackjackRunner(
    playerName, 
    Random(),
    hand => if score(hand) >= 17 then Decision.Stick else Decision.Hit,
    _ => readUserDecision,
    displayCurrentState
  )
  runner.run

@tailrec
def readUserDecision: Decision = 
  println("Your decision (h for hit, s for stick):")
  readLine().toLowerCase match
    case "h" => Decision.Hit
    case "s" => Decision.Stick
    case other => 
      println(s"Sorry, don't know $other, please try again.")
      readUserDecision

def displayCurrentState(state: SinglePlayerCardGameState): SinglePlayerCardGameState =
  state match
    case PlayerDecides(_, player, dealer) => displayHelper(s"${player.id}'s turn.", player, dealer)
    case DealerDecides(_, player, dealer) => displayHelper(s"${dealer.id}'s turn.", player, dealer)
    case DealerWon(player, dealer) => displayHelper("Dealer won.", player, dealer)
    case PlayerWon(player, dealer) => displayHelper("You won.", player, dealer)
    case Draw(player, dealer) => displayHelper("Draw", player, dealer)
      
  state

def displayHelper(status: String, player: Player, dealer: Dealer): Unit =
  val displayHand = (hand: Hand) => hand.map(_.show).mkString(",")
  println(status)
  println(s"Your hand: ${displayHand(player.hand)}")
  println(s"Dealer's hand: ${displayHand(dealer.hand)}")
