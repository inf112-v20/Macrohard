package inf112.skeleton.app;

import inf112.skeleton.app.cards.Deck;
import inf112.skeleton.app.graphics.CardGraphic;
import inf112.skeleton.app.screens.GameScreen;

import java.util.ArrayList;

public class GameLoop {

    private final GameScreen gameScreen;
    private Board board;
    ArrayList<Player> players;
    private int phase;

    private boolean cardsShown;
    private int clientPlayerIndex;

    public GameLoop(Board board, int clientPlayerIndex, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.board = board;
        this.players = board.getPlayers();
        clientPlayerIndex = clientPlayerIndex;
        phase = 0;
    }

    public void tick() {
        if(phase == 0){
            if(!cardsShown){
                for(Player player : players){
                    Deck deck = new Deck();
                    deck.shuffle();
                    deck.dealHand(player);
                }
                for(int i = 0; i < players.get(clientPlayerIndex).getHand().getDealtHand().length; i++){
                    CardGraphic cardGraphic = new CardGraphic(players.get(clientPlayerIndex).getHand().getDealtHand()[i]);
                    gameScreen.addStageActor(cardGraphic);
                }
            }
            cardsShown = true;
        }
    }

    public void phase(int phase){

    }
}
