package inf112.skeleton.app.tiles;

public class Flag extends Tile {

    private final int number;

    public Flag(int number, int row, int col) {
        super(row, col);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return ("TYPE: Flag. NUMBER: " + this.number);
    }
}
