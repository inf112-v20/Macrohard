package inf112.skeleton.app;

import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.MovementType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MovementCardTest {

    private MovementCard move1 = new MovementCard(1, MovementType.ONE_FORWARD);
    private MovementCard move2 = new MovementCard(1, MovementType.TWO_FORWARD);
    private MovementCard move3 = new MovementCard(1, MovementType.THREE_FORWARD);
    private MovementCard moveBack = new MovementCard(1, MovementType.ONE_BACKWARD);

    @Test
    public void numberOfMovesIsEqualToOneForOneForwardMovementCard() {
        assertEquals(1, move1.getMoveID());
    }

    @Test
    public void numberOfMovesIsEqualToTwoForTwoForwardMovementCard() {
        assertEquals(2, move2.getMoveID());
    }

    @Test
    public void numberOfMovesIsEqualToThreeForThreeForwardMovementCard() {
        assertEquals(3, move3.getMoveID());
    }

    @Test
    public void numberOfMovesIsEqualToMinusOneForOneBackwardMovementCard() {
        assertEquals(-1, moveBack.getMoveID());
    }

}