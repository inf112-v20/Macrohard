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
        if (!outOfBounds(row, col)){
            board[row][col].setOccupied(true);
        } else {
            this.player = null;
        }
    }

    public void move(Player player, int row, int col, Direction dir){
        board[player.getRow()][player.getCol()].setOccupied(false);

        if(!outOfBounds(row, col)){
            player.setRow(row);
            player.setCol(col);
            player.setDirection(dir);
            board[row][col].setOccupied(true);
        } else {
            System.out.println("Cannot move out of bounds");
            player.setDirection(dir);
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
        return row < 0 || col < 0 || row >= height || col >= width;
    }

    public Boolean isOccupied(Tile tile){
        return tile.getOccupied();
    }

}
