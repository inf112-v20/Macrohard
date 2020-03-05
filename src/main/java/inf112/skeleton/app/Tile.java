package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

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

    public boolean getOccupied() {
        return occupied;
    }

    public int getRow() {
        return row;
    }

    public int getCol() { return col; }

    public static String getType(TiledMapTileLayer.Cell cell) {
        return (String) cell.getTile().getProperties().get("Type");
    }

}
