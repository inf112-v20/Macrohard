package inf112.skeleton.app;

import java.util.Arrays;

public class Board {

    private final int height;
    private final int width;


    private Boolean[][] tiles;
    private Player player;

    public Board(Player player, int height, int width) {
        this.height = height;
        this.width = width;

        this.tiles = initializeBoard(height, width);
        if (!playerOutOfBounds(player)) {
            this.player = player;
            tiles[player.getRow()][player.getCol()] = true;
        }
        else {
            this.player = null;
        }
    }

    public Boolean[][] initializeBoard(int height, int width) {
        Boolean[][] tiles = new Boolean[height][width];
        for (Boolean[] tile : tiles) {
            Arrays.fill(tile, false);
        }
        return tiles;
    }

    public Boolean[][] setPlayer(int row, int col){
        if (row > 0 || col > 0 || row < width || col < height){
            this.tiles[row][col] = true;
        }
        return tiles;
    }

    public Boolean[][] getTiles() {
        return tiles;
    }

    public Player getPlayer() {
        return player;
    }

    private Boolean playerOutOfBounds(Player player) {
        if (player.getRow() < 0 || player.getCol() < 0) {
            return true;
        }
        else if (player.getRow() > height || player.getCol() > width) {
            return true;
        }
        else {
            return false;
        }
    }

}
