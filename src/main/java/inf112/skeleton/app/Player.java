package inf112.skeleton.app;

import com.badlogic.gdx.scenes.scene2d.Actor;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.graphics.PlayerInfoGraphic;
import inf112.skeleton.app.tiles.Tile;
import org.jetbrains.annotations.NotNull;

public class Player implements Comparable<Player> {

    public boolean isNPC;
    private int row;
    private int col;
    private Tile archiveMarker;
    private Direction direction;
    public Card[] program;
    public Card[] hand;

    private int damageTokens;
    private int lifeTokens;

    private PlayerGraphic playerGraphic;
    public int programRegister = 0;
    private PlayerInfoGraphic playerInfoGraphic;
    private boolean destroyed;


    public Player(int row, int col, Direction direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.isNPC = false;
        this.damageTokens = 0;
        this.lifeTokens = 3;
    }

    public Player(int row, int col, Direction direction, boolean isNPC) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.isNPC = isNPC;
        this.damageTokens = 0;
        this.lifeTokens = 3;

        PlayerInfoGraphic playerInfoGraphic = new PlayerInfoGraphic(this);
    }

    public void setArchiveMarker(Tile archiveMarker) {
        this.archiveMarker = archiveMarker;
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

    public Direction getDirection () {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean hasLockedInProgram() { return getProgram() != null; }

    public int getDamageTokens() {
        return damageTokens;
    }

    public int getLifeTokens(){
        return lifeTokens;
    }

    public int getHandSize() { return 9 - damageTokens; }

    public PlayerGraphic getGraphics() {
        return this.playerGraphic;
    }

    public void setGraphic(PlayerGraphic playerGraphic) {
        this.playerGraphic = playerGraphic;
    }

    public void rotateClockwise() {
        setDirection(getDirection().turnClockwise());
    }

    public void rotateCounterClockwise() {
        setDirection(getDirection().turnCounterClockwise());
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public Card[] getHand() {
        return hand;
    }

    public Card[] getProgram() { return program;}

    public void createEmptyProgram() {
        this.program = new Card[5];
    }

    public void discardHandAndWipeProgram() {
        hand = null;
        program = null;
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
        if (this.damageTokens > 9) {
            destroy();
        }
        this.playerInfoGraphic.updateValues();
    }

    public void setDamageTokens(int newDamageTokens) {
        damageTokens = newDamageTokens;
    }

    @Override
    public int compareTo(@NotNull Player otherPlayer) {
        return getProgram()[programRegister].compareTo(otherPlayer.getProgram()[programRegister]);
    }

    public void setInfoGraphic(PlayerInfoGraphic playerInfoGraphic) {
        this.playerInfoGraphic = playerInfoGraphic;
    }

    public PlayerInfoGraphic getPlayerInfoGraphic(){
        return this.playerInfoGraphic;
    }


    public void destroy() {
        lifeTokens --;
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void reboot() {
        setRow(archiveMarker.getRow());
        setCol(archiveMarker.getCol());
        archiveMarker.setPlayer(this);

        destroyed = false;
        damageTokens = 2;
    }

}
