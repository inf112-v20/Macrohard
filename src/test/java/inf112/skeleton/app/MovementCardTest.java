package inf112.skeleton.app;

import inf112.skeleton.app.cards.MovementCard;
import org.junit.Assert;
import org.junit.Test;

public class MovementCardTest {

    @Test
    public void numberOfMovesIsEqualToOne() {
        MovementCard card = new MovementCard(1, 1);

        Assert.assertEquals(1, card.getNumberOfMoves());
    }

    @Test
    public void numberOfMovesCannotExceedThree() {
        MovementCard card = new MovementCard(1, 4);

        Assert.assertEquals(0, card.getNumberOfMoves());
    }

}