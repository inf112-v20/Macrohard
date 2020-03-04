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

    public int[] getMoveCoordinates() {
        if (this.equals(NORTH)) {
            return new int[] {1, 0};
        }
        else if (this.equals(EAST)) {
            return new int[] {0, 1};
        }
        else if (this.equals(SOUTH)) {
            return new int[] {-1, 0};
        }
        else {
            return new int[] {0, -1};
        }
    }

    public int getRowTrajectory() {
        return this.getMoveCoordinates()[0];
    }

    public int getColumnTrajectory() {
        return this.getMoveCoordinates()[1];
    }
}
