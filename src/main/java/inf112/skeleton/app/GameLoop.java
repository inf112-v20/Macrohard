package inf112.skeleton.app;

public class GameLoop {

    private Board board;
    Player[] players;
    private int phase;

    public GameLoop(Board board) {
        this.board = board;
        this.players = board.getPlayers();
        phase = 0;
    }

    public void tick() {

        // Choose cards
        boolean cardsChosen = true;
        for (int i = 0; i < players.length; i++) {
            if(!players[i].hasChosenCards){
                cardsChosen = false;
            }
        }

        if(cardsChosen){
            phase++;
            phase(phase);
        }
    }

    public void phase(int phase){
        for (int i = 0; i < players.length; i++) {
            board.execute(players[i], players[i].getProgram().get(phase));
        }
    }
}
