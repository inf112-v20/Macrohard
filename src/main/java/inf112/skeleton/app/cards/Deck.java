package inf112.skeleton.app.cards;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import inf112.skeleton.app.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private int deckSize = 84;
    private ArrayList<Card> deck;

/*    public Deck(boolean shuffled) {
        deck = new ArrayList<>();
        int priority = 0;
        for (int numCards = 0; numCards < 84; numCards++){

            Card card;
            if (numCards < 18) {
                card = new MovementCard(numCards, MovementType.ONE_FORWARD);
            } else if (numCards < 30) {
                card = new MovementCard(numCards, MovementType.TWO_FORWARD);
            } else if (numCards < 36) {
                card = new MovementCard(numCards, MovementType.THREE_FORWARD);
            } else if (numCards < 42) {
                card = new MovementCard(numCards, MovementType.ONE_BACKWARD);
            } else if (numCards < 60) {
                card = new RotationCard(numCards, RotationType.CLOCKWISE);
            } else if (numCards < 78) {
                card = new RotationCard(numCards, RotationType.COUNTER_CLOCKWISE);
            } else {
                card = new RotationCard(numCards, RotationType.U_TURN);
            }
            deck.add(card);
        }
        if (shuffled) {
            shuffle();
        }
    }*/

    public Deck(boolean shuffled) {
        deck = new ArrayList<>();
        int priority = 10;

        for (int numCards = 0; numCards < 84; numCards++){
            Card card;
            if (numCards < 6) {
                card = new RotationCard(priority, RotationType.U_TURN);
            } else if (numCards < 24) {
                card = new RotationCard(priority, RotationType.COUNTER_CLOCKWISE);
                priority+=10;

            } else if (numCards < 42){
                if (priority == 430){
                    priority = 70;
                }
                priority+=10;
                card = new RotationCard(priority, RotationType.CLOCKWISE);
            } else if (numCards < 48) {
                card = new MovementCard(priority, MovementType.ONE_BACKWARD);
            } else if (numCards < 66) {
                card = new MovementCard(priority, MovementType.ONE_FORWARD);
            } else if (numCards < 78) {
                card = new MovementCard(priority, MovementType.TWO_FORWARD);
            } else {
                card = new MovementCard(priority, MovementType.THREE_FORWARD);
            }
            deck.add(card);
            priority+=10;
        }
        if (shuffled) {
            shuffle();
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void dealHand(Player player) {
        int handSize = 9 - player.getDamageTokens();
        Card[] playerHand = new Card[handSize];
        for (int i = 0; i < handSize; i++) {
            playerHand[i] = deck.remove(i);
        }
        player.setHand(playerHand);
        deckSize -= handSize;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public int getDeckSize() {
        return getDeck().size();
    }

    @Override
    public String toString(){
        String result = "";
        for (Card i : deck){
            result+= (i.toString()+"\n");
        }
        return result;
    }

}
