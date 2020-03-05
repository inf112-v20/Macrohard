package inf112.skeleton.app.cards;

public abstract class Card {

    protected final int priority;
    public boolean selected;
    public int handIndex;
    protected final CardType cardType;

    public Card(int priority, boolean selected, int handIndex , CardType cardType)
    {
        this.priority = priority;
        this.selected = selected;
        this.handIndex = handIndex;
        this.cardType = cardType;
    }

    public String getName() {
        return cardType.toString();
    }

}
