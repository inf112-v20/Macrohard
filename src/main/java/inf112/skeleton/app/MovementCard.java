package inf112.skeleton.app;

public class MovementCard extends Card {

    private final int numberOfMoves;

    public MovementCard(int priority, int numberOfMoves){
        super(priority);
        this.numberOfMoves = numberOfMoves;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }
}
