package client.game;

import gameParts.Pawn;
import gameParts.PlayerColor;
import gameParts.Point;
import server.Player;

import javax.swing.*;
import java.awt.*;

class FieldButton extends JButton{
    private Point coordinates;
    private Pawn pawn;
    private Color defaultColor;

    FieldButton(Point point){
        super();
        coordinates=point;
    }

    public Point getCoordinates() {
        return coordinates;
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

    public void setDefaultColor(Color c){
        this.defaultColor=c;
    }
    void setDefaultBackgroundColor(){
        this.setBackground(defaultColor);
    }


}
