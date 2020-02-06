package inf112.skeleton.app;

import java.util.Arrays;

public class Play {

    public static void main(String[] args) {
        Player player = new Player(1, 4);
        Board board = new Board(player,7, 5);
        board.setPlayer(player.getRow(), player.getCol());

        System.out.println("Row: " + player.getRow());
        System.out.println("Col: " + player.getCol());

        Board board1 = new Board(7,5);
        board1.setPlayer(player.getRow(), player.getCol());
        System.out.println(board1.getTile(3,3));
        board1.update(3,3,1);
        board1.update(1,1,1);
        board1.update(0,0,7);

        for (Tile[]a : board1.getBoard()) {
            System.out.println(Arrays.toString(a));
        }
        System.out.print("\n\n\n");
        for (Tile[]a : board.getBoard()) {
            System.out.println(Arrays.toString(a));
        }
    }
}
