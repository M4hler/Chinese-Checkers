package gameParts;

import server.Player;

import java.awt.*;

public class Pawn
{
    private PlayerColor color;

    public Pawn(PlayerColor color)
    {
        this.color = color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public PlayerColor getColor()
    {
        return color;
    }
}
