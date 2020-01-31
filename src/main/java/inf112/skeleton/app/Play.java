package inf112.skeleton.app;

import java.util.Arrays;

public class Play {

    public static void main(String[] args) {
        Board board = new Board(7, 5);
        Player player = new Player(1, 4);
        board.setPlayer(player.getRow(), player.getCol());

        System.out.println("Row: " + player.getRow());
        System.out.println("Col: " + player.getCol());

        for (Boolean[]a : board.getBoard()) {
            System.out.println(Arrays.toString(a));
        }
    }
}
