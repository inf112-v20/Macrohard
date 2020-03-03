package inf112.skeleton.app;

import inf112.skeleton.app.cards.RotationCard;
import inf112.skeleton.app.cards.RotationType;
import org.junit.Assert;
import org.junit.Test;

public class RotationCardTest {

    @Test
    public void rotateCounterClockwiseCardFromNorthYieldsWest() {
        RotationCard card = new RotationCard(1, RotationType.ROTATE_COUNTER_CLOCKWISE);

        Direction newDir = card.getNewDirection(Direction.NORTH);

        Assert.assertEquals(Direction.WEST, newDir);
    }

    @Test
    public void rotateClockwiseCardFromNorthYieldsEast() {
        RotationCard card = new RotationCard(1, RotationType.ROTATE_CLOCKWISE);

        Direction newDir = card.getNewDirection(Direction.NORTH);

        Assert.assertEquals(Direction.EAST, newDir);
    }

    @Test
    public void uRotateFromNorthYieldsSouth() {
        RotationCard card = new RotationCard(1, RotationType.ROTATE_U);

        Direction newDir = card.getNewDirection(Direction.NORTH);

        Assert.assertEquals(Direction.SOUTH, newDir);
    }

    @Test
    public void rotateCounterClockwiseCardFromSouthYieldsEast() {
        RotationCard card = new RotationCard(1, RotationType.ROTATE_COUNTER_CLOCKWISE);

        Direction newDir = card.getNewDirection(Direction.SOUTH);

        Assert.assertEquals(Direction.EAST, newDir);
    }

    @Test
    public void rotateClockwiseCardFromSouthYieldsWest() {
        RotationCard card = new RotationCard(1, RotationType.ROTATE_CLOCKWISE);

        Direction newDir = card.getNewDirection(Direction.SOUTH);

        Assert.assertEquals(Direction.WEST, newDir);
    }

    @Test
    public void uRotateFromSouthYieldsNorth() {
        RotationCard card = new RotationCard(1, RotationType.ROTATE_U);

        Direction newDir = card.getNewDirection(Direction.SOUTH);

        Assert.assertEquals(Direction.NORTH, newDir);
    }

}