package gameParts;

public class Gameboard {
    private Point constants[];
    private int size;
    private Field board [][];

    public Gameboard(int size){
        this.size=size;
        board = new Field[4*size+1][4*size+1];
        createConstatns();
        initializeMidField();
        initializeTriangles();
        for(int x=0;x<4*size+1;x++){
            for(int y=0;y<4*size+1;y++){
                if(board[x][y]!=null){
                System.out.println(board[x][y].getX()+","+board[x][y].getY());}
            }
        }
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
    void initializeMidField() {
        int x, y;
        board[2*size][2*size]=new Field(new Point(0,0),false); //middle point init
        for (int i = 1; i <= this.size; i++) {
            x = -i;
            y = 0; //starting point, running circles with constants
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < i; k++) {
                    x += constants[j].getX();
                    y += constants[j].getY();
                    board[x + 2 * size][y + 2 * size] = new Field(x, y, false);
                    //  }
                }
            }
        }
    }
    void initializeTriangles(){
        int x,y,c;
        for(int p=0;p<6;p++){

            if(p==0){
                x=0;
                y=-size;
                c=4;
            }else if(p==1){
                x=size;
                y=-size;
                c=5;
            }else if(p==2){
                x=size;
                y=0;
                c=6;
            }else if(p==3){
                x=0;
                y=size;
                c=1;
            }else if(p==4){
                x=-size;
                y=size;
                c=2;
            }else{
                x=-size;
                y=0;
                c=3;
            }
            for(int i=size;i>0;i--){
                c=(c+2)%6;
                for(int j=0;j<i;j++){
                    x+=constants[c].getX();
                    y+=constants[c].getY();
                    board[x+2*size][y+2*size]=new Field(x,y,true);
                }

            }

        }

    }

    public Field[][] getBoard() {
        return board;
    }
}

