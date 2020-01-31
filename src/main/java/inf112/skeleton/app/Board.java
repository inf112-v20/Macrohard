package inf112.skeleton.app;

import java.util.Arrays;

public class Board {

    private final int height;
    private final int width;


    private Tile[][] board;
    private Player player;

    public Board (int height, int width){
        this.height = height;
        this.width = width;
        this.board = initializeBoard(height, width);
        //this.board = initializeBoard(height,width);
    }

    //TODO: Cleanup!
    public Board(Player player, int height, int width) {
        this.height = height;
        this.width = width;

        this.board = initializeBoard(height, width);
        if (!playerOutOfBounds(player)) {
            this.player = player;
            this.board = update(player.getRow(),player.getCol(),1);
        }
        else {
            this.player = null;
        }
    }

    public Tile[][] initializeBoard(int height, int width) {
        Tile[][] init = new Tile[height][width];
        Tile blank = new Tile(0,0);
        for (Tile[] a : init) {
            Arrays.fill(a, blank);
        }
        return init;
    }

    public Tile[][] setPlayer(int row, int col){
        if (row > 0 || col > 0 || row < width || col < height){
            Tile playerTile = new Tile(1,board[row][col].getType());
            this.board[row][col] = playerTile;
        }
        return board;
    }

    public Tile[][] update (int row, int col, int status){
        Tile prevTile = board[row][col];
        Tile newTile = new Tile(status,prevTile.getType());
        board[row][col] = newTile;
        return this.board;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Tile getTile(int row, int col){
        return board[row][col];
    }

    public Player getPlayer() {
        return player;
    }

    private Boolean playerOutOfBounds(Player player) {
        return player.getRow() < 0 || player.getCol() < 0 || player.getRow() > height || player.getCol() > width;
    }

}
