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
            rotate(player, (RotationCard) card);
        }
        else if (card instanceof MovementCard) {
            move(player, (MovementCard) card);
        }
    }

    private void rotate(Player player, RotationCard card) {
        Direction newDir = card.getNewDirection(player.getDirection());
        player.setDirection(newDir);
    }

    private void move(Player player, MovementCard card) {
        if (card.getMoveID() == -1) {
            moveBackwards(player);
        }
        else {
            moveForward(player, card.getMoveID());
        }
    }

    private void moveBackwards(Player player) {
        int newRow = player.getRow() - player.getRowTrajectory();
        int newCol = player.getCol() - player.getColumnTrajectory();
        if (!outOfBounds(newRow, newCol)) {
            board[player.getRow()][player.getCol()].setOccupied(false);
            player.setRow(newRow);
            player.setCol(newCol);
            board[newRow][newCol].setOccupied(true);
        }
        else { System.out.println("Cannot move out of bounds"); }
    }

    private void moveForward(Player player, int numOfMoves) {
        for (int i = 0; i < numOfMoves; i++) {
            int newRow = player.getRow() + player.getRowTrajectory();
            int newCol = player.getCol() + player.getColumnTrajectory();
            if(!outOfBounds(newRow, newCol)){
                board[player.getRow()][player.getCol()].setOccupied(false);
                player.setRow(newRow);
                player.setCol(newCol);
                board[newRow][newCol].setOccupied(true);
            }
            else {
                System.out.println("Cannot move out of bounds");
                break;
            }
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
