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
        //Tile test = new Tile(1,0);
        System.out.println(board1.getTile(3,3));
        board1.update(3,3,1);

        for (Tile[]a : board1.getBoard()) {
            System.out.println(Arrays.toString(a));
        }
        System.out.print("\n\n\n");
        for (Tile[]a : board.getBoard()) {
            System.out.println(Arrays.toString(a));
        }
/*
        for (int row = 0; row <7;row++){
            for(int col = 0; col<5; col++){
                System.out.print(board[row][col]);
            }
        }*/
    }
}
