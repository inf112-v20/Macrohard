package inf112.skeleton.app;

import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.MovementType;
import static org.junit.Assert.*;
import org.junit.Test;

public class MovementCardTest {

    MovementCard move1 = new MovementCard(1, MovementType.ONE_FORWARD);
    MovementCard move2 = new MovementCard(1, MovementType.TWO_FORWARD);
    MovementCard move3 = new MovementCard(1, MovementType.THREE_FORWARD);
    MovementCard moveBack = new MovementCard(1, MovementType.ONE_BACKWARD);

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