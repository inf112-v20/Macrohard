package inf112.skeleton.app;

public class RotationCard extends Card {

    private final RotationType rotationType;

    public RotationCard(int priority, RotationType rotationType) {
        super(priority);
        this.rotationType = rotationType;
    }

    public Direction getNewDirection(Direction direction) {
        if (this.rotationType.equals(RotationType.ROTATE_CLOCKWISE)) {
            return direction.turnClockwise();
        }
        else if (this.rotationType.equals(RotationType.ROTATE_COUNTER_CLOCKWISE)) {
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
