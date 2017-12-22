package gameParts;

import java.awt.*;

public class Pawn {
    public Pawn(Color color){
        this.color=color;
    }
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
