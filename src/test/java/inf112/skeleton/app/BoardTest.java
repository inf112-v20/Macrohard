package inf112.skeleton.app;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void playerMustBePlacedOnBoard() {
        Player player = new Player(11, 11);
        Board board = new Board(player,10, 10);

        Assert.assertNull(board.getPlayer());
    }

    @Test
    public void playerTileIsOccupied() {
        Player player = new Player(3, 2);
        Board board = new Board(player, 5, 5);

        assertTrue(board.getTiles()[3][2]);
    }

}