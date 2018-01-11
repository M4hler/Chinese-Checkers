package client.game;

import gameParts.Pawn;
import gameParts.PlayerColor;
import gameParts.Point;
import server.Player;

import javax.swing.*;
import java.awt.*;

class FieldButton extends JButton{
    private int x,y;
    private Pawn pawn;
    private Color defaultColor;

    FieldButton(int x,int y){
        super();
        this.x=x;
        this.y=y;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn){
        this.pawn=pawn;
    }

    public void colorPawn(Colors colors) {
        if (this.pawn != null) {
            this.setBackground(colors.getPawnColor(pawn.getColor()));
        }else{
            this.setBackground(defaultColor);
        }


    }
    @Override
    public boolean equals(Object object)
    {
        boolean same = false;

        if (object != null && object instanceof FieldButton)
        {
            same = ((this.x == ((FieldButton) object).getX()) &&
                    (this.y == ((FieldButton) object).getY()));

        }
        return same;
    }


    public Color getColor()
    {
        return this.defaultColor;
    }

    public void setDefaultColor(Color c){
        this.defaultColor=c;
    }
    void setDefaultBackgroundColor(){
        this.setBackground(defaultColor);
    }


}
