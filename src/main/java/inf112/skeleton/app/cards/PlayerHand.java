package inf112.skeleton.app.cards;

public class PlayerHand  {

    private int handSize = 9;
    private Card[] program;
    private Card[] hand;

    public void setHand(Card[] possibleHand) {
        this.hand = possibleHand;
        this.handSize = possibleHand.length;
    }

    public void setFinalHand() {
        this.program = new Card[5];
    }

    public Card[] getPossibleHand() {
        return hand;
    }

    public Card[] getFinalHand() {
        return program;
    }
}
