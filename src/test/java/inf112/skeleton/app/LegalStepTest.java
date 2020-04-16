package inf112.skeleton.app;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LegalStepTest {

    private Player player;
    private Board board;
    private final ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.NORTH,
            Direction.EAST, Direction.SOUTH, Direction.WEST));

    private void createPlayerInMiddleOfBoard() {
        player = new Player(4,4, Direction.NORTH, false);
        board = new Board( 12, 12, player);
    }

    private void createPlayerInMiddleOfBoardSurroundedByFourPlayers() {
        player = new Player(4,4, Direction.NORTH, false);
        Player dummy1 = new Player(5, 4, Direction.NORTH, true);
        Player dummy2 = new Player(3, 4, Direction.NORTH, true);
        Player dummy3 = new Player(4, 3, Direction.NORTH, true);
        Player dummy4 = new Player(4, 5, Direction.NORTH, true);
        board = new Board( 12, 12, player, dummy1, dummy2, dummy3, dummy4);
    }

    @Test
    public void legalStepInAllDirectionsFromTheMiddleOfBoard() {
        createPlayerInMiddleOfBoard();

        for (Direction direction : directions) {
            assertTrue(board.legalStep(player, direction));
        }
    }

    @Test
    public void legalStepInAllDirectionsExceptInDirectionOfWall() {
        createPlayerInMiddleOfBoard();
        board.getTile(player).getWalls().add(Direction.NORTH);

        assertFalse(board.legalStep(player, Direction.NORTH));
        for (Direction remainingDirections : directions.subList(1, directions.size())) {
            assertTrue(board.legalStep(player, remainingDirections));
        }
    }

    @Test
    public void illegalStepsInAllDirectionsForImprisonedPlayer() {
        createPlayerInMiddleOfBoard();
        board.getTile(player).getWalls().addAll(directions);

        for (Direction direction : directions) {
            assertFalse(board.legalStep(player, direction));
        }
    }

    @Test
    public void legalStepInAllDirectionsEvenThoughNextTileIsOccupied() {
        createPlayerInMiddleOfBoardSurroundedByFourPlayers();

        for (Direction direction : directions) {
            assertTrue(board.legalStep(player, direction));
        }
    }

    @Test
    public void illegalStepInDirectionLeadingToAnOccupiedTileWithAWallInSameDirection() {
        createPlayerInMiddleOfBoardSurroundedByFourPlayers();
        board.getTile(player.getRow() + 1, player.getCol()).getWalls().add(Direction.NORTH);

        assertFalse(board.legalStep(player, Direction.NORTH));
        for (Direction remainingDirections : directions.subList(1, directions.size())) {
            assertTrue(board.legalStep(player, remainingDirections));
        }
    }


}