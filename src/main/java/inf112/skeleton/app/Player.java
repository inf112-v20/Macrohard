package inf112.skeleton.app;

public class Player {

    private int row;
    private int col;
    private Direction direction;

    public Player(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    public Direction getDirection () {
        return this.direction;
    }
}
