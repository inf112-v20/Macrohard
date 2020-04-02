package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.managers.TiledMapManager;

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

        initializeBoard(player, height, width);
    }

    public Board(Player player, TiledMapManager manager) {
        this.player = player;
        this.height = 12;
        this.width = 12;

        initializeBoard(player, height, width);
        buildWalls(manager);
    }

    public void initializeBoard(Player player, int height, int width) {
        board = new Tile[height][width];
        for (int i = 0; i < height; i ++){
            for (int j = 0; j < width; j ++) {
                board[i][j] = new Tile(false, i, j);
            }
        }

        setPlayer(player.getRow(), player.getCol());
        player.setSpawnPoint(board[player.getRow()][player.getCol()]);
    }

    private void buildWalls(TiledMapManager manager) {
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
                else if (manager.getCell("HOLES", row, col) != null) {
                    board[row][col].setHole(true);
                }
            }
        }
    }

    public void setPlayer(int row, int col){
        if (!outOfBounds(row, col)){
            board[row][col].setOccupied(true);
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

    private void rotate(Player player, RotationCard card) {
        Direction newDir = card.getNewDirection(player.getDirection());
        player.setDirection(newDir);
    }

    private void move(Player player, MovementCard card) {
        int roof = (card.getMoveID() == -1 ? 1 : card.getMoveID());
        int directionScalar = (card.getMoveID() == -1 ? -1 : 1);
        Direction direction = (card.getMoveID() == -1? player.getDirection().opposite() : player.getDirection());

        for (int i = 0; i < roof; i ++) {
            int newRow = player.getNextRow(directionScalar);
            int newCol = player.getNextCol(directionScalar);
            if (!outOfBounds(newRow, newCol) && !collision(player, direction)) {
                board[player.getRow()][player.getCol()].setOccupied(false);
                if (!board[newRow][newCol].isHole()) {
                    player.setRow(newRow);
                    player.setCol(newCol);
                    board[newRow][newCol].setOccupied(true);
                }
                else {
                    player.reSpawn();
                    player.getSpawnPoint().setOccupied(true);
                    break;
                }
            }
            else {
                break;
            }
        }

    }

    private Boolean outOfBounds(int row, int col) {
        return row < 0 || col < 0 || row >= height || col >= width;
    }

    private Boolean collision(Player player, Direction direction) {
        return board[player.getRow()][player.getCol()].getWalls().contains(direction);
    }

}
