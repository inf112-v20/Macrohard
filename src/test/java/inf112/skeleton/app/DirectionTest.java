package inf112.skeleton.app;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectionTest {

    @Test
    public void numberOfDirectionsEqualsToFour() {
        Assert.assertEquals(4, Direction.values().length);
    }

    @Test
    public void turningClockwiseFromNorthYieldsEast() {
        Direction north = Direction.NORTH;

        Direction newDir = north.turnClockwise();

        Assert.assertEquals(Direction.EAST, newDir);
    }

    @Test
    public void turningCounterClockwiseFromNorthYieldsWest() {
        Direction north = Direction.NORTH;

        Direction newDir = north.turnCounterClockwise();

        Assert.assertEquals(Direction.WEST, newDir);
    }

    @Test
    public void uTurningFromNorthYieldsSouth() {
        Direction north = Direction.NORTH;

        Direction newDir = north.turnCounterClockwise().turnCounterClockwise();

        Assert.assertEquals(Direction.SOUTH, newDir);
    }

    @Test
    public void turningClockwiseFromWestYieldsNorth() {
        Direction north = Direction.WEST;

        Direction newDir = north.turnClockwise();

        Assert.assertEquals(Direction.NORTH, newDir);
    }

    @Test
    public void turningCounterClockwiseFromWestYieldsSouth() {
        Direction north = Direction.WEST;

        Direction newDir = north.turnCounterClockwise();

        Assert.assertEquals(Direction.SOUTH, newDir);
    }

    @Test
    public void uTurningFromWestYieldsEast() {
        Direction north = Direction.WEST;

        Direction newDir = north.turnCounterClockwise().turnCounterClockwise();

        Assert.assertEquals(Direction.EAST, newDir);
    }

}