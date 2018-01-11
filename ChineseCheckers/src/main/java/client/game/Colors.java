package client.game;

import gameParts.PlayerColor;

import java.awt.*;

public class Colors
{
    private Color pawnYellow,pawnBlue,pawnBlack,pawnGreen,pawnWhite,pawnRed,
                  fieldYellow,fieldBlue,fieldBlack,fieldGreen,fieldWhite,fieldRed;

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
        fieldYellow=Color.YELLOW;
        fieldBlue=Color.blue;
        fieldBlack=Color.BLACK;
        fieldGreen=Color.GREEN;
        fieldWhite=Color.WHITE;
        fieldRed=Color.RED;
    }

    public Color getPawnColor(PlayerColor p){
        switch (p){
            case BLACK:return pawnBlack;
            case BLUE:return pawnBlue;
            case GREEN:return pawnGreen;
            case RED:return pawnRed;
            case WHITE:return pawnWhite;
            default:return pawnYellow;
        }
    }
    public Color getFieldColor(PlayerColor p){
        switch (p){
            case BLACK:return fieldBlack;
            case BLUE:return fieldBlue;
            case GREEN:return fieldGreen;
            case RED:return fieldRed;
            case WHITE:return fieldWhite;
            default:return fieldYellow;
        }
    }



}
