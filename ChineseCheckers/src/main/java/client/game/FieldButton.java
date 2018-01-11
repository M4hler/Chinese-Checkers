package client.game;

import gameParts.Pawn;

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

    Pawn getPawn() {
        return pawn;
    }

    void setPawn(Pawn pawn){
        this.pawn=pawn;
    }

    void colorPawn(Colors colors) {
        if (this.pawn != null) {
            this.setBackground(colors.getPawnColor(pawn.getColor()));
        }else{
            this.setDefaultBackgroundColor();
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

    void setDefaultColor(Color c){
        this.defaultColor=c;
    }
    private void setDefaultBackgroundColor(){
        this.setBackground(defaultColor);
    }


}
