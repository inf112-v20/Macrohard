package inf112.skeleton.app.cards;

public class PlayerHand {

    private Card[] program;
    private Card[] hand;

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public Card[] getHand() {
        return hand;
    }

    public Card[] getProgram() {
        return program;
    }

    public void setProgram() {
        this.program = new Card[5];
    }

    public void clear() {
        program = null;
        hand = null;

    }


}

