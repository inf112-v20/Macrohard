package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.managers.TiledMapManager;
import inf112.skeleton.app.tiles.ConveyorBelt;
import inf112.skeleton.app.tiles.Hole;
import inf112.skeleton.app.tiles.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Board {

    private final int height;
    private final int width;

    private Tile[][] board;
    private Player player;

    //Graphic-independent constructor for test-classes
    public Board(Player player, int height, int width) {
        this.player = player;
        this.height = height;
        this.width = width;

        initializeBoard(height, width);
        set(player);
    }

    public Board(Player player, TiledMapManager mapManager) {
        this.player = player;
        this.height = 12;
        this.width = 12;

        initializeBoard(height, width);
        installConveyorBelts(mapManager);
        digHoles(mapManager);
        erectWalls(mapManager);
        set(player);
    }

    private void digHoles(TiledMapManager mapManager) {
        for (int row = 0; row < height; row ++) {
            for (int col = 0; col < width; col ++) {
                if (mapManager.getCell("HOLES", row, col) != null) {
                    board[row][col] = new Hole(row, col);
                }
            }
        }
    }

    private void set(Player player) {
        setPlayer(player.getRow(), player.getCol());
        player.setSpawnPoint(board[player.getRow()][player.getCol()]);
    }

    public void initializeBoard(int height, int width) {
        board = new Tile[height][width];
        for (int i = 0; i < height; i ++){
            for (int j = 0; j < width; j ++) {
                board[i][j] = new Tile(i, j);
            }
        }
    }

    private void erectWalls(TiledMapManager manager) {
        for (int row = 0; row < height; row ++) {
            for (int col = 0; col < width; col ++) {
                if (manager.getCell("WALLS", row, col) != null) {
                    TiledMapTileLayer.Cell wall = manager.getCell("WALLS", row, col);
                    String value = (String) wall.getTile().getProperties().get("Direction");
                    Direction dir = Direction.fromString(value);
                    board[row][col].getWalls().add(dir);
                    if (!outOfBounds(row + dir.getRowTrajectory(), col + dir.getColumnTrajectory())) {
                        Tile tile = board[row + dir.getRowTrajectory()][col + dir.getColumnTrajectory()];
                        if (tile != null)  { ArrayList<Direction> walls = tile.getWalls(); walls.add(dir.opposite()); }
                    }
                }
            }
        }
    }

    private void installConveyorBelts(TiledMapManager manager) {
        for (int row = 0; row < height; row ++) {
            for (int col = 0; col < width; col ++) {
                if (manager.getCell("CONVEYOR_BELTS", row, col) != null) {
                    TiledMapTileLayer.Cell wall = manager.getCell("CONVEYOR_BELTS", row, col);
                    String value = (String) wall.getTile().getProperties().get("Direction");
                    Direction dir = Direction.fromString(value);
                    boolean express = ((String) wall.getTile().getProperties().get("Type")).equals("CONVEYOR_EXPRESS");
                    board[row][col] = new ConveyorBelt(row, col, dir, express);
                }
            }
        }
    }

    public void setPlayer(int row, int col){
        if (!outOfBounds(row, col)){
            board[row][col].setPlayer(player);
        } else {
            this.player = null;
        }
    }

    public void execute(Player player, Card card) {
        if (card instanceof RotationCard) {
            rotate(player, (RotationCard) card);
        }
        else if (card instanceof MovementCard) {
            move(player, (MovementCard) card);
        }
    }

    private void rotate(@NotNull Player player, @NotNull RotationCard card) {
        Direction newDir = card.getNewDirection(player.getDirection());
        player.setDirection(newDir);
    }

    private void move(Player player, @NotNull MovementCard card) {
        int roof = (card.getMoveID() == -1 ? 1 : card.getMoveID());
        Direction direction = (card.getMoveID() == -1? player.getDirection().opposite() : player.getDirection());

        for (int i = 0; i < roof; i ++) {
            if (stepOne(player, direction)) { continue; } else { break; }
        }
    }

    public void rollConveyorBelts(boolean expressOnly) {
        ArrayList<ConveyorBelt> belts = new ArrayList<>();
        for (int row = 0; row < height; row ++) {
            for (int col = 0; col < width; col ++) {
                if (board[row][col] instanceof ConveyorBelt && board[row][col].isOccupied()) {
                    ConveyorBelt belt = (ConveyorBelt) board[row][col];
                    if (!expressOnly || belt.isExpress()) {
                        belts.add(belt);
                    }
                }
            }
        }
        for (ConveyorBelt belt : belts) {
            stepOne(belt.getPlayer(), belt.getDirection());
        }
    }

    public boolean stepOne(Player player, Direction direction) {
        if (legalStep(player, direction)) {
            board[player.getRow()][player.getCol()].setPlayer(null);
            player.stepIn(direction);
            if (board[player.getRow()][player.getCol()] instanceof Hole) {
                player.reSpawn();
                return false;
            }
            else {
                board[player.getRow()][player.getCol()].setPlayer(player);
                return true;
            }
        } else { return false; }
    }

    private boolean legalStep(Player player, Direction direction) {
        return (!collision(player, direction) && !outOfBounds(player, direction));
    }

    private boolean outOfBounds(int row, int col) {
        return row < 0 || col < 0 || row >= height || col >= width;
    }

    private boolean outOfBounds(Player player, Direction direction) {
        int newRow = player.getRow() + direction.getRowTrajectory();
        int newCol = player.getCol() + direction.getColumnTrajectory();
        return newRow < 0 || newCol < 0 || newRow >= height || newCol >= width;
    }

    private boolean collision(Player player, Direction direction) {
        return board[player.getRow()][player.getCol()].getWalls().contains(direction);
    }

}
