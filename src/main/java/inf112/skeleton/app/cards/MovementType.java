package inf112.skeleton.app.cards;

public enum MovementType {

    ONE_FORWARD,
    TWO_FORWARD,
    THREE_FORWARD,
    ONE_BACKWARD;

    public int getMoveID() {
        if (this.equals(ONE_BACKWARD)) {
            return -1;
        }
        else {
            return this.ordinal() + 1;
        }
    }

}
