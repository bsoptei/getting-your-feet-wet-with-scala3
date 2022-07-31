package deck

import testutils.*
import extensions.{given}

class CardTest extends TestHelper:
  test("Card show") {
    AceOfSpades.show shouldEqual "Ace of Spades"
  }
