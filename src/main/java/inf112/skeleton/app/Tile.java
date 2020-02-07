package inf112.skeleton.app;
/*
kanskje tile kan være en superclass av alle tiles, slik at vi slipper mye
 */
public class Tile {
    //Status tells us if a tile has a player on it, or if there is a wall blocking access
    private int status;
    //Type has a value, where the number is interpreted as a type of tile, i.e. 0 = normal floor tile
    private int type;

    private int row;

    private int col;


    public Tile(int status, int type, int row, int col) {
        this.status = status;
        this.type = type;
        this.row = row;
        this.col = col;
    }

    //TODO: Find correct implementation, currently updates all tiles' status to 1
    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString(){//overriding the toString() method
        return "Status: " + this.status + " Type: " + this.type;
}
}
