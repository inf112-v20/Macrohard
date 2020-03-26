package inf112.skeleton.app;

//Might be useful when implementing collisions and tile dynamics

import java.util.ArrayList;

public class Tile {

    private boolean occupied;
    private int row;
    private int col;
    private ArrayList<Direction> walls = new ArrayList<>();

    public Tile(boolean occupied, int row, int col) {
        this.occupied = occupied;
        this.row = row;
        this.col = col;
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

}
