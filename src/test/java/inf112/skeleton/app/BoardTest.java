package inf112.skeleton.app;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    Player player;
    Board board;

    @Before
    public void setUp() {
        player = new Player(3,2);
        board = new Board(player,10, 10);
    }

    @Test
    public void playerMustBePlacedOnBoard() {
        Player player = new Player(11, 11);
        Board board = new Board(player,10, 10);
        Assert.assertNull(board.getPlayer());
    }

    @Test
    public void playerTileIsOccupied() {
        assertTrue(board.getTile(3,2).isOccupied());
        assertFalse(board.getTile(4,2).isOccupied());
    }

    @Test
    public void updatedBoardHasCorrectValues() {
        board.update(3,2,0);
        assertEquals(board.getTile(3,2).isOccupied(),false);
        board.update(3,3,1);
        assertEquals(board.getTile(3,3).isOccupied() , true);
    }


}