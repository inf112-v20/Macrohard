package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.managers.TiledMapManager;
import inf112.skeleton.app.tiles.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Board {

    private final int height;
    private final int width;

    private Tile[][] board;
    private ArrayList<Dock> docks;
    private ArrayList<Player> players;
    private ArrayList<Laser> lasers;
    private LinkedList<ConveyorBelt> queuedConveyorBelts;

    private int numberOfFlags = 0;

    //Graphic-independent constructor for test-classes
    public Board(int height, int width, Player... players) {
        this.players = new ArrayList<>(Arrays.asList(players));
        this.docks = new ArrayList<>();
        this.lasers = new ArrayList<>();
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
        this.docks = new ArrayList<>();
        this.lasers = new ArrayList<>();
        this.height = mapManager.getHeight();
        this.width = mapManager.getWidth();

        layTiles(mapManager);
        erectWalls(mapManager);
        dockPlayers(players);
        placeFlags(mapManager);
    }

    private void set(Player player) {
        getTile(player).setPlayer(player);
    }

    public void dockPlayers(ArrayList<Player> players) {
        int playerIndex = 0;
        while (playerIndex < Math.min(players.size(), docks.size())) {
            Player player = players.get(playerIndex);
            Dock dock = docks.get(playerIndex);
            dock.setPlayer(player);
            player.setArchiveMarker(dock);
            player.setRow(dock.getRow());
            player.setCol(dock.getCol());
            playerIndex++;
        }
    }

    public void layTile(Tile tile) {
        Tile temp = board[tile.getRow()][tile.getCol()];
        if (temp != null && temp.isOccupied()) {
            tile.setPlayer(temp.getPlayer());
        }
        board[tile.getRow()][tile.getCol()] = tile;
    }

    //Fills the board with standard tiles
    public void layTiles() {
        board = new Tile[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                layTile(new Tile(i, j));
            }
        }
    }

    //Fills the board with tiles in accordance with the mapManager
    public void layTiles(TiledMapManager mapManager) {
        board = new Tile[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                TiledMapTile tile = mapManager.getCell("TILES", row, col).getTile();
                String tileType = (String) tile.getProperties().get("Type");
                Tile currentTile;
                switch (tileType) {
                    case "GEAR":
                        boolean clockwise = (boolean) tile.getProperties().get("Clockwise");
                        currentTile = new Gear(row, col, clockwise);
                        break;
                    case "HOLE":
                        currentTile = new Hole(row, col);
                        break;
                    case "DOCK":
                        int number = (Integer) tile.getProperties().get("Number");
                        currentTile = new Dock(number, row, col);
                        docks.add((Dock) currentTile);
                        break;
                    case "CONVEYOR_BELT":
                        currentTile = installConveyorBelt(mapManager, row, col);
                        break;
                    case "REPAIR_SITE":
                        currentTile = new RepairSite(row, col);
                        break;
                    default:
                        currentTile = new Tile(row, col);
                        break;
                }
                layTile(currentTile);
            }
        }
        Collections.sort(docks);
    }

    private void erectWalls(TiledMapManager manager) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (manager.getCell("WALLS", row, col) != null) {
                    TiledMapTile wall = manager.getCell("WALLS", row, col).getTile();
                    String[] directions = ((String) wall.getProperties().get("Directions")).split(",");
                    for (String string : directions) {
                        Direction direction = Direction.fromString(string);
                        erectWall(direction, row, col);
                        int nrOfLasers = (Integer) wall.getProperties().get("NrOfLasers");
                        if (nrOfLasers > 0) {
                            installLaser(nrOfLasers, direction.opposite(), row, col);
                        }
                    }
                }
            }
        }
    }

    public void erectWall(Direction direction, int row, int col) {
        Tile tile = getTile(row, col);
        tile.getWalls().add(direction);
        if (!outOfBounds(tile, direction)) {
            Tile nextTile = getNextTile(tile, direction);
            nextTile.getWalls().add(direction.opposite());
        }
    }

    private void installLaser(int nrOfLasers, Direction direction, int row, int col) {
        lasers.add(new Laser(row, col, nrOfLasers, direction));
    }

    private ConveyorBelt installConveyorBelt(TiledMapManager mapManager, int row, int col) {
        TiledMapTileLayer.Cell belt = mapManager.getCell("TILES", row, col);
        String value = (String) belt.getTile().getProperties().get("Direction");
        Direction dir = Direction.fromString(value);
        boolean express = (boolean) belt.getTile().getProperties().get("Express");
        return new ConveyorBelt(row, col, dir, express);
    }

    private void placeFlags(TiledMapManager manager) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (manager.getCell("FLAGS", row, col) != null) {
                    int number = (Integer) manager.getCell("FLAGS", row, col).getTile().getProperties().get("Number");
                    Flag flag = new Flag(number, row, col);
                    layTile(flag);
                    numberOfFlags++;
                }
            }
        }
    }

    public void execute(Player player, Card card) {
        if (card instanceof RotationCard) {
            rotate(player, (RotationCard) card);
        } else if (card instanceof MovementCard) {
            move(player, (MovementCard) card);
        }
    }

    private void rotate(Player player, RotationCard card) {
        if (!player.isDestroyed()) {
            Direction newDir = card.getNewDirection(player.getDirection());
            player.setDirection(newDir);
        }

    }

    private void move(Player player, MovementCard card) {
        int roof = (card.getMoveID() == -1 ? 1 : card.getMoveID());
        Direction direction = (card.getMoveID() == -1 ? player.getDirection().opposite() : player.getDirection());

        for (int i = 0; i < roof; i++) {
            if (legalStep(player, direction)) {
                stepOne(player, direction);
            } else {
                break;
            }
        }
    }

    public void fireBoardLasers() {
        for (Laser laser : lasers) {
            Tile targetTile = getLaserTarget(getTile(laser), laser.getDirection());
            if (targetTile.isOccupied()) {
                targetTile.getPlayer().applyDamage(laser.getDamage());
            }
        }
    }

    public ArrayList<LinkedList<Tile>> firePlayerLasers() {
        ArrayList<LinkedList<Tile>> laserBeamTiles = new ArrayList<>();
        LinkedList<Player> damagedPlayers = new LinkedList<>();
        for (Player player : players) {
            if (!player.isDestroyed()) {
                LinkedList<Tile> laserTrajectory = getPlayerLaserBeam(getTile(player), player.getDirection());
                laserBeamTiles.add(laserTrajectory);
                Tile targetTile = laserTrajectory.getLast();
                if (targetTile.isOccupied() && !targetTile.getPlayer().equals(player)) {
                    damagedPlayers.add(targetTile.getPlayer());
                }
            }
        }
        // Damage applied simultaneously to all players
        // to avoid players being destroyed before firing own laser
        for (Player player : damagedPlayers) {
            player.applyDamage(1);
        }
        return laserBeamTiles;
    }

    public boolean rotateGears() {
        boolean used = false;
        for (Tile[] row : board) {
            for (Tile tile : row) {
                if (tile instanceof Gear && tile.isOccupied()) {
                    used = true;
                    Gear gear = (Gear) tile;
                    Player player = gear.getPlayer();
                    if (gear.rotatesClockwise()) {
                        player.rotateClockwise();
                    } else {
                        player.rotateCounterClockwise();
                    }
                }
            }
        }
        return used;
    }

    public Tile getTile(Laser laser) {
        return getTile(laser.getRow(), laser.getCol());
    }

    private Tile getLaserTarget(Tile tile, Direction direction) {
        if (isOccupied(tile) || wallCollision(tile, direction) || outOfBounds(tile, direction)) {
            return tile;
        } else {
            return getLaserTarget(getNextTile(tile, direction), direction);
        }
    }

    private LinkedList<Tile> getPlayerLaserBeam(Tile fromTile, Direction inDirection) {
        LinkedList<Tile> beam = new LinkedList<>();
        beam.add(fromTile);
        Tile currentTile = fromTile;
        while (!wallCollision(currentTile, inDirection) && !outOfBounds(currentTile, inDirection)) {
            currentTile = getNextTile(currentTile, inDirection);
            beam.add(currentTile);
            if (currentTile.isOccupied()) {
                break;
            }
        }
        return beam;
    }

    /**
     * This method finds occupied conveyor belts and queues them for movement
     * to ensure that no two players occupies the same tile after conveyors are moved.
     *
     * @param expressOnly true if only express conveyor belts are to be queued up for movement
     * @return players queued for movement
     */
    private ArrayList<Player> queueConveyorBelts(boolean expressOnly) {
        queuedConveyorBelts = new LinkedList<>();
        ArrayList<Tile> targetTiles = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        for (Tile[] row : board) {
            for (Tile tile : row) {
                if (tile instanceof ConveyorBelt && tile.isOccupied()) {
                    ConveyorBelt belt = (ConveyorBelt) tile;
                    if ((!expressOnly || belt.isExpress()) && legalRoll(belt, belt.getDirection(), expressOnly)) {
                        Tile targetTile = getNextTile(belt, belt.getDirection());
                        int index = targetTiles.indexOf(targetTile);
                        if (targetTiles.contains(targetTile)) {
                            queuedConveyorBelts.remove(index);
                            players.remove(index);
                        } else {
                            queuedConveyorBelts.add(belt);
                            players.add(belt.getPlayer());
                            targetTiles.add(targetTile);
                        }
                    }
                }
            }
        }
        return players;
    }

    /**
     * Move players queued for movement.
     * If there are no players waiting to move, we return false to avoid playing the sound effect.
     *
     * @param expressOnly true if only express conveyor belts are to be queued up for movement
     * @return False if no players are queued for movement
     */
    public boolean rollConveyorBelts(boolean expressOnly) {
        ArrayList<Player> rollingPlayers = queueConveyorBelts(expressOnly);
        int indexOfPlayer = 0;
        while (!queuedConveyorBelts.isEmpty()) {
            ConveyorBelt belt = queuedConveyorBelts.poll();
            Player beltPlayer = rollingPlayers.get(indexOfPlayer);
            roll(belt, beltPlayer);
            indexOfPlayer++;
        }
        return !rollingPlayers.isEmpty();
    }

    public void touchBoardElements() {
        for (Player player : players) {
            if (!player.isDestroyed()) {
                Tile tile = getTile(player);
                if (tile instanceof Flag || tile instanceof RepairSite) {
                    player.repair();
                    player.setArchiveMarker(tile);
                    if (tile instanceof Flag && ((Flag) tile).getNumber() == player.getNextFlag()) {
                        player.touchFlag();
                    }
                }
            }
        }
    }

    private void roll(ConveyorBelt belt, Player player) {
        Direction direction = belt.getDirection();
        Tile toTile = getNextTile(belt, direction);

        //Row and column of player is updated
        player.stepIn(direction);

        //Queue respawn for rolling player if player has rolled into a hole
        if (toTile instanceof Hole) {
            player.destroy();
        } else {
            //Rotate player if the conveyor belt swings either left or right
            //Assumes that there are no two conveyor belts pointing into each other
            if (getTile(player) instanceof ConveyorBelt) {
                ConveyorBelt nextBelt = (ConveyorBelt) getTile(player);
                if (!direction.equals(nextBelt.getDirection())) {
                    if (direction.turnClockwise().equals(nextBelt.getDirection())) {
                        player.rotateClockwise();
                    } else if (direction.turnCounterClockwise().equals(nextBelt.getDirection())) {
                        player.rotateCounterClockwise();
                    }
                }
            }
            toTile.setPlayer(player);
        }

        //Update the Tile->Player-relation now that player is moved
        if (belt.isOccupied() && belt.getPlayer().equals(player)) {
            //Since all conveyor belts roll simultaneously,
            //be sure not to overwrite any other players potentially occupying the belt
            belt.setPlayer(null);
        }
    }

    public void stepOne(Player player, Direction direction) {
        Tile fromTile = getTile(player);
        Tile toTile = getNextTile(fromTile, direction);

        if (toTile.isOccupied()) {
            stepOne(toTile.getPlayer(), direction);
        }

        player.stepIn(direction);

        if (toTile instanceof Hole) {
            player.destroy();
            player.destroyInFall();
        } else {
            toTile.setPlayer(player);
        }

        if (fromTile.isOccupied() && fromTile.getPlayer().equals(player)) {
            fromTile.setPlayer(null);
        }

    }

    public boolean legalStep(Player player, Direction direction) {
        Tile fromTile = getTile(player);
        if (player.isDestroyed() || outOfBounds(fromTile, direction) || wallCollision(getTile(player), direction)) {
            return false;
        }
        Tile toTile = getNextTile(fromTile, direction);
        if (!toTile.isOccupied()) {
            return true;
        }
        // Else check recursively if player on target tile can
        // legally be pushed in same direction
        else {
            return legalStep(toTile.getPlayer(), direction);
        }
    }

    public boolean legalRoll(Tile tile, Direction direction, boolean expressOnly) {
        Player player = tile.getPlayer();
        Tile fromTile = getTile(player);
        if (outOfBounds(fromTile, direction) || wallCollision(tile, direction)) {
            return false;
        }
        Tile toTile = getNextTile(fromTile, direction);
        if (!toTile.isOccupied()) {
            return true;
        } else if (toTile instanceof ConveyorBelt) {
            // If target tile is an occupied conveyor belt
            // check if the player on the belt will roll at the same time,
            // thus freeing the tile
            ConveyorBelt toBelt = (ConveyorBelt) toTile;
            if (!expressOnly || toBelt.isExpress()) {
                // This recursive call will result in an infinite loop in very rare circumstances
                return legalRoll(toBelt, toBelt.getDirection(), expressOnly);
            }
        }
        // Else, robot is not allowed to roll, since you can't push other robots when you roll
        return false;
    }

    private boolean outOfBounds(int row, int col) {
        return row < 0 || col < 0 || row >= height || col >= width;
    }

    private boolean outOfBounds(Tile tile, Direction direction) {
        int newRow = tile.getRow() + direction.getRowModifier();
        int newCol = tile.getCol() + direction.getColumnModifier();
        return outOfBounds(newRow, newCol);
    }

    private boolean wallCollision(Tile tile, Direction direction) {
        return tile.getWalls().contains(direction);
    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    public Tile getTile(Player player) {
        return getTile(player.getRow(), player.getCol());
    }

    public Tile getNextTile(Tile tile, Direction direction) {
        return board[tile.getRow() + direction.getRowModifier()][tile.getCol() + direction.getColumnModifier()];
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }

    public boolean isOccupied(Tile tile) {
        return tile.isOccupied();
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }
}
