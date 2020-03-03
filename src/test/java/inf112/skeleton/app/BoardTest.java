package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.cards.RotationType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    Player player;
    Board board;
    Card moveCard;
    Card rotateCard;

    @Before
    public void setUp() {
        player = new Player(3,2, Direction.NORTH);
        board = new Board(player,10, 10);
        moveCard = new MovementCard(1,2);
        rotateCard = new RotationCard(2, RotationType.ROTATE_CLOCKWISE);
    }

    @Test
    // player position should remain unchanged when attempting to move out of bounds. However, direction should change.
    public void MoveOutOfBoundsTest() {
        board.move(player,11, 9, Direction.EAST);
        assertTrue(player.getRow() == 3);
        assertTrue(player.getCol() == 2);
        assertTrue(player.getDirection() == Direction.EAST);
    }

    @Test
    public void playerTileIsOccupied() {
        assertTrue(board.isOccupied(board.getTile(3,2)));
        assertFalse(board.isOccupied(board.getTile(2,3)));
    }

    @Test
    public void updatedBoardHasCorrectValues() {
        board.move(player,3,3, Direction.NORTH);
        assertTrue(board.isOccupied(board.getTile(3, 3)));
    }

    @Test
    public void moveTest() {
        board.move(player, 5, 5,Direction.WEST);
        assertFalse(board.getTile(3,2).getOccupied());
        assertTrue(board.getTile(5,5).getOccupied());
        assertTrue(player.getRow() == 5);
        assertTrue(player.getCol() == 5);

    }

    @Test
    public void setPlayerTest() {
        board.setPlayer(1,1);
        assertTrue(board.getTile(1,1).getOccupied());
    }

    @Test
    // Should set board.player to null when out of bounds
    public void setPlayerOutOfBoundsTest() {
        board.setPlayer(13,13);
        assertEquals(board.getPlayer(),null);
    }

    @Test
    public void moveUsingMoveCardTest() {
        board.execute(player, moveCard);
        assertTrue(player.getRow() == 5);
        assertTrue(player.getDirection()==Direction.NORTH);
    }

    @Test
    public void moveUsingRotationCardTest() {
        board.execute(player,rotateCard);
        assertTrue(player.getRow() == 3);
        assertTrue(player.getCol() == 2);
        assertTrue(player.getDirection() == Direction.EAST);
    }

    @Test
    public void moveOutOfBoundsUsingMoveCardTest() {
        Player player1 = new Player(9,1,Direction.NORTH);
        board.execute(player1,moveCard);
        assertTrue(player1.getRow() == 9);
        assertTrue(player1.getDirection() == Direction.NORTH);
    }

}