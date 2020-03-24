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

public class BoardTest {

    private Player player1;
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
        player1 = new Player(initRow, initCol, Direction.NORTH);
        board = new Board(player1,10, 10);
    }

    @Test
    public void move1NorthIncrementsPlayerRowWithOne() {
        board.execute(player1, move1);

        assertEquals(initRow + 1, player1.getRow());
    }

    @Test
    public void movingVerticallyDoesNotAffectPlayerColumn() {
        board.execute(player1, move1);

        assertEquals(initCol, player1.getCol());
    }


    @Test
    public void move2NorthIncrementsPlayerRowWithTwo() {
        board.execute(player1, move2);

        assertEquals(initRow + 2, player1.getRow());
    }

    @Test
    public void moving1EastIncrementsPlayerColumnWithOne() {
        player1.setDirection(Direction.EAST);

        board.execute(player1, move1);

        assertEquals(initCol + 1, player1.getCol());
    }

    @Test
    public void moving3EastIncrementsPlayerColumnWithThree() {
        player1.setDirection(Direction.EAST);

        board.execute(player1, move3);

        assertEquals(initCol + 3, player1.getCol());
    }

    @Test
    public void movingHorizontallyDoesNotAffectPlayerRow() {
        player1.setDirection(Direction.WEST);

        board.execute(player1, move1);

        assertEquals(initRow, player1.getRow());
    }

    @Test
    public void movingBackFacingNorthDecrementsPlayerRowWithOne() {
        board.execute(player1, moveBack);

        assertEquals(initRow - 1, player1.getRow());
    }

    @Test
    public void movingBackFacingWestIncrementsPlayerColumnWithOne() {
        player1.setDirection(Direction.WEST);

        board.execute(player1, moveBack);

        assertEquals(initCol + 1, player1.getCol());
    }

    @Test
    public void movingForwardDoesNotAffectDirectionOfPlayer() {
        board.execute(player1, move2);

        assertEquals(Direction.NORTH, player1.getDirection());
    }

    @Test
    public void rotatingClockwiseFromNorthYieldsEast() {
        board.execute(player1,clockwise);

        assertEquals(Direction.EAST, player1.getDirection());
    }

    @Test
    public void rotatingCounterClockwiseAndThenMoving1ForwardDecrementPlayerColumnWithOne() {
        board.execute(player1, countClockwise);
        board.execute(player1, move1);

        assertEquals( initCol - 1, player1.getCol());
    }

    @Test
    public void uTurningAndThenMoving1ForwardDecrementPlayerColumnWithOne() {
        board.execute(player1, uTurn);
        board.execute(player1, move1);

        assertEquals(initRow - 1, player1.getRow());
    }

    @Test
    public void movingOutOfBoundsVerticallyDoesNotAffectPlayerRow() {
        player1.setRow(9);

        board.execute(player1,move1);

        assertEquals(9, player1.getRow());
    }

    @Test
    public void movingOutOfBoundsHorizontallyDoesNotAffectPlayerColumn() {
        player1.setCol(9);
        player1.setDirection(Direction.EAST);

        board.execute(player1,move1);

        assertEquals(9, player1.getCol());
    }
}