package inf112.skeleton.app;

public class Board {

    private final int height;
    private final int width;

    private Tile[][] board;
    private Player player;

    public Board(Player player, int height, int width) {
        this.player = player;
        this.height = height;
        this.width = width;

        this.board = initializeBoard(player, height, width);
    }

    public Tile[][] initializeBoard(Player player, int height, int width) {
        Tile[][] initialBoard = new Tile[height][width];
        for (int i = 0; i < height; i ++){
            for (int j = 0; j < width; j ++) {
                initialBoard[i][j] = new Tile(0,0);
            }
        }
        if (!playerOutOfBounds(player)) {

        }
        return initialBoard;
    }

    public Tile[][] setPlayer(int row, int col){
        if (row > 0 || col > 0 || row < width || col < height){
            Tile playerTile = new Tile(1,board[row][col].getType(), row , col);
            this.board[row][col] = playerTile;
        }
        return board;
    }

    public Tile[][] update (int row, int col, int status){
        Tile prevTile = board[row][col];
        Tile newTile = new Tile(status,prevTile.getType(), prevTile.getRow(), prevTile.getCol());
        this.board[row][col] = newTile;
        return board;
    }

    public void move (Player player, int row, int col){
        player.setRow(height-row);
        player.setCol(col);
        //board[row][col].setStatus(1);
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

    public Boolean isOccupied(Tile tile){

        return (tile.getRow() == player.getRow()) && (tile.getCol() == player.getCol());
    }

}
