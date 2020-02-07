package inf112.skeleton.app;
/*
kanskje tile kan være en superclass av alle tiles, slik at vi slipper mye
 */
public class Tile {

    //Status tells us if a tile has a player on it, or if there is a wall blocking access
    private boolean occupied;

    private int row;

    private int col;


    public Tile(boolean occupied, int row, int col) {
        this.occupied = occupied;
        this.row = row;
        this.col = col;
    }

    public void isOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
