package client.game;

import java.util.ArrayList;

public class Controller {
    GameBoardPanel panel;
    FieldButton lastClicked;
    ArrayList<FieldButton> highlighted;

    Controller(GameBoardPanel panel){
        lastClicked = null;
        highlighted = null;
        this.panel=panel;

    }
    void fieldButtonClicked(FieldButton b){
        if(lastClicked==null){
            lastClicked = b;
            sendToServer("getMoves,"+b.getCoordinates().getX()+","+b.getCoordinates().getY());

        }else{
            if(highlighted.contains(b)){
                panel.lowlight(highlighted);
                panel.movePawn(lastClicked,b);

                int x1,x2,y1,y2;
                x1=lastClicked.getCoordinates().getX();
                x2=b.getCoordinates().getX();
                y1=lastClicked.getCoordinates().getY();
                y2=b.getCoordinates().getY();

                sendToServer("move,"+x1+","+y1+","+x2+","+y2);

                lastClicked=null;
                highlighted=null;

                endTurn();
            }

        }

    }
    void endTurn(){
        sendToServer("end");
    }
    void sendToServer(String code){
        //
        //
        //
        //
    }
    void ReceiveFromServer(){
        String message;
        //create string itp;
        ///if(message=="possibleMoves"){
            //panel.higlight(argumenty);
        //}
    }
}
