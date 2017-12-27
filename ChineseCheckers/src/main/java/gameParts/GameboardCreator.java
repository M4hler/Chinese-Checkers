package gameParts;

import java.awt.*;
public class GameboardCreator {
    private Point constants[];
    private int radius;
    private Field board [][];

    public GameboardCreator(int radius) {
        createConstatns();

        this.radius = radius;
        board = new Field[4 * radius + 1][4 * radius + 1];

        initializeMidField();
        initializeTriangles();
    }

    private void createConstatns(){
       constants=new Point[6];
       constants[0]=new Point(1,-1);    //Upper Right
       constants[1]=new Point(1,0);    //Right
       constants[2]=new Point(0,1);    //DownRight
       constants[3]=new Point(-1,1);   //Down Left
       constants[4]=new Point(-1,0);    //Left
       constants[5]=new Point(0,-1);    //UpperLeft
    }
    private void initializeMidField() {
        int x, y;
        board[2*radius][2*radius]=new Field(new Point(0,0),null); //middle point init
        for (int i = 1; i <= this.radius; i++) {
            x = -i;
            y = 0; //starting point, running circles with constants
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < i; k++) {
                    x += constants[j].getX();
                    y += constants[j].getY();
                    board[x + 2 * radius][y + 2 * radius] = new Field(x, y, null);
                }
            }
        }
    }
    private void initializeTriangles(){
        int x,y,c;
        Color color;
        for(int p=0;p<6;p++){

            if(p==0){
                x=0;
                y=-radius;
                c=4;
                color= Color.BLACK;

            }else if(p==1){
                x=radius;
                y=-radius;
                c=5;
                color=Color.BLUE;
            }else if(p==2){
                x=radius;
                y=0;
                c=6;
                color=Color.GREEN;
            }else if(p==3){
                x=0;
                y=radius;
                c=1;
                color=Color.RED;
            }else if(p==4){
                x=-radius;
                y=radius;
                c=2;
                color=Color.WHITE;
            }else{
                x=-radius;
                y=0;
                c=3;
                color=Color.YELLOW;
            }
            for(int i=radius;i>0;i--){
                c=(c+2)%6;
                for(int j=0;j<i;j++){
                    x+=constants[c].getX();
                    y+=constants[c].getY();
                    board[x+2*radius][y+2*radius]=new Field(x,y,color);
                }

            }

        }

    }

    public Field[][] getBoard() {
        return board;
    }
}

