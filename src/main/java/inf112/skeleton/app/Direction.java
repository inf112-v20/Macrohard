package inf112.skeleton.app;

import java.util.Random;

public enum Direction {

    NORTH,
    EAST,
    SOUTH,
    WEST;

    private static Direction[] directions = values();

    public static Direction any() {
        Random random = new Random();
        return directions[random.nextInt(4)];
    }

    public Direction turnClockwise() {
        return directions[(this.ordinal() + 1) % directions.length];
    }

    public Direction turnCounterClockwise() {
        if (this.equals(NORTH)) {
            return WEST;
        }
        return directions[(this.ordinal() - 1) % directions.length];
    }

    public Direction opposite() {
        return this.turnClockwise().turnClockwise();
    }

    public static Direction fromString(String string) throws IllegalArgumentException {
        switch (string) {
            case "NORTH":
                return NORTH;
            case "EAST":
                return EAST;
            case "SOUTH":
                return SOUTH;
            case "WEST":
                return WEST;
            default:
                throw new IllegalArgumentException(string + " is no direction.");
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case WEST:
                return "WEST";
            case EAST:
                return "EAST";
            case NORTH:
                return "NORTH";
            case SOUTH:
                return "SOUTH";
            default:
                return "";
        }
    }


    public int getRowModifier() {
        return this.getCoordinateModifiers()[0];
    }

    public int getColumnModifier() {
        return this.getCoordinateModifiers()[1];
    }

    public int[] getCoordinateModifiers() {
        if (this.equals(NORTH)) {
            return new int[]{1, 0};
        } else if (this.equals(EAST)) {
            return new int[]{0, 1};
        } else if (this.equals(SOUTH)) {
            return new int[]{-1, 0};
        } else {
            return new int[]{0, -1};
        }
    }


}
