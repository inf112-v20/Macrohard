package inf112.skeleton.app.cards;

public abstract class Card implements Comparable<Card> {

    protected final int priority;
    public boolean selected = false;
    public int registerIndex = -1;
    protected final CardType cardType;

    public Card(int priority, CardType cardType) {
        this.priority = priority;
        this.cardType = cardType;
    }

    public String getName() {
        return cardType.toString();
    }

    public int getPriority() {
        return this.priority;
    }

    public void setRegisterIndex(int registerIndex) { this.registerIndex = registerIndex; }

    public boolean isInProgramRegister() {
        return registerIndex != -1;
    }

    //Sorts cards by priority, so that the highest priority-cards place first
    @Override
    public int compareTo(Card card) {
        return card.getPriority() - this.getPriority();
    }

    @Override
    public String toString(){
       return ("TYPE: " + this.cardType + "PRIORITY: " + this.priority);
    }

}
