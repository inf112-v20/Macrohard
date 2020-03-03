package inf112.skeleton.app;

public class MovementCard extends Card {

    private int numberOfMoves;

    public MovementCard(int priority, int numberOfMoves){
        super(priority);
        if (numberOfMoves < 4 && numberOfMoves > -2) {
            this.numberOfMoves = numberOfMoves;
        }
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    @Override
    public String toString() {
        return "MovementCard{" + "priority=" + priority + ", numberOfMoves=" + numberOfMoves +
                '}';
    }
}
