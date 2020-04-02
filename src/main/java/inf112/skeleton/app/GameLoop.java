package inf112.skeleton.app;


import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.MovementType;
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
       switch (phase) {
            case 0:
            // Deal hand to all players.
            for(Player player : players){
                Deck deck = new Deck();
                deck.shuffle();
                deck.dealHand(player);
            }
            phase ++;
            break;

           case 1:
            // Draw cards on screen
               if (!cardsShown) {
                for(int i = 0; i < players.get(0).getHand().getDealtHand().length; i++){
                    CardGraphic cardGraphic = new CardGraphic(players.get(0).getHand().getDealtHand()[i]);
                    gameScreen.addStageActor(cardGraphic);
                    cardImages.add(cardGraphic);
                    cardsShown = true;
                    }
               for (int j = 1; j<players.size(); j++) {
                    gameScreen.lockRandomProgram((players.get(j)));
                    }
               }
            if (players.get(0).hasChosenCards) {
                phase++;
                break;
            }
            break;

            case 2:
                roundPriority = new Stack();
                for (int j = 0; j<players.size(); j++) {
                    roundPriority.push(priorityHandler(players));
                }
                if (roundPriority.size() == players.size()) {
                    phase ++;
                    break;
                }
                break;

           case 3:

                while (!roundPriority.isEmpty()) {
                    gameScreen.runProgram(players.get((Integer) roundPriority.pop()), cardNumber); }
                cardNumber++;
                if (roundPriority.isEmpty() && cardNumber < 5) {
                    phase--;
                    break;
                } else {
                    break;
                }
           default:
               System.out.println("something went wrong :)");
        }
        }



    // returns the players index of the player with the highest priority card in slot equal to cardNumber
    public int priorityHandler(ArrayList<Player> players) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i<players.size(); i++) {
            if (players.get(i).getProgram()[cardNumber] == null) continue;
            if (players.get(i).getProgram()[cardNumber].getPrio() > max) {
                if (!roundPriority.isEmpty() &&(Integer) roundPriority.peek() == i) continue;
                max = i;
            }
        }
        if (max == Integer.MIN_VALUE) return 0;
        return max;
    }
}
