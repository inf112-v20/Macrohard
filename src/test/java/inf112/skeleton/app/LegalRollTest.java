package inf112.skeleton.app;

import inf112.skeleton.app.tiles.Tile;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LegalRollTest {

    private Player player;
    private Board board;
    private Tile playerTile;
    private final ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.NORTH, Direction.EAST,
            Direction.SOUTH, Direction.WEST));
    private final int playerRow = 4;
    private final int playerCol = 4;

    private void initializePlayerAt(int row, int col) {
        player = new Player(row, col, Direction.NORTH, false);
        board = new Board(new ArrayList<>(Arrays.asList(player)), 12, 12);
        playerTile = board.getTile(player);
    }

    @Test
    public void legalRollInEveryDirection() {
        initializePlayerAt(playerRow, playerCol);

        for (Direction direction : directions) {
            assertTrue(board.legalRoll(board.getTile(player), direction, false));
        }
    }

    @Test
    public void illegalRollInDirectionOfWall() {
        initializePlayerAt(playerRow, playerCol);
        playerTile.getWalls().add(Direction.NORTH);

        assertFalse(board.legalRoll(playerTile, Direction.NORTH, false));
    }

    @Test
    public void illegalRollInDirectionOfOccupiedNonBeltTile() {
        initializePlayerAt(playerRow, playerCol);
        Tile nextTile = board.getAdjacentTile(playerTile, Direction.SOUTH);
        Player dummy = new Player(nextTile.getRow(), nextTile.getCol(), Direction.SOUTH, true);
        nextTile.setPlayer(dummy);

        assertFalse(board.legalRoll(playerTile, Direction.SOUTH, false));
    }


}