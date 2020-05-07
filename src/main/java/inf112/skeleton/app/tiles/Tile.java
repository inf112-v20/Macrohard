package inf112.skeleton.app.tiles;

import inf112.skeleton.app.Direction;
import inf112.skeleton.app.Player;

import java.util.ArrayList;

public class Tile {

    private Player player;
    private final int row;
    private final int col;
    private ArrayList<Direction> walls = new ArrayList<>();

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public ArrayList<Direction> getWalls() {
        return walls;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isOccupied() {
        return player != null;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
