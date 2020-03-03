package inf112.skeleton.app;

public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    private static Direction[] directions = values();

    public Direction turnClockwise() {
        return directions[(this.ordinal() + 1) % directions.length];
    }

    public Direction turnCounterClockwise() {
        if (this.equals(NORTH)) {
            return WEST;
        }
        return directions[(this.ordinal() - 1) % directions.length];
    }
}
