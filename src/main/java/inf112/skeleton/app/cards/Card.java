package inf112.skeleton.app.cards;

import inf112.skeleton.app.Player;

public abstract class Card {

    protected final int priority;
    public boolean selected = false;
    public int handIndex = -1;
    protected final CardType cardType;
    private Player player;

    public Card(int priority, CardType cardType) {
        this.priority = priority;
        this.cardType = cardType;
    }

    public String getName() {
        return cardType.toString();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPrio() {
        return this.priority;}

    public Player getPlayer() {
        return player;
    }
}
