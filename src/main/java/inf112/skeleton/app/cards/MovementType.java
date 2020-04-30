package inf112.skeleton.app.cards;

public enum MovementType implements CardType {

    ONE_FORWARD,
    TWO_FORWARD,
    THREE_FORWARD,
    ONE_BACKWARD;

    public int getMoveID() {
        if (this.equals(ONE_BACKWARD)) {
            return -1;
        } else return this.ordinal() + 1;
    }

    public String toString() {
        switch (this) {
            case ONE_FORWARD:
                return "move1";
            case TWO_FORWARD:
                return "move2";
            case THREE_FORWARD:
                return "move3";
            case ONE_BACKWARD:
                return "moveBack";
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
