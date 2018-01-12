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
        pawnYellow=new Color(255, 255, 0);
        pawnBlue=new Color(0, 0, 150) ;
        pawnBlack=Color.BLACK;
        pawnGreen=new Color(0, 170, 0);
        pawnWhite=new Color(204, 51, 153);
        pawnRed=new Color(255, 0, 0);
        highlighted =new Color(255, 85, 0);
        field=new Color(242, 242, 242);
        fieldYellow=new Color(255, 255, 204);
        fieldBlue=new Color(179, 179, 255);
        fieldBlack=Color.GRAY;
        fieldGreen=new Color(153, 255, 187);
        fieldWhite=new Color(255, 230, 255);
        fieldRed=new Color(255, 204, 204);
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
