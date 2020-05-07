package inf112.skeleton.app.tiles;

import inf112.skeleton.app.Direction;

public class Laser {

    private final int row;
    private final int col;
    private final int damage;
    private final Direction direction;

    public Laser(int row, int col, int damage, Direction direction) {
        this.row = row;
        this.col = col;
        this.damage = damage;
        this.direction = direction;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getDamage() {
        return damage;
    }

    public Direction getDirection() {
        return direction;
    }

}
