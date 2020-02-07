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

        initializeBoard(player, height, width);
    }

    public void initializeBoard(Player player, int height, int width) {
        board = new Tile[height][width];
        for (int i = 0; i < height; i ++){
            for (int j = 0; j < width; j ++) {
                board[i][j] = new Tile(false, i, j);
            }
        }
        setPlayer(player, player.getRow(), player.getCol());
    }

    public void setPlayer(Player player, int row, int col){
        if (!outOfBounds(player.getRow(), player.getCol())){
            board[row][col].isOccupied(true);
        }
    }

    public void move(Player player, int row, int col){
        if(!outOfBounds(row, col)){
            player.setRow(height-row);
            player.setCol(col);
        }
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

    private Boolean outOfBounds(int row, int col) {
        return row < 0 || col < 0 || row > height || col > width;
    }

    public Boolean isOccupied(Tile tile){

        return (tile.getRow() == player.getRow()) && (tile.getCol() == player.getCol());
    }

}
