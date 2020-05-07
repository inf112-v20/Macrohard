package inf112.skeleton.app.cards;

import inf112.skeleton.app.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> deck;

    public Deck(boolean shuffled) {
        deck = new ArrayList<>();
        int priority = 10;

        for (int numCards = 0; numCards < 84; numCards++) {
            Card card;
            if (numCards < 6) {
                card = new RotationCard(priority, RotationType.U_TURN);
            } else if (numCards < 24) {
                card = new RotationCard(priority, RotationType.COUNTER_CLOCKWISE);
                priority += 10;

            } else if (numCards < 42) {
                if (priority == 430) {
                    priority = 70;
                }
                priority += 10;
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
            priority += 10;
        }
        if (shuffled) {
            shuffle();
        }
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void dealHand(Player player) {
        int handSize = player.getHandSize();
        for (int i = 0; i < handSize; i++) {
            Card card = deck.remove(deck.size() - 1);
            player.receive(card);
        }
    }

    public int getDeckSize() {
        return deck.size();
    }

}
