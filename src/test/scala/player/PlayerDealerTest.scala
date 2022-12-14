package player

import deck.{Card, Rank, Suit}
import extensions.{given}
import testutils.*

class PlayerDealerTest extends TestHelper:
  test("Player/Dealer.updateHand") {
    val p0 = Player("")
    p0.hand shouldBe empty
    
    val p1 = p0.updateHand(AceOfSpades)
    p1.hand.size shouldEqual 1
    
    val p2 = p1.updateHand(TestHandWithoutAce)
    p2.hand.size shouldEqual 2
  }
