package inf112.skeleton.app.tiles;

public class Gear extends Tile {

    private final boolean clockwise;

    public Gear(int row, int col, boolean clockwise) {
        super(row, col);
        this.clockwise = clockwise;
    }

    public boolean rotatesClockwise() {
        return clockwise;
    }
}
