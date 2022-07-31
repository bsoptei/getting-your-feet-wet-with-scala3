package deck

import testutils.*

class DeckTest extends TestHelper:
  test("Deck.deck") {
    Deck.deck.size shouldEqual 52
  }

  test("Deck.shuffled") {
    val shuffled = TestDeckNonEmpty.shuffled 
    shuffled.isEmpty shouldBe false
    shuffled should not equal Deck.deck
    TestDeckEmpty shouldBe empty
    TestDeckEmpty.shuffled shouldBe empty
  }

  test("Deck.isEmpty, Deck.nonEmpty") {
    TestDeckNonEmpty.isEmpty shouldBe false
    TestDeckNonEmpty.nonEmpty shouldBe true
    TestDeckEmpty.isEmpty shouldBe true
    TestDeckEmpty.nonEmpty shouldBe false
  }

  test("Deck.draw") {
    val (cardOption, newDeck) = TestDeckNonEmpty.draw
    cardOption.isDefined shouldBe true
    newDeck.size shouldBe 51

    val (cardOption2, newDeck2) = TestDeckEmpty.draw
    cardOption2.isDefined shouldBe false
    newDeck2 shouldEqual TestDeckEmpty

    val (cardOption3, newDeck3) = TestDeckSingleton.draw
    cardOption3.isDefined shouldBe true
    newDeck3.isEmpty shouldBe true
  }

  test("Deck drawN") {
    val n = 5
    val (drawn1, newDeck) = TestDeckEmpty.drawN(n)
    drawn1 shouldBe empty
    newDeck shouldEqual TestDeckEmpty

    val (drawn2, newDeck2) = TestDeckNonEmpty.drawN(n)
    drawn2.size shouldBe n
    newDeck2 should not equal TestDeckNonEmpty
    newDeck2.size shouldBe 47

    val (drawn3, _) = newDeck2.drawN(50)
    drawn3 shouldBe empty
  }
