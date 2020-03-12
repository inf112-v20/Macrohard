package inf112.skeleton.app;

import java.util.ArrayList;

public class GameLoop {

    private Board board;
    ArrayList<Player> players;
    private int phase;

    public GameLoop(Board board) {
        this.board = board;
        this.players = board.getPlayers();
        phase = 0;
    }

    public void tick() {

        // Choose cards
        boolean cardsChosen = true;
        for (Player player : players) {
            if(!player.hasChosenCards){
                cardsChosen = false;
            }
        }

        if(cardsChosen){
            phase++;
            phase(phase);
        }
    }

    public void phase(int phase){
        for (Player player : players) {
            board.execute(player, player.getProgram().get(phase));
        }
    }
}
