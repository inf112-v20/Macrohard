package inf112.skeleton.app.cards;

import inf112.skeleton.app.Player;

import java.util.Random;

public class Deck {

    private int deckSize = 84;
    private Card[] deck;

    public Deck() {
        deck = new Card[deckSize];
        for (int numCards = 0; numCards < 84; numCards++){
            if (numCards < 18) {
                deck[numCards] = new MovementCard(numCards, MovementType.ONE_FORWARD);
            } else if (numCards < 30) {
                deck[numCards] = new MovementCard(numCards, MovementType.TWO_FORWARD);
            } else if (numCards < 36) {
                deck[numCards] = new MovementCard(numCards, MovementType.THREE_FORWARD);
            } else if (numCards < 42) {
                deck[numCards] = new MovementCard(numCards, MovementType.ONE_BACKWARD);
            } else if (numCards < 60) {
                deck[numCards] = new RotationCard(numCards, RotationType.CLOCKWISE);
            } else if (numCards < 78) {
                deck[numCards] = new RotationCard(numCards, RotationType.COUNTER_CLOCKWISE);
            } else {
                deck[numCards] = new RotationCard(numCards, RotationType.U_TURN);
            }
        }
    }

    //Implementing Fisher–Yates / Knuth shuffle
    public void shuffle() {
        Random rand = new Random();
        int length = deck.length;
        for (int i = length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            Card temp = deck[i];
            deck[i] = deck[index];
            deck[index] = temp;

        }
    }

    public void dealHand(Player player) {
        int choiceSize = player.getHealthPoints();
        Card[] cardChoices = new Card[choiceSize];
        for (int i = 0; i < choiceSize; i++) {
            cardChoices[i] = deck[i];
        }
        PlayerHand p = new PlayerHand();
        p.setPossibleHand(cardChoices);
        player.hand = p;
        deckSize -= choiceSize;
        deck = resize(deck, choiceSize);
    }

    public Card[] resize (Card[] oldDeck, int sizeDifference) {
        Card[] newDeck = new Card[deckSize];
        for (int i = 0; i < deckSize; i ++) {
            newDeck[i] = oldDeck[sizeDifference+i];
        }
        return newDeck;
    }

    @Override
    public String toString(){
        String result = "";
        for (Card i : deck){
            result += (i.toString() + "\n");
        }
        return result;
    }

}
