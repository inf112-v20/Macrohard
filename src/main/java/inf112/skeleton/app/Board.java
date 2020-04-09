package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.managers.TiledMapManager;
import inf112.skeleton.app.tiles.ConveyorBelt;
import inf112.skeleton.app.tiles.Hole;
import inf112.skeleton.app.tiles.Tile;

import java.util.ArrayList;
import java.util.LinkedList;

public class Board {

    private final int height;
    private final int width;

    private Tile[][] board;
    private ArrayList<Player> players;
    private LinkedList<ConveyorBelt> queuedConveyorBelts;

    //Graphic-independent constructor for test-classes
    public Board(ArrayList<Player> players, int height, int width) {
        this.players = players;
        this.height = height;
        this.width = width;

        layTiles();
        for (Player player : players) {
            set(player);
        }
    }

    //Graphic-dependent constructor for multiple players
    public Board(ArrayList<Player> players, TiledMapManager mapManager) {
        this.players = players;
        this.height = mapManager.getHeight();
        this.width = mapManager.getWidth();

        layTiles(mapManager);
        erectWalls(mapManager);

        for (Player player : players) { set(player); }
    }

    public void set(Player player) {
        board[player.getRow()][player.getCol()].setPlayer(player);
        player.setSpawnPoint(board[player.getRow()][player.getCol()]);
    }

    //Fills the board with standard tiles
    public void layTiles() {
        board = new Tile[height][width];
        for (int i = 0; i < height; i ++){
            for (int j = 0; j < width; j ++) {
                layTile(new Tile(i, j));
            }
        }
    }

    //Fills the board with tiles in accordance with the mapManager
    public void layTiles(TiledMapManager mapManager) {
        board = new Tile[height][width];
        for (int row = 0; row < height; row ++){
            for (int col = 0; col < width; col ++) {
                Tile currentTile;
                if (mapManager.getCell("HOLES", row, col) != null) {
                    currentTile = new Hole(row, col);
                }
                else if (mapManager.getCell("CONVEYOR_BELTS", row, col) != null) {
                    currentTile = installConveyorBelt(mapManager, row, col);
                }
                else {
                    currentTile = new Tile(row, col);
                }
                layTile(currentTile);
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

    private ConveyorBelt installConveyorBelt(TiledMapManager mapManager, int row, int col) {
        TiledMapTileLayer.Cell wall = mapManager.getCell("CONVEYOR_BELTS", row, col);
        String value = (String) wall.getTile().getProperties().get("Direction");
        Direction dir = Direction.fromString(value);
        boolean express = (wall.getTile().getProperties().get("Type")).equals("CONVEYOR_EXPRESS");
        return new ConveyorBelt(row, col, dir, express);

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
        Direction direction = (card.getMoveID() == -1? player.getDirection().opposite() : player.getDirection());

        for (int i = 0; i < roof; i ++) {
            if (legalStep(player, direction)) { stepOne(player, direction); } else { break; }
        }
    }

    private ArrayList<Player> queueConveyorBelts(boolean expressOnly) {
        queuedConveyorBelts = new LinkedList<>();
        ArrayList<Player> players = new ArrayList<>();
        for (Tile[] row : board) {
            for (Tile tile : row) {
                if (tile instanceof ConveyorBelt && tile.isOccupied()) {
                    ConveyorBelt belt = (ConveyorBelt) tile;
                    if (!expressOnly || belt.isExpress()) {
                        if (legalRoll(belt, expressOnly, belt.getDirection())) {
                            queuedConveyorBelts.add(belt);
                            players.add(belt.getPlayer());
                        }
                    }
                }
            }
        }
        return players;
    }

    public void rollConveyorBelts(boolean expressOnly) {
        ArrayList<Player> rollingPlayers = queueConveyorBelts(expressOnly);
        int indexOfPlayer = 0;
        while (!queuedConveyorBelts.isEmpty()) {
            ConveyorBelt belt = queuedConveyorBelts.poll();
            Player beltPlayer = rollingPlayers.get(indexOfPlayer);
            roll(belt, beltPlayer);
            indexOfPlayer ++;
        }
    }

    private void roll(ConveyorBelt belt, Player player) {
        Direction direction = belt.getDirection();
        Tile toTile = getAdjacentTile(belt, direction);

        //Move the player currently occupying the target tile, if such player exists
        if (toTile.isOccupied() && !queuedConveyorBelts.contains(toTile)) {
            stepOne(toTile.getPlayer(), direction);
        }

        //Respawn rolling player if player is rolled into a hole
        if (toTile instanceof Hole) {
            player.reSpawn();
            toTile = player.getSpawnPoint();
        }
        //Step one in direction of belt if otherwise
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

        //Update the Tile->Player-relation now that player is moved
        if (belt.isOccupied()) {
            //Since all conveyor belts roll simultaneously,
            //be sure not to overwrite any other players potentially occupying the belt
            if (belt.getPlayer().equals(player)) {
                belt.setPlayer(null);
            }
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
            return (noWallCollision(player, direction));
        }
        // Else check recursively if player on target tile can
        // legally be pushed in same direction
        else { return legalStep(toTile.getPlayer(), direction); }
    }

    public boolean legalRoll(ConveyorBelt belt, boolean expressOnly, Direction direction) {
        Player player = belt.getPlayer();
        if (outOfBounds(player, direction)) {
            return false;
        }
        Tile toTile = getAdjacentTile(getTile(player), direction);
        if (!toTile.isOccupied()) {
            return (noWallCollision(player, direction));
        }
        else if (toTile instanceof ConveyorBelt) {
            // If target tile is an occupied conveyor belt
            // check if the player on the belt will roll at the same time,
            // thus freeing the tile
            ConveyorBelt toBelt = (ConveyorBelt) toTile;
            if (!expressOnly || toBelt.isExpress()) {
                // This recursive call will result in an infinite loop in very rare circumstances
                if (legalRoll(toBelt, expressOnly, toBelt.getDirection())) {
                    return true;
                }
            }
        }
        // Else check if next player can be legally pushed
        return legalStep(toTile.getPlayer(), direction);
    }

    private boolean outOfBounds(int row, int col) {
        return row < 0 || col < 0 || row >= height || col >= width;
    }

    private boolean outOfBounds(Player player, Direction direction) {
        int newRow = player.getRow() + direction.getRowTrajectory();
        int newCol = player.getCol() + direction.getColumnTrajectory();
        return newRow < 0 || newCol < 0 || newRow >= height || newCol >= width;
    }

    private boolean noWallCollision(Player player, Direction direction) {
        return !getTile(player).getWalls().contains(direction);
    }

    public Tile getTile(int row, int col){
        return board[row][col];
    }

    public Tile getTile(Player player) {
        return getTile(player.getRow(), player.getCol());
    }

    public void layTile(Tile tile) {
        board[tile.getRow()][tile.getCol()] = tile;
    }

    public Tile getAdjacentTile(Tile tile, Direction direction) {
        return board[tile.getRow() + direction.getRowTrajectory()][tile.getCol() + direction.getColumnTrajectory()];
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isOccupied(Tile tile){
            return tile.isOccupied();
        }

}
