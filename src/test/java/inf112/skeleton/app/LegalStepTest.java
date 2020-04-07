package inf112.skeleton.app;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LegalStepTest {

    private Player player;
    private Board board;
    private final ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST));

    private void createPlayerInMiddleOfBoard() {
        player = new Player(4,4, Direction.NORTH, false);
        board = new Board(new ArrayList<>(Arrays.asList(player)), 12, 12);
    }

    private void createPlayerInMiddleOfBoardSurroundedByFourPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        player = new Player(4,4, Direction.NORTH, false);
        players.add(player);
        players.add(new Player(5, 4, Direction.NORTH, true));
        players.add(new Player(3, 4, Direction.NORTH, true));
        players.add(new Player(4, 3, Direction.NORTH, true));
        players.add(new Player(4, 5, Direction.NORTH, true));
        board = new Board(players, 12, 12);
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