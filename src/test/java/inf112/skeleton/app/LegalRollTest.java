package inf112.skeleton.app;

import inf112.skeleton.app.tiles.ConveyorBelt;
import inf112.skeleton.app.tiles.Tile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LegalRollTest {

    private Board board;

    private final int midRow = 4;
    private final int midCol = 4;

    private Direction wall = Direction.SOUTH;
    private Direction unOccupiedTileDirection = Direction.WEST;

    private Tile playerTile;
    private ConveyorBelt northwardExpressBelt = new ConveyorBelt(midRow + Direction.NORTH.getRowModifier(), midCol + Direction.NORTH.getColumnModifier(), Direction.NORTH, true);
    private ConveyorBelt eastwardBelt = new ConveyorBelt(midRow + Direction.EAST.getRowModifier(), midCol + Direction.EAST.getColumnModifier(), Direction.EAST, false);

    @Before
    public void setUp() {
        Player player = new Player(midRow, midCol, Direction.NORTH);
        Player dummy1 = new Player(eastwardBelt.getRow(), eastwardBelt.getCol(), Direction.EAST);
        Player dummy2 = new Player(northwardExpressBelt.getRow(), northwardExpressBelt.getCol(), Direction.NORTH);

        board = new Board(10, 10, player, dummy1, dummy2);

        playerTile = board.getTile(player);
        playerTile.getWalls().add(wall);

        board.layTile(eastwardBelt);
    }

    @Test
    public void legalRollInDirectionOfUnoccupiedTile() {
        assertTrue(board.legalRoll(playerTile, unOccupiedTileDirection, false));
    }

    @Test
    public void illegalRollInDirectionOfWall() {
        assertFalse(board.legalRoll(playerTile, wall, false));
    }

    @Test
    public void illegalRollInDirectionOfOccupiedNonBeltTile() {
        assertFalse(board.legalRoll(playerTile, Direction.NORTH, false));
    }

    @Test
    public void legalConveyorRollInDirectionOfOccupiedConveyorBelt() {
        assertTrue(board.legalRoll(playerTile, Direction.EAST, false));
    }

    @Test
    public void illegalExpressRollInDirectionOfOccupiedNonExpressBelt() {
        assertFalse(board.legalRoll(playerTile, Direction.EAST, true));
    }

    @Test
    public void legalExpressRollInDirectionOfOccupiedExpressBelt() {
        board.layTile(northwardExpressBelt);

        assertTrue(board.legalRoll(playerTile, Direction.NORTH, true));
    }

    @Test
    public void illegalRollInDirectionOfOccupiedBeltFacingOccupiedNonBeltTile() {
        Player dummy1 = board.getPlayers().get(1);
        int[] xyCoordinate = new int[]{dummy1.getRow() + Direction.EAST.getRowModifier(), dummy1.getCol() + Direction.EAST.getColumnModifier()};
        Player dummy3 = new Player(xyCoordinate[0], xyCoordinate[1], Direction.EAST);
        board.getTile(dummy3).setPlayer(dummy3);

        assertFalse(board.legalRoll(playerTile, Direction.EAST, false));
    }

    @Test
    public void illegalRollInDirectionOfOccupiedBeltFacingWall() {
        eastwardBelt.getWalls().add(eastwardBelt.getDirection());

        assertFalse(board.legalRoll(playerTile, Direction.EAST, false));
    }


}