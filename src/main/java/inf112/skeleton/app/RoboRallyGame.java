package inf112.skeleton.app;

import inf112.skeleton.app.cards.Deck;


public class RoboRallyGame {

    Player[] players;
    Board board;
    Deck deck = new Deck();

    public RoboRallyGame(Player[] players, Board board) {
        this.players = players;
        this.board = board;

        gameLoop();
    }

    private void gameLoop() {
        while (!finished()) {
            for (Player player : players) {
                deck.dealHand(player);
            }
        }
    }

    private boolean finished() {
        return false;
    }

}
