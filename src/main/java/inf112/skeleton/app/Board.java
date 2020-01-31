package inf112.skeleton.app;

import java.util.Arrays;

public class Board {

    private final int height;
    private final int width;


    private Boolean[][] tiles;
    private Player player;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;

        tiles = initializeBoard(height, width);
    }

    public Boolean[][] getTiles() {
        return tiles;
    }

    public Boolean[][] initializeBoard(int height, int width) {
        Boolean[][] board = new Boolean[height][width];
        for (Boolean[] tile : tiles) {
            Arrays.fill(tile, false);
        }
        return board;
    }

    public Boolean[][] setPlayer(int row, int col){
        if (row > 0 || col > 0 || row < width || col < height){
            this.tiles[row][col] = true;
        }
        return tiles;
    }

}
