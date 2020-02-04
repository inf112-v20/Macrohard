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

        assertTrue(board.getTile(3,2).isOccupied());
    }

    @Test
    public void updatedBoardHasCorrectValues() {
        Player player = new Player(3, 2);
        Board board = new Board(player,10, 10);
        board.update(3,2,0);
        board.update(3,3,1);
        assertEquals(board.getTile(3,2).isOccupied(),false);
        assertEquals(board.getTile(3,3).isOccupied() , true);
    }

}