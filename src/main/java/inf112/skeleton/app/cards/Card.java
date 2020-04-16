package inf112.skeleton.app.cards;

public abstract class Card implements Comparable<Card> {

    protected final int priority;
    public boolean selected = false;
    public int programIndex = -1;
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

    //Sorts cards by priority, so that the highest priority-cards place first
    @Override
    public int compareTo(Card card) {
        return card.getPriority() - this.getPriority();
    }

    public void setProgramIndex(int programIndex) { this.programIndex = programIndex; }

    public boolean isInProgramRegister() {
        return programIndex != -1;
    }

    @Override
    public String toString(){
       return ("TYPE: " + this.cardType + "PRIORITY: " + this.priority);
    }


}
