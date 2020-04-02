package inf112.skeleton.app;

//Might be useful when implementing collisions and tile dynamics

import java.util.ArrayList;

public class Tile {

    private boolean occupied;
    private final int row;
    private final int col;
    private ArrayList<Direction> walls = new ArrayList<>();
    private boolean isHole;

    public Tile(boolean occupied, int row, int col) {
        this.occupied = occupied;
        this.row = row;
        this.col = col;
        this.isHole = false;
    }

    public void buildWall(Direction direction) {
        walls.add(direction);
    }

    public ArrayList<Direction> getWalls() {
        return walls;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void setHole(boolean isHole) {
        this.isHole = isHole;
    }

    public boolean isHole() {
        return isHole;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
