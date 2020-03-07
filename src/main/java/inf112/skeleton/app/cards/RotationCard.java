package inf112.skeleton.app.cards;

import inf112.skeleton.app.Direction;

public class RotationCard extends Card {

    private final RotationType rotationType;

    public RotationCard(int priority, RotationType rotationType) {
        super(priority, rotationType);
        this.rotationType = rotationType;
    }

    public Direction getNewDirection(Direction direction) {
        if (this.rotationType.equals(RotationType.CLOCKWISE)) {
            return direction.turnClockwise();
        }
        else if (this.rotationType.equals(RotationType.COUNTER_CLOCKWISE)) {
            return direction.turnCounterClockwise();
        }
        else {
            return direction.turnClockwise().turnClockwise();
        }
    }

    @Override
    public String toString() {
        return "RotationCard{" +
                "rotationType=" + rotationType +
                ", priority=" + priority +
                '}';
    }

}
