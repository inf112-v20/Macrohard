package inf112.skeleton.app;

import inf112.skeleton.app.cards.PlayerHand;

public class Player {

    private int row;
    private int col;
    private Direction direction;
    public PlayerHand hand;
    private int healthPoints;

    public Player(int row, int col, Direction direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.healthPoints = 9;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setHealthPoints (int hp) { this.healthPoints = hp;}

    public Direction getDirection () {
        return this.direction;
    }
    public PlayerHand getHand () {
        return this.hand;
    }
    public int getHealthPoints() {
        return this.healthPoints;
    }


}
