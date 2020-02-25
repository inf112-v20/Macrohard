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
    public void outOfBoundsTest() {
        board.move(player,11, 10);
        assertTrue(player.getRow() == 3);
        assertTrue(player.getCol() == 2);
    }

    @Test
    public void playerTileIsOccupied() {
        assertTrue(board.isOccupied(board.getTile(3,2)));
    }

    @Test
    public void updatedBoardHasCorrectValues() {
        board.move(player,3,3);
        //assertEquals(board.isOccupied(board.getTile(3,2)),false);
        assertEquals(board.isOccupied(board.getTile(3,3)) , true);
    }

    @Test
    public void moveTest() {
        board.move(player, 5, 5);
        Assert.assertTrue(board.getTile(3,2).getOccupied() == false);
        Assert.assertTrue(player.getRow() == 5);
        Assert.assertTrue(player.getRow() == 5);

    }

}