package inf112.skeleton.app;

import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.graphics.PlayerGraphic;
import inf112.skeleton.app.graphics.PlayerInfoGraphic;
import inf112.skeleton.app.tiles.Tile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class Player implements Comparable<Player> {

    private int row;
    private int col;
    private Direction direction;

    public Tile archiveMarker;
    public ArrayList<Card> cards;
    public Card[] program;

    public boolean inPowerDown = false;
    public boolean announcedPowerDown = false;
    private boolean destroyed;

    private int damageTokens;
    private int lifeTokens;
    private int previousFlag;

    private final int name;

    private PlayerInfoGraphic infoGraphic;
    private PlayerGraphic playerGraphic;

    public Player(int row, int col, Direction direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
        this.damageTokens = 0;
        this.lifeTokens = 3;
        this.cards = new ArrayList<>();
        this.program = new Card[5];
        this.name = ++RoboRallyApplication.numberOfPlayers;
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

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setArchiveMarker(Tile archiveMarker) {
        this.archiveMarker = archiveMarker;
    }

    public void stepIn(Direction direction) {
        setRow(row + direction.getRowModifier());
        setCol(col + direction.getColumnModifier());
    }

    public boolean hasCompleteProgram() {
        for (Card card : getProgram()) {
            if (card == null) { return false; }
        }
        return true;
    }

    public int getDamageTokens() {
        return damageTokens;
    }

    public int getLifeTokens() {
        return lifeTokens;
    }

    public int getHandSize() {
        return 9 - damageTokens;
    }

    public PlayerGraphic getPlayerGraphic() {
        return playerGraphic;
    }

    public void setPlayerGraphic(PlayerGraphic playerGraphic) {
        this.playerGraphic = playerGraphic;
    }

    public PlayerInfoGraphic getInfoGraphic() {
        return infoGraphic;
    }

    public void setInfoGraphic(PlayerInfoGraphic infoGraphic) {
        this.infoGraphic = infoGraphic;
    }

    public void rotateClockwise() {
        setDirection(getDirection().turnClockwise());
    }

    public void rotateCounterClockwise() {
        setDirection(getDirection().turnCounterClockwise());
    }

    public void receive(Card card) {
        cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card[] getProgram() {
        return program;
    }

    public void lockInRandomProgram() {
        Collections.shuffle(cards);
        int bound = Math.min(program.length, cards.size());
        for (int i = 0; i < bound; i++) {
            if(program[i] != null && program[i].isLocked) continue;
            program[i] = cards.get(i);
            program[i].select();
        }
    }

    public void discardUnselectedCards() {
        int i = 0;
        while (i < cards.size()) {
            Card card = cards.get(i);
            if (!card.isSelected()) {
                cards.remove(card);
                i --;
            }
            i ++;
        }
    }

    public void wipeProgram() {
        for (int i = 0; i < program.length; i++) {
            Card card = program[i];
            if (!card.isLocked()) {
                program[i] = null;
                cards.remove(card);
            }
        }
    }

    public void applyDamage(int damage) {
        damageTokens += damage;
        if (damageTokens > 9) {
            destroy();
        }
        for (int i = 1; i <= getNrOfLockedProgramRegisters(); i ++) {
            Card card = program[program.length - i];
            if (card != null && !card.isLocked()) {
                card.lock();
            }

        }
    }

    private void setDamageTokens(int damageTokens) {
        this.damageTokens = damageTokens;
        for (int i = 0; i < program.length - getNrOfLockedProgramRegisters(); i ++) {
            Card card = program[i];
            if (card != null && card.isLocked()) {
                card.unlock();
            }
        }
    }

    public void destroy() {
        lifeTokens--;
        setDamageTokens(0);
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isDead() {
        return lifeTokens <= 0;
    }

    public void reboot() {
        setRow(archiveMarker.getRow());
        setCol(archiveMarker.getCol());
        archiveMarker.setPlayer(this);

        destroyed = false;
        setDamageTokens(2);
    }

    public void repair() {
        // Discard one damage token if there exist at least one damage token
        if (damageTokens > 0) {
            setDamageTokens(damageTokens - 1);
        }
    }

    @Override
    public int compareTo(@NotNull Player otherPlayer) {
        return getProgram()[RoboRallyGame.currentProgramRegister].compareTo(otherPlayer.getProgram()[RoboRallyGame.currentProgramRegister]);
    }

    @Override
    public String toString() {
        return "Player{" +
                "row=" + row +
                ", col=" + col +
                ", damage=" + damageTokens +
                '}';
    }

    public int name() {
        return name;
    }

    public int getNextFlag() {
        return previousFlag + 1;
    }

    public void touchFlag() {
        previousFlag++;
    }

    public int getPreviousFlag() {
        return previousFlag;
    }

    public int getNrOfLockedProgramRegisters() { return Math.max(0, damageTokens - 4); }

    public void powerDown() {
        announcedPowerDown = false;
        inPowerDown = true;
        setDamageTokens(0);
    }
}
