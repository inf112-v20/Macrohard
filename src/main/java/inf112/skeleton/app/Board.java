package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.MovementCard;
import inf112.skeleton.app.cards.RotationCard;

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
        setPlayer(player.getRow(), player.getCol());
    }

    public void setPlayer(int row, int col){
        if (!outOfBounds(row, col)){
            board[row][col].setOccupied(true);
        } else {
            this.player = null;
        }
    }

    public void execute(Player player, Card card) {
        if (card instanceof RotationCard) {
            Direction newDir = ((RotationCard) card).getNewDirection(player.getDirection());
            move(player, player.getRow(), player.getCol(), newDir);
        }
        else {
            MovementCard mc = (MovementCard) card;
            for (int i = 0; i < mc.getNumberOfMoves(); i++) {
                if (player.getDirection().equals(Direction.NORTH)) {
                    move(player, player.getRow() + 1, player.getCol(), player.getDirection());
                }
                else if (player.getDirection().equals(Direction.SOUTH)) {
                    move(player, player.getRow() - 1, player.getCol(), player.getDirection());
                }
                else if (player.getDirection().equals(Direction.EAST)) {
                    move(player, player.getRow(), player.getCol() + 1, player.getDirection());
                }
                else {
                    move(player, player.getRow(), player.getCol() - 1, player.getDirection());
                }
            }

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
        return row < 1 || col < 0 || row >= height || col >= width;
    }

    public Boolean isOccupied(Tile tile){
        return tile.getOccupied();
    }

}
