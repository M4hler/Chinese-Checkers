package client.game;

import gameParts.Point;

import java.util.ArrayList;

public class Controller {
    GameBoardPanel panel;
    FieldButton lastClicked;
    ArrayList<Point> highlighted;

    Controller(){
        lastClicked = null;
        highlighted = null;
    }

    public void addPanel(GameBoardPanel panel){ this.panel=panel;}

    public void highlight(ArrayList<Point> array){
        highlighted=array;
        panel.higlight(array);
    }
    public void lowlight(){
        if(highlighted.size()!=0){
            panel.lowlight(highlighted);
            highlighted.clear();
        }
    }
    public void doMove(int x1,int y1,int x2,int y2){
        lowlight();
        lastClicked=null;
        panel.movePawn(x1,y1,x2,y2);
    }

    void fieldButtonClicked(FieldButton b){
        if(lastClicked==null){
            lastClicked = b;
            sendToServer("getMoves,"+b.getCoordinates().getX()+","+b.getCoordinates().getY());
        }else{
            int x1=lastClicked.getCoordinates().getX();
            int y1=lastClicked.getCoordinates().getY();
            int x2=b.getCoordinates().getX();
            int y2=b.getCoordinates().getY();
            lowlight();
            sendToServer("canMove,"+x1+","+y1+","+x2+","+y2);
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

    void decodeMessage(String message){
        String code[]=message.split(",");
        if(code[0].equals("move")){
            int x1,y1,x2,y2;
            x1=Integer.parseInt(code[1]);
            y1=Integer.parseInt(code[2]);
            x2=Integer.parseInt(code[3]);
            y2=Integer.parseInt(code[4]);
            doMove(x1,y1,x2,y2);

        }else if(code[0].equals("wrongMove")){
            lastClicked=null;
            lowlight();
            // add info to optionspanel or sth
        }else if(code[0].equals("returnMoves")){
            int i=1;
            int x,y;
            ArrayList<Point> array=null;
            while(!code[i].equals("end")){
                x=Integer.parseInt(code[i]);
                y=Integer.parseInt(code[i+1]);
                i+=2;
                array.add(new Point(x,y));
            }
            highlight(array);
        }
    }
}
