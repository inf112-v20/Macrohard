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

    public static Direction fromString(String string) throws IllegalArgumentException {
        switch (string) {
            case "NORTH": return NORTH;
            case "EAST": return EAST;
            case "SOUTH": return SOUTH;
            case "WEST": return WEST;
            default:
                throw new IllegalArgumentException(string + " is no direction.");
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case WEST: return "WEST";
            case EAST: return "EAST";
            case NORTH: return "NORTH";
            case SOUTH: return "SOUTH";
            default:
                return "";
        }
    }

    public Direction opposite() {
        return this.turnClockwise().turnClockwise();
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

    public static float getDegreesBetween(Direction from, Direction to) {
        if (from.opposite().equals(to)) {
            return (float) 180.0;
        } else if (from.turnClockwise().equals(to)) {
            return (float) -90.0;
        } else if (from.turnCounterClockwise().equals(to)) {
            return (float) 90.0;
        } else { return (float) 0.0; }
    }

}
