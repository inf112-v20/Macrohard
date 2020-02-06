package inf112.skeleton.app;

import java.util.Arrays;

public class Board {

    private final int height;
    private final int width;


    private Boolean[][] board;
    private Player player;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;

        board = initializeBoard(height, width);
    }

    public Boolean[][] getBoard() {
        return board;
    }

    public Boolean[][] initializeBoard(int height, int width) {
        Boolean[][] board = new Boolean[height][width];
        for (Boolean[] b : board) {
            Arrays.fill(b, false);
        }
        return board;
    }

    public Boolean[][] setPlayer(int x, int y){
        if (x > 0 || y >0 || x < this.width || y < this.height){
            this.board[x][y] = true;
        } else {
            //throw new Exception("Player out of bounds!");
        }

        return board;
    }

}
