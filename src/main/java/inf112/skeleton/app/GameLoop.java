package inf112.skeleton.app;

public class GameLoop {

    private Board board;
    Player[] players;
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
