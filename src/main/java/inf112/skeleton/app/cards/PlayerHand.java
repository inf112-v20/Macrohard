package inf112.skeleton.app.cards;

import inf112.skeleton.app.cards.Card;

public class PlayerHand  {
    int handSize = 9;
    private Card[] finalHand;
    private Card[] possibleHand;

    public void setPossibleHand(Card[] possibleHand) {
        this.possibleHand = possibleHand;
        this.handSize = possibleHand.length;
    }

    public void setFinalHand() {
        finalHand = new Card[5];
    }

    public String toString(){
        String result = "";
        for (Card i : possibleHand){
            result += (i.toString() + "\n");
        }
        return result;
    }

}
