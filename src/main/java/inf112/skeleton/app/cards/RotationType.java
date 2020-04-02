package inf112.skeleton.app.cards;

public enum RotationType implements CardType {

    COUNTER_CLOCKWISE,
    CLOCKWISE,
    U_TURN;

    public String toString() {
        switch (this) {
            case COUNTER_CLOCKWISE: return "rotateCounterclockwise";
            case CLOCKWISE: return "rotateClockwise";
            case U_TURN: return "uTurn";
            default: return "";
        }
    }

}
