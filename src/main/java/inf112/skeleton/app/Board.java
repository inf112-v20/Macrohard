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
    private ArrayList<Player> players;

    //Graphic-independent constructor for test-classes
    public Board(ArrayList<Player> players, int height, int width) {
        this.players = players;
        this.height = height;
        this.width = width;

        initializeBoard(height, width);
        for (Player player : players) {
            set(player);
        }
    }

    //Constructor for multiple players
    public Board(ArrayList<Player> players, TiledMapManager mapManager, int height, int width) {
        this.players = players;
        this.height = height;
        this.width = width;

        initializeBoard(height, width);
        installConveyorBelts(mapManager);
        digHoles(mapManager);
        erectWalls(mapManager);

        for (Player player : players) { set(player); }
    }

    void set(Player player) {
        board[player.getRow()][player.getCol()].setPlayer(player);
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

    private void digHoles(TiledMapManager mapManager) {
        for (int row = 0; row < height; row ++) {
            for (int col = 0; col < width; col ++) {
                if (mapManager.getCell("HOLES", row, col) != null) {
                    board[row][col] = new Hole(row, col);
                }
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
                    boolean express = (wall.getTile().getProperties().get("Type")).equals("CONVEYOR_EXPRESS");
                    board[row][col] = new ConveyorBelt(row, col, dir, express);
                }
            }
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
            if (legalStep(player, direction)) { stepOne(player, direction); } else { break; }
        }
    }

    public void rollConveyorBelts(boolean expressOnly) {
        ArrayList<ConveyorBelt> belts = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        for (int row = 0; row < height; row ++) {
            for (int col = 0; col < width; col ++) {
                if (board[row][col] instanceof ConveyorBelt && board[row][col].isOccupied()) {
                    ConveyorBelt belt = (ConveyorBelt) board[row][col];
                    if (!expressOnly || belt.isExpress()) {
                        belts.add(belt);
                        players.add(belt.getPlayer());
                    }
                }
            }
        }
        for (int i = 0; i < belts.size(); i ++) {
            ConveyorBelt belt = belts.get(i);
            Player beltPlayer = players.get(i);
            if (legalRoll(belt, expressOnly, belt.getDirection())) {
                roll(belt, beltPlayer, expressOnly);
            }
        }
    }

    private void roll(ConveyorBelt belt, Player player, boolean express) {
        Direction direction = belt.getDirection();
        Tile toTile = getAdjacentTile(belt, direction);

        //Move next player
        if (toTile.isOccupied()) {
            if (!(toTile instanceof ConveyorBelt)) {
                stepOne(toTile.getPlayer(), direction);
            }
            else {
                ConveyorBelt nextBelt = (ConveyorBelt) toTile;
                if (express && !nextBelt.isExpress()) {
                    stepOne(toTile.getPlayer(), direction);
                }
            }
        }

        //Respawn player if player is rolled into a hole, step if otherwise
        if (toTile instanceof Hole) {
            player.reSpawn();
            toTile = player.getSpawnPoint();
        }
        else {
            player.stepIn(direction);
            //Rotate player if the conveyor belt swings either left or right
            //Assumes that there are no two conveyor belts pointing into each other
            if (getTile(player) instanceof ConveyorBelt) {
                ConveyorBelt nextBelt = (ConveyorBelt) getTile(player);
                if (!direction.equals(nextBelt.getDirection())) {
                    if (direction.turnClockwise().equals(nextBelt.getDirection())) {
                        player.turnClockwise();
                    }
                    else if (direction.turnCounterClockwise().equals(nextBelt.getDirection())) {
                        player.turnCounterClockwise();
                    }
                }
            }
        }

        if (belt.isOccupied() && belt.getPlayer().equals(player)) {
            belt.setPlayer(null);
        }
        toTile.setPlayer(player);
    }

    public void stepOne(Player player, Direction direction) {
        Tile fromTile = getTile(player);
        Tile toTile = getAdjacentTile(fromTile, direction);
        if (toTile.isOccupied()) {
            stepOne(toTile.getPlayer(), direction);
        }
        if (toTile instanceof Hole) {
            player.reSpawn();
            toTile = player.getSpawnPoint();
        }
        else {
            player.stepIn(direction);
        }
        if (fromTile.isOccupied() && fromTile.getPlayer().equals(player)) {
            fromTile.setPlayer(null);
        }
        toTile.setPlayer(player);
    }

    public boolean legalStep(Player player, Direction direction) {
        if (outOfBounds(player, direction)) {
            return false;
        }
        Tile toTile = getAdjacentTile(getTile(player), direction);
        if (!toTile.isOccupied()) {
            return (!collision(player, direction));
        }
        else { return legalStep(toTile.getPlayer(), direction); }
    }

    public boolean legalRoll(ConveyorBelt belt, boolean expressOnly, Direction direction) {
        Player player = belt.getPlayer();
        if (outOfBounds(player, direction)) {
            return false;
        }
        Tile toTile = getAdjacentTile(getTile(player), direction);
        if (!toTile.isOccupied()) {
            return (!collision(player, direction));
        }
        else if (toTile instanceof ConveyorBelt) {
            ConveyorBelt toBelt = (ConveyorBelt) toTile;
            if (!expressOnly || toBelt.isExpress()) {
                return legalRoll(toBelt, expressOnly, toBelt.getDirection());
            }
            else {
                return legalStep(toBelt.getPlayer(), direction);
            }
        }
        else {
            return legalStep(toTile.getPlayer(), direction);
        }
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
        return getTile(player).getWalls().contains(direction);
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Tile getTile(int row, int col){
        return board[row][col];
    }

    public Tile getTile(Player player) {
        return getTile(player.getRow(), player.getCol());
    }

    public Tile getAdjacentTile(Tile tile, Direction direction) {
        return board[tile.getRow() + direction.getRowTrajectory()][tile.getCol() + direction.getColumnTrajectory()];
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int i){
        return players.get(i);
    }

    public boolean isOccupied(Tile tile){
            return tile.isOccupied();
        }

}
