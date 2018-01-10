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

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn){
        this.pawn=pawn;
    }

    public void colorPawn(Colors colors) {
        PlayerColor c;
        if (this.pawn != null) {
            c = pawn.getColor();
            switch (c){
                case RED:
                    this.setBackground(colors.pawnRed);
                    break;
                case BLUE:
                    this.setBackground(colors.pawnBlue);
                    break;
                case BLACK:
                    this.setBackground(colors.pawnBlack);
                    break;
                case GREEN:
                    this.setBackground(colors.pawnGreen);
                    break;
                case WHITE:
                    this.setBackground(colors.pawnWhite);
                    break;
                case YELLOW:
                    this.setBackground(colors.pawnYellow);
                    break;
            }
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
