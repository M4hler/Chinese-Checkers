package client.game;

import gameParts.Point;
import server.Game;

import java.util.ArrayList;


public class DummyController extends Controller{
    Game g;
    DummyController() {
        super();
        g=new Game(5,6);
    }

    void fieldButtonClicked(FieldButton b)
    {
        if (lastClicked == null) {
            if (b.getPawn() != null) {
                lastClicked = b;
                //sendToServer("getMoves," + b.getCoordinates().getX() + "," + b.getCoordinates().getY());
                highlighted=g.returnPossibleMoves(b.getCoordinates().getX(),b.getCoordinates().getY());
                highlight(highlighted);
            }
        } else {
            /*if (lastClicked.equals(b)) {
                lowlight();
                lastClicked = null;
                fieldButtonClicked(b);
            } else */if (b.getPawn() != null) {
                lowlight();
                lastClicked = null;
                fieldButtonClicked(b);

                //lastClicked = b;
                //lowlight
                //sendToServer("getMoves,"+b.getCoordinates().getX()+","+b.getCoordinates().getY());
            } else {
                int x1 = lastClicked.getCoordinates().getX();
                int y1 = lastClicked.getCoordinates().getY();
                int x2 = b.getCoordinates().getX();
                int y2 = b.getCoordinates().getY();
                lowlight();
                boolean bool= g.canMove(x1,y1,x2,y2);
                if(bool){
                    doMove(x1,y1,x2,y2);
                    g.move(x1,y1,x2,y2);
                    lastClicked=null;
                }else{
                    lowlight();
                    lastClicked=null;
                }
                //if can move, if cant lowlight and null
            }
        }
    }


    void decodeMessage(String message)
    {
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

