package inf112.skeleton.app.cards;

public class MovementCard extends Card {

    private MovementType movementType;

    public MovementCard(int priority, MovementType movementType){
        super(priority, movementType);
        this.movementType = movementType;
    }

    public int getMoveID() {
        return movementType.getMoveID();
    }

    @Override
    public String toString() {

        return "MovementCard{" + "priority=" + priority + ", numberOfMoves=" + getMoveID() +
                '}';
    }
}
