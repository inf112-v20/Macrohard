package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.managers.TiledMapManager;

import java.util.ArrayList;

import java.util.ArrayList;

public class Board {

    private final int height;
    private final int width;

    private Tile[][] board;
    private Player player;
    private ArrayList<Player> players;

    //Graphic-independent constructor for test-classes
    public Board(Player player, int height, int width) {
        this.player = player;
        this.height = height;
        this.width = width;
        initializeBoard(player,height,width);
    }

    public void initializeBoard(Player player, int height, int width) {
        board = new Tile[height][width];
        for (int i = 0; i < height; i ++){
            for (int j = 0; j < width; j ++) {
                board[i][j] = new Tile(false, i, j);
            }
        }
        setPlayer(player.getRow(), player.getCol());
    }


    public Board(ArrayList<Player> players, TiledMapManager manager, int height, int width) {
        this.players = players;
        this.height = height;
        this.width = width;

        initializeBoard(players, height, width);
        buildWalls(manager);
    }

    public void initializeBoard(ArrayList<Player> players, int height, int width) {
        board = new Tile[height][width];
        for (int i = 0; i < height; i ++){
            for (int j = 0; j < width; j ++) {
                board[i][j] = new Tile(false, i, j);
            }
        }
        for(Player player : players){
            setPlayer(player.getRow(), player.getCol());
        }
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
            }
        }
    }

    public void setPlayer(int row, int col){
        if (!outOfBounds(row, col)){
            board[row][col].setOccupied(true);
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
        if (card.getMoveID() == -1) {
            moveBackwards(player);
        }
        else {
            moveForward(player, card.getMoveID());
        }
    }

    private void moveBackwards(Player player) {
        int newRow = player.getRow() - player.getRowTrajectory();
        int newCol = player.getCol() - player.getColumnTrajectory();
        if (!outOfBounds(newRow, newCol)) {
            board[player.getRow()][player.getCol()].setOccupied(false);
            player.setRow(newRow);
            player.setCol(newCol);
            board[newRow][newCol].setOccupied(true);
        }
        else { System.out.println("Cannot move out of bounds"); }
    }

    private void moveForward(Player player, int numOfMoves) {
        for (int i = 0; i < numOfMoves; i++) {
            int newRow = player.getRow() + player.getRowTrajectory();
            int newCol = player.getCol() + player.getColumnTrajectory();
            if(!outOfBounds(newRow, newCol) && !forwardCollision(player)){
                board[player.getRow()][player.getCol()].setOccupied(false);
                player.setRow(newRow);
                player.setCol(newCol);
                board[newRow][newCol].setOccupied(true);
            }
            else {
                System.out.println("Cannot move out of bounds");
                break;
            }
        }
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Tile getTile(int row, int col){
        return board[row][col];
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int i){
        return players.get(i);
    }

    private Boolean outOfBounds(int row, int col) {
        return row < 0 || col < 0 || row >= height || col >= width;
    }

    private Boolean forwardCollision(Player player) {
        return board[player.getRow()][player.getCol()].getWalls().contains(player.getDirection());
    }
        public Boolean isOccupied(Tile tile){
            return tile.getOccupied();
        }

}
