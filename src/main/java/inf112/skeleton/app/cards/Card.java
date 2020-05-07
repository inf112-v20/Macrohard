package inf112.skeleton.app.cards;

import inf112.skeleton.app.graphics.CardGraphic;

public abstract class Card implements Comparable<Card> {

    protected final int priority;
    protected final CardType cardType;

    private CardGraphic cardGraphic;
    public boolean isSelected = false;
    public boolean isLocked = false;

    public Card(int priority, CardType cardType) {
        this.priority = priority;
        this.cardType = cardType;
    }

    public void lock() {
        isLocked = true;
        if (cardGraphic != null) {
            cardGraphic.lock();
        }
    }

    public void unlock() {
        isLocked = false;
        if (cardGraphic != null) {
            cardGraphic.unlock();
        }
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void select() {
        isSelected = true;
    }

    public void deselect() {
        isSelected = false;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getName() {
        return cardType.toString();
    }

    public int getPriority() {
        return this.priority;
    }

    public CardGraphic getCardGraphic() {
        return cardGraphic;
    }

    public void setCardGraphic(CardGraphic cardGraphic) {
        this.cardGraphic = cardGraphic;
    }

    /**
     * Override-method to help sort cards with respect to priority,
     * the highest prioritized card coming first.
     *
     * @param card The card that this card is to be compared with.
     * @return an int that is used to arrange an partial order.
     */
    @Override
    public int compareTo(Card card) {
        return card.getPriority() - this.getPriority();
    }

}
