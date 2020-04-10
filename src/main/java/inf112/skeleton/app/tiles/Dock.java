package inf112.skeleton.app.tiles;

public class Dock extends Tile implements Comparable<Dock> {

    private final int number;

    public Dock(int number, int row, int col) {
        super(row, col);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public int compareTo(Dock other) {
        return this.number - other.getNumber();
    }
}
