package inf112.skeleton.app;

//Might be useful when implementing collisions and tile dynamics

public class Tile {

    private boolean occupied;
    private int row;
    private int col;

    public Tile(boolean occupied, int row, int col) {
        this.occupied = occupied;
        this.row = row;
        this.col = col;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

}
