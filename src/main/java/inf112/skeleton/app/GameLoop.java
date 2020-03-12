package inf112.skeleton.app;

import java.util.ArrayList;

public class GameLoop {

    private Board board;
    ArrayList<Player> players;
    private int phase;

    private boolean cardsShown;
    private int clientPlayerIndex;

    public GameLoop(Board board, int clientPlayerIndex) {
        this.board = board;
        this.players = board.getPlayers();
        clientPlayerIndex = clientPlayerIndex;
        phase = 0;
    }

    public void tick() {
        if(phase == 0){
            if(!cardsShown){

            }
        }
    }

    public void phase(int phase){

    }
}
