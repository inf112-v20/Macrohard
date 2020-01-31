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

    public Boolean[][] initializeBoard(int height, int width) {
        Boolean[][] board = new Boolean[height][width];
        for (Boolean[] b : board) {
            Arrays.fill(b, false);
        }
        return board;
    }

}
