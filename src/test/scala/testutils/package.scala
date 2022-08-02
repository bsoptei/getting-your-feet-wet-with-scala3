package testutils

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.*
import matchers.should.*
import scala.util.Random

import deck.*
import extensions.{given}
import player.Player
import Rank.*
import shuffle.Shuffle
import Suit.*
import player.Dealer
import utils.Hand


trait TestHelper extends AnyFunSuite with Matchers

final val AceOfSpades = Card(Spades, Ace)
final val TestRandom = Random(42)
final val TestDeckNonEmpty = Deck(TestRandom)
final val TestDeckEmpty = Deck(TestRandom, Nil)
final val TestDeckSingleton = Deck(TestRandom, Seq(AceOfSpades))
final val TestHandWithoutAce = Seq(Card(Hearts, Three))
final val TestHandWithOneAce = Seq(AceOfSpades) ++ TestHandWithoutAce
final val TestHandWithOneAce2 = TestHandWithOneAce :+ Card(Diamonds, King)
final val TestHandWithMoreAces = Seq(AceOfSpades, Card(Hearts, Ace), Card(Clubs, Ace))
final val TestHandWithMoreAces2 = TestHandWithMoreAces :+ Card(Diamonds, Five)
final val PlayerName = "John Doe"
final val TestPlayer = Player(PlayerName, TestHandWithOneAce)
final val BlackjackHand = Seq(Card(Spades, Jack), Card(Spades, Ace))
final val TestDealer = Dealer("The Dealer")

case object NoOpShuffle

given Shuffle[NoOpShuffle.type] with
  extension (n: NoOpShuffle.type) def shuffle[T](xs: Seq[T]): Seq[T] = xs

final def deterministicDeck(hand: Hand) = Deck(NoOpShuffle, hand)