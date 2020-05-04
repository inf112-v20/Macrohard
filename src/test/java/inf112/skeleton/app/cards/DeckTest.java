package inf112.skeleton.app.cards;

import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DeckTest {
    Deck deck;
    Player player;

    @Before
    public void setUp() {
        deck = new Deck(true);
        player = new Player(5, 5, Direction.any());

    }

    @Test
    public void dealHand() {
        int deckSize = deck.getDeckSize();
        deck.dealHand(player);
        assertEquals(player.getCards().size(), (9 - player.getDamageTokens()));
        assertEquals(deck.getDeckSize(), deckSize - (9 - player.getDamageTokens()));
    }

    @Test
    public void dealNonValidHand() {
        player.applyDamage(1);
        int deckSize = deck.getDeckSize();
        deck.dealHand(player);
        assertEquals(player.getCards().size(), (9 - player.getDamageTokens()));
        assertEquals(deck.getDeckSize(), deckSize - (9 - player.getDamageTokens()));
    }

    @Test
    public void cardDealtEqualsCardRemoved() {
        deck.dealHand(player);
        for (Card card : player.getCards()) {
            for (Card dcard : deck.getDeck()) {
                assertNotEquals(card, dcard);
            }
        }
    }


}