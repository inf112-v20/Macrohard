package inf112.skeleton.app.cards;

public class PlayerHand  {

    private int handSize = 9;
    private Card[] program;
    private Card[] hand;

    public void setPossibleHand(Card[] possibleHand) {
        this.hand = possibleHand;
        this.handSize = possibleHand.length;
    }

    public void setProgram() {
        this.program = new Card[5];
    }

    public Card[] getDealtHand() {
        return hand;
    }

    public Card[] getProgram() {
        return program;
    }
}
