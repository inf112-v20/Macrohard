package inf112.skeleton.app.tiles;

import inf112.skeleton.app.Direction;

public class ConveyorBelt extends Tile {

    private final Direction direction;
    private final boolean express;

    public ConveyorBelt(int row, int col, Direction direction, boolean express) {
        super(row, col);
        this.direction = direction;
        this.express = express;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isExpress() {
        return express;
    }
}
