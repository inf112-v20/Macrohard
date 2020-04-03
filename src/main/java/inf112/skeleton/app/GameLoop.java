package inf112.skeleton.app;


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
    Deck deck;
    private int phase;
    private int programRegister = 0;

    private boolean cardsShown;
    private boolean canClean;

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
                deck = new Deck();
                deck.shuffle();
                deck.dealHand(player);
            }
            phase ++;
            break;

           case 1:
            // Draw cards on screen and lock in program for NPC's
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
                // Decide the order in which program cards will be played
                if (players.get(0).getProgram() != null && programRegister <5) {
                roundPriority = new Stack();
                for (int j = 0; j<players.size(); j++) {
                    roundPriority.push(priorityHandler(players));
                }
                if (roundPriority.size() == players.size()) {
                    phase ++;
                    break;
                } }
                break;

           case 3:
                // Execute program cards in order. If cards on last program register are played, continue.
                while (!roundPriority.isEmpty() && programRegister < 5) {
                    gameScreen.runProgram(players.get((Integer) roundPriority.pop()), programRegister); }
                if (programRegister < 4) {
                    programRegister++;
                    phase--;
                    break;
                } else {
                    canClean = true;
                    phase ++;
                    break;
                }

           case 4:
               // Cleanup
               if (canClean) {
                 for (Player player : players) {
                      player.clearHand();
                     }
                 deck = null;
                 gameScreen.clearCards(cardImages);
                 cardsShown = false;
                 programRegister = 0;
                 phase = 0;
                 canClean = false;
                   break;
               } else {
                   break;
               }

           default:
               System.out.println("phase index did an oopsie :)");
        }
        }



    // returns the players index of the player with the highest priority card in the given programRegister
    // Currently lots of workarounds to avoid nullpointer, should probably be cleaned up.
    public int priorityHandler(ArrayList<Player> players) {
        int max = Integer.MIN_VALUE;
        if (phase == 2) {
        for (int i = 0; i<players.size(); i++) {
            if (players.get(i).getProgram() == null || players.get(i).getProgram()[programRegister] == null) continue;
            if (players.get(i).getProgram()[programRegister].getPrio() > max) {
                if (!roundPriority.isEmpty() &&(Integer) roundPriority.peek() == i) continue;
                max = i;
            }
        } }
        if (max == Integer.MIN_VALUE) return 0;
        return max;
    }
}
