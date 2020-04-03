package inf112.skeleton.app.cards;

import inf112.skeleton.app.Player;

public abstract class Card {

    protected final int priority;
    public boolean selected = false;
    public int handIndex = -1;
    protected final CardType cardType;

    public Card(int priority, CardType cardType) {
        this.priority = priority;
        this.cardType = cardType;
    }

    public String getName() {
        return cardType.toString();
    }


    public int getPrio() {
        return this.priority;}

}
