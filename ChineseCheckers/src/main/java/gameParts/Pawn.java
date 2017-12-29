package gameParts;

import server.Player;

import java.awt.*;

public class Pawn {
    public Pawn(PlayerColor color){
        this.color=color;
    }
    private PlayerColor color;

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public PlayerColor getColor() {
        return color;
    }
}
