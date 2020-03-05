package inf112.skeleton.app.cards;

public abstract class Card {

    protected final int priority;
    protected final CardType cardType;

    public Card(int priority, CardType cardType) {
        this.priority = priority;
        this.cardType = cardType;
    }

    public String getName() {
        return cardType.toString();
    }

}
