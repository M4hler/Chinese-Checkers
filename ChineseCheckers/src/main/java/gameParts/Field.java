package gameParts;

import server.Player;

import java.awt.*;

public class Field {

        private Pawn pawn;
        int x,y;
        private PlayerColor color;

        public Field(int x, int y, PlayerColor color) {
            this.x=x;
            this.y=y;
            this.pawn=null;
            this.color=color;
        }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public PlayerColor getColor() {
        return color;
    }

    public Pawn getPawn() {  return pawn; }

    public void setPawn(Pawn pawn){ this.pawn=pawn; }
}
