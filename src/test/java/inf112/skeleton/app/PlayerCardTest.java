package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.MovementType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerCardTest {

    private Player player;
    private Deck deck;
    private int initialDeckSize;

    @Before
    public void setUp() {
        player = new Player(0,0, Direction.NORTH);
        deck = new Deck();
        deck.shuffle();
        initialDeckSize = deck.getDeckSize();
        deck.dealHand(player);
    }

    @Test
    public void deckResizesProperlyAfterDealingHand(){
        Assert.assertEquals(deck.getDeckSize(),initialDeckSize-player.getHealthPoints());
    }

    @Test
    public void numberOfCardsDealtIsEqualToPlayerHealthPoints(){
        Assert.assertTrue(player.getHand().getPossibleHand().length == player.getHealthPoints());
        player.setHealthPoints(8);
        deck.dealHand(player);
        Assert.assertTrue(player.getHand().getPossibleHand().length == player.getHealthPoints());
    }

    @Test
    public void playerProgramHasCorrectProperties(){
        player.setProgram();
        Assert.assertEquals(player.getProgram().length,5);
        Card card = new MovementCard(10, MovementType.ONE_FORWARD);
        player.getHand().getFinalHand()[0] = card;
        Assert.assertTrue(player.getProgram()[0]== card);

    }
}
