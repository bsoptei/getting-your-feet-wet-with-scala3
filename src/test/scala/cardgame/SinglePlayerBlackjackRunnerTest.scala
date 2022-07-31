package cardgame

import SinglePlayerBlackjack.Decision.*
import deck.*
import extensions.{given}
import player.*
import testutils.*
import SinglePlayerCardGameState.*

class SinglePlayerBlackjackRunnerTest extends TestHelper:
  test("SinglePlayerBlackjackRunner.checkBlackjack") {
    val defaultValue = PlayerDecides(Deck(TestRandom), Player(PlayerName), TestDealer)
    val default = () => defaultValue
    val check = SinglePlayerBlackjackRunner.checkBlackjack
    check(Player(PlayerName), TestDealer, default) shouldBe defaultValue
    val state1 = check(Player(PlayerName, BlackjackHand), TestDealer, default) match
      case _: PlayerWon => true
      case _ => false
    state1 shouldBe true
    val state2 = check(Player(PlayerName, BlackjackHand), TestDealer.updateHand(BlackjackHand), default) match
      case _: Draw => true
      case _ => false
    state2 shouldBe true
    val state3 = check(Player(PlayerName), TestDealer.updateHand(BlackjackHand), default) match
      case _: DealerWon => true
      case _ => false
    state3 shouldBe true
  }

  test("SinglePlayerBlackjackRunner.run") {
    var indicator = 0
    val runner = 
      SinglePlayerBlackjackRunner(
        "",
        TestRandom, 
        _ => Hit, 
        _ => Stick
      )
    
    val state = runner.run match
      case _: PlayerWon => true
      case _ => false
    state shouldBe true
  }
