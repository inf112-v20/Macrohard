package inf112.skeleton.app;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovementCardTest {
    MovementCard testcard;

    @Before
    public void setUp() throws Exception {
        testcard = new MovementCard(1,Type.MOVE2);
    }

    @Test
    public void testGetType() {
        Assert.assertFalse(testcard.getType(testcard) == Type.MOVE1);
        Assert.assertFalse(testcard.getType(testcard) == Type.MOVE3);
        Assert.assertTrue(testcard.getType(testcard) == Type.MOVE2);
    }

}