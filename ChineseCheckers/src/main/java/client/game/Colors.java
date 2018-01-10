package client.game;

import gameParts.PlayerColor;

import java.awt.*;

public class Colors
{
    Color pawnYellow;
    Color pawnBlue;
    Color pawnBlack;
    Color pawnGreen;
    Color pawnWhite;
    Color pawnRed;
    Color highlighted;
    Color field;


   public Colors()
   {
        pawnYellow=new Color(255, 153, 0);
        pawnBlue=new Color(0, 0, 102) ;
        pawnBlack=new Color(26, 26, 26);
        pawnGreen=new Color(0, 77, 0);
        pawnWhite=new Color(191, 191, 191);
        pawnRed=new Color(128, 0, 0);
        highlighted =new Color(84, 24, 94);
        field=Color.GRAY;
       pawnYellow=Color.YELLOW;
       pawnBlue=Color.blue;
       pawnBlack=Color.BLACK;
       pawnGreen=Color.GREEN;
       pawnWhite=Color.WHITE;
       pawnRed=Color.RED;
    }

    public Color getFieldColor(PlayerColor p){
        switch (p){
            case BLACK:return pawnBlack;
            case BLUE:return pawnBlue;
            case GREEN:return pawnGreen;
            case RED:return pawnRed;
            case WHITE:return pawnWhite;
            default:return pawnYellow;
        }
    }


}
