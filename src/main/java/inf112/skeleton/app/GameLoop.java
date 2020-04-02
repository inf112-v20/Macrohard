package inf112.skeleton.app;


import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.screens.GameScreen;
import java.util.ArrayList;
import java.util.Stack;

public class GameLoop {

    private final GameScreen gameScreen;
    private Board board;
    ArrayList<Player> players;
    ArrayList<CardGraphic> cardImages;
    Stack roundPriority;
    private int phase;
    private int cardNumber = 0;
    private Player largest;

    private boolean cardsShown;
    private boolean programLocked;
    private boolean orderFixed;
    private int clientPlayerIndex;

    public GameLoop(Board board, int clientPlayerIndex, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.board = board;
        this.players = board.getPlayers();
        this.cardImages = new ArrayList<CardGraphic>(9);
        clientPlayerIndex = clientPlayerIndex;
        phase = 0;
    }

    public void tick() {
        if (!cardsShown) {
            phase(0);
        }
        if (!programLocked) {
            phase(1);
        } else{
            phase(2);

        }
        /*if (orderFixed) {
            phase(phase);
        }*/
        }


    public void phase(int phase){
        switch (phase) {
            case 0:
            // Deal hand to all players.
            for(Player player : players){
                Deck deck = new Deck();
                deck.shuffle();
                deck.dealHand(player);
            }
            cardsShown = true;
            phase++;
            break;

            case 1:
                // Draw cards on screen
                for(int i = 0; i < players.get(clientPlayerIndex).getHand().getDealtHand().length; i++){
                    CardGraphic cardGraphic = new CardGraphic(players.get(clientPlayerIndex).getHand().getDealtHand()[i]);
                    gameScreen.addStageActor(cardGraphic);
                    cardImages.add(cardGraphic);
                }
                programLocked = true;
                phase++;
                break;

            case 2:
                // Clear screen and let next player lock in program, or continue if last player locked in program.
                if (players.get(clientPlayerIndex).hasChosenCards && players.size()-clientPlayerIndex != 1) {
                    gameScreen.clearCards(cardImages);
                    gameScreen.clientPlayerIndex++;
                    clientPlayerIndex++;
                    programLocked = false;
                    phase--;
                    break;
                } else {
                    // All players have chosen program. continue game.
                    /*gameScreen.clientPlayerIndex = 0;
                    clientPlayerIndex = 0;*/
                    /*programLocked = true;
                    orderFixed = false;*/
                   // phase++;
                    break;
                }
            case 3:
                // Arranges the order in which the cards in program slot number cardNumber will be played.
                roundPriority = new Stack();
                for (int j = 0; j<players.size(); j++) {
                    roundPriority.push(players.get(priorityHandler(players)));
                }
                orderFixed = true;
                phase++;
                break;

            case 4:
                // execute cards in order
                while (!roundPriority.isEmpty()) {
                    gameScreen.runProgram((Player) roundPriority.pop(), cardNumber);
                }
                cardNumber++;
                if (cardNumber == 5) break;
                orderFixed = false;
                phase--;
                break;

        }
    }
    // returns the players index of the player with the highest priority card in slot equal to cardNumber
    public int priorityHandler(ArrayList<Player> players) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i<players.size(); i++) {
            if (players.get(i).getProgram()[cardNumber].getPrio() > max) {
                max = i;
            }
        }
        return max;
    }
}
