package inf112.skeleton.app;

public class Tile {
    //Status tells us if a tile has a player on it, or if there is a wall blocking access
    private int status;
    //Type has a value, where the number is interpreted as a type of tile, i.e. 0 = normal floor tile
    private int type;


    public Tile(int status, int type) {
        this.status = status;
        this.type = type;
    }

    public Boolean isOccupied(){
        return this.status == 1;
    }

    //TODO: Find correct implementation, currently updates all tiles' status to 1
    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString(){//overriding the toString() method
        return "Status: " + this.status + " Type: " + this.type;
}
}
