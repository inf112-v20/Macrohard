package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.PlayerHand;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.tiles.Tile;

public class Player {

    public boolean hasChosenCards;
    public boolean isNPC;
    private int row;
    private int col;
    private Tile spawnPoint;
    private Direction direction;
    public PlayerHand hand;

    private int damageTokens;
    private PlayerGraphic playerGraphic;


    public Player(int row, int col, Direction direction, boolean isNPC) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.isNPC = isNPC;
        this.damageTokens = 0;
    }

    public Tile getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Tile spawnPoint) {
        this.spawnPoint = spawnPoint;
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

    public void stepIn(Direction direction) {
        setRow(row + direction.getRowTrajectory());
        setCol(col + direction.getColumnTrajectory());
    }

    public void reSpawn() {
        setRow(spawnPoint.getRow());
        setCol(spawnPoint.getCol());
    }

    public Direction getDirection () {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public PlayerHand getHand() {
        return this.hand;
    }

    public Card[] getProgram() {
        return hand.getProgram();
    }

    public void setProgram() {
        hand.setProgram();
    }

    public int getDamageTokens() {
        return damageTokens;
    }

    public PlayerGraphic getGraphics() {
        return this.playerGraphic;
    }

    public void setGraphic(PlayerGraphic playerGraphic) {
        this.playerGraphic = playerGraphic;
    }

    public void clearHand() {
        hand.clear();
    }

    public void turnClockwise() {
        setDirection(getDirection().turnClockwise());
    }

    public void turnCounterClockwise() {
        setDirection(getDirection().turnCounterClockwise());
    }

    @Override
    public String toString() {
        return "Player{" +
                "row=" + row +
                ", col=" + col +
                ", damage=" + damageTokens +
                '}';
    }

    public void applyDamage(int damage) {
        this.damageTokens += damage;
    }
}
