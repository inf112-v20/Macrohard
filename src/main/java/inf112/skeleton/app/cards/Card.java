package inf112.skeleton.app.cards;

public abstract class Card {

    protected final int priority;
    public boolean selected;
    public int handIndex;

    public Card(int priority, boolean selected, int handIndex)
    {
        this.priority = priority;
        this.selected = selected;
        this.handIndex = handIndex;
    }



}
