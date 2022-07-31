package cardgame

import deck.*
import player.*  
import Rank.*
import Suit.*
import SinglePlayerBlackjack.{isBust, score}
import utils.Hand
import testutils.*

class ScoreTest extends TestHelper:
  val player = (hand: Hand) => Player("Jane Doe", hand)
  val dealer = (hand: Hand) => Dealer("Foo Bar", hand)

  test("score individual cards") {
    a [NotImplementedError] should be thrownBy score(AceOfSpades)
    val ofClubs = (rank: Rank) => Card(Clubs, rank)
    score(ofClubs(Two)) shouldEqual 2
    score(ofClubs(Three)) shouldEqual 3
    score(ofClubs(Four)) shouldEqual 4
    score(ofClubs(Five)) shouldEqual 5
    score(ofClubs(Six)) shouldEqual 6
    score(ofClubs(Seven)) shouldEqual 7
    score(ofClubs(Eight)) shouldEqual 8
    score(ofClubs(Nine)) shouldEqual 9
    score(ofClubs(Ten)) shouldEqual 10
    score(ofClubs(Jack)) shouldEqual 10
    score(ofClubs(Queen)) shouldEqual 10
    score(ofClubs(King)) shouldEqual 10
  }

  test("score hand") {
    score(Nil) shouldEqual 0
    score(TestHandWithoutAce) shouldEqual 3
    score(TestHandWithOneAce) shouldEqual 14
    score(TestHandWithOneAce2) shouldEqual 24
    score(TestHandWithMoreAces) shouldEqual 13
    score(TestHandWithMoreAces2) shouldEqual 18
  }

  test("score player/dealer") {
    Seq(
      TestHandWithoutAce, 
      TestHandWithOneAce, 
      TestHandWithOneAce2,
      TestHandWithMoreAces,
      TestHandWithMoreAces2
    ).foreach(hand =>
      score(hand) shouldEqual(score(player(hand)))
      score(hand) shouldEqual(score(dealer(hand)))
    )
  }

  test("isBust") {
    val bustHand = BlackjackHand :+ Card(Clubs, Two)
    isBust(player(BlackjackHand)) shouldBe false
    isBust(dealer(BlackjackHand)) shouldBe false

    isBust(player(bustHand)) shouldBe true
    isBust(dealer(bustHand)) shouldBe true
  }
