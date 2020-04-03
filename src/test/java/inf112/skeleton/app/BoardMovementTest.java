package inf112.skeleton.app;

import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.MovementType;
import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.cards.RotationType;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BoardMovementTest {

    private Player player;
    private Board board;
    private int initRow = 3;
    private int initCol = 2;
    private MovementCard move1 = new MovementCard(1, MovementType.ONE_FORWARD);
    private MovementCard move2 = new MovementCard(1, MovementType.TWO_FORWARD);
    private MovementCard move3 = new MovementCard(1, MovementType.THREE_FORWARD);
    private MovementCard moveBack = new MovementCard(1, MovementType.ONE_BACKWARD);
    private RotationCard clockwise = new RotationCard(1, RotationType.CLOCKWISE);
    private RotationCard countClockwise = new RotationCard(1, RotationType.COUNTER_CLOCKWISE);
    private RotationCard uTurn = new RotationCard(1, RotationType.U_TURN);

    @Before
    public void setUp() {
        player = new Player(initRow, initCol, Direction.NORTH, true);
        board = new Board(player,10, 10);
    }

    @Test
    public void move1NorthIncrementsPlayerRowWithOne() {
        board.execute(player, move1);

        assertEquals(initRow + 1, player.getRow());
    }

    @Test
    public void movingVerticallyDoesNotAffectPlayerColumn() {
        board.execute(player, move1);

        assertEquals(initCol, player.getCol());
    }


    @Test
    public void move2NorthIncrementsPlayerRowWithTwo() {
        board.execute(player, move2);

        assertEquals(initRow + 2, player.getRow());
    }

    @Test
    public void moving1EastIncrementsPlayerColumnWithOne() {
        player.setDirection(Direction.EAST);

        board.execute(player, move1);

        assertEquals(initCol + 1, player.getCol());
    }

    @Test
    public void moving3EastIncrementsPlayerColumnWithThree() {
        player.setDirection(Direction.EAST);

        board.execute(player, move3);

        assertEquals(initCol + 3, player.getCol());
    }

    @Test
    public void movingHorizontallyDoesNotAffectPlayerRow() {
        player.setDirection(Direction.WEST);

        board.execute(player, move1);

        assertEquals(initRow, player.getRow());
    }

    @Test
    public void movingBackFacingNorthDecrementsPlayerRowWithOne() {
        board.execute(player, moveBack);

        assertEquals(initRow - 1, player.getRow());
    }

    @Test
    public void movingBackFacingWestIncrementsPlayerColumnWithOne() {
        player.setDirection(Direction.WEST);

        board.execute(player, moveBack);

        assertEquals(initCol + 1, player.getCol());
    }

    @Test
    public void playerTileIsOccupied() {
        assertTrue(board.isOccupied(board.getTile(3,2)));
        assertFalse(board.isOccupied(board.getTile(2,3)));
    }

    @Test
    public void setPlayerTest() {
        board.set(player);
        assertTrue(board.getTile(player.getRow(),player.getCol()).isOccupied());
    }

    @Test
    public void movingForwardDoesNotAffectDirectionOfPlayer() {
        board.execute(player, move2);

        assertEquals(Direction.NORTH, player.getDirection());
    }

    @Test
    public void rotatingClockwiseFromNorthYieldsEast() {
        board.execute(player,clockwise);

        assertEquals(Direction.EAST, player.getDirection());
    }

    @Test
    public void rotatingCounterClockwiseAndThenMoving1ForwardDecrementPlayerColumnWithOne() {
        board.execute(player, countClockwise);
        board.execute(player, move1);

        assertEquals( initCol - 1, player.getCol());
    }

    @Test
    public void uTurningAndThenMoving1ForwardDecrementPlayerColumnWithOne() {
        board.execute(player, uTurn);
        board.execute(player, move1);

        assertEquals(initRow - 1, player.getRow());
    }

    @Test
    public void movingOutOfBoundsVerticallyDoesNotAffectPlayerRow() {
        player.setRow(9);

        board.execute(player,move1);

        assertEquals(9, player.getRow());
    }

    @Test
    public void movingOutOfBoundsHorizontallyDoesNotAffectPlayerColumn() {
        player.setCol(9);
        player.setDirection(Direction.EAST);

        board.execute(player,move1);

        assertEquals(9, player.getCol());
    }
}