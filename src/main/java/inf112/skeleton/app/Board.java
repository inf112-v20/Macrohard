package inf112.skeleton.app;

public class Board {

    private final int heigth;
    private final int width;

    private Boolean[][] board;
    private Player player;

    public Board(int heigth, int width, Boolean[][] board) {
        this.heigth = heigth;
        this.width = width;
        this.board = board;
    }
}
