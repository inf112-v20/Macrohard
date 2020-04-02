package inf112.skeleton.app.tiles;

import inf112.skeleton.app.Direction;

public class ConveyorBelt extends Tile {

    private final Direction direction;

    public ConveyorBelt(boolean occupied, int row, int col, Direction direction) {
        super(occupied, row, col);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

}
