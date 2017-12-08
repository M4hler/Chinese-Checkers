package gameParts;

public class Pawn {
    public Pawn(PlayerColor color){
        this.color=color;
    }
    private PlayerColor color; // (can enum be null? ) maybe normal class not enum

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public PlayerColor getColor() {
        return color;
    }
}
