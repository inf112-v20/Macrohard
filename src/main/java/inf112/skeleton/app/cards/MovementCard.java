package inf112.skeleton.app.cards;

import inf112.skeleton.app.cards.Card;

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
