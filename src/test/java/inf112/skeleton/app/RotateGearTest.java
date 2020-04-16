package inf112.skeleton.app;

import inf112.skeleton.app.Board;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;

import static org.junit.Assert.*;

public class RotateGearTest {

    private final int midRow = 2;
    private final int midCol = 2;
    private final Direction initialDirection = Direction.NORTH;
    private final Player player = new Player(midRow, midCol, initialDirection);
    private final Board board = new Board(midRow * 2, midCol * 2, player);

}