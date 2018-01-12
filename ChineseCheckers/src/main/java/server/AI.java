package server;

import gameParts.PlayerColor;
import gameParts.Point;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.*;

class AI {
    private Game game;
    private PlayerColor playerColor;
    private Point enemyCorner;
    private int boardSize;

    private int longestMove;
    private Point start,end;
    private Point[][] memory;

    AI(Game game, PlayerColor playerColor){
        this.game=game;
        this.playerColor=playerColor;
        longestMove=0;
        start=null;
        end=null;
        boardSize=game.boardSize;
        PlayerColor enemyColor=game.getEnemyColor(playerColor);
        enemyCorner=getEnemyTopPoint(enemyColor);
        memory=new Point[4][2];
    }

    PlayerColor getPlayerColor() {
        return playerColor;
    }


    /*
    private void shiftMemory(){
        memory[3][0]=memory[2][0];
        memory[3][1]=memory[2][1];
        memory[2][0]=memory[1][0];
        memory[2][1]=memory[1][1];
        memory[1][0]=memory[0][0];
        memory[1][1]=memory[0][0];
    }*/

    void makeMove(){
        longestMove=0;
        start=null;
        end=null;

        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                if(game.gameboard[i][j]!=null){
                    if(game.gameboard[i][j].getPawn()!=null){
                        if(game.gameboard[i][j].getPawn().getColor()==playerColor){
                            findLongestMove(new Point(i,j));
                        }
                    }
                }
            }
        }
        if(start ==null || end ==null){
            game.changeTurn();
        }else{
            try{
                TimeUnit.MILLISECONDS.sleep(400);
            }catch (InterruptedException e){
                System.out.println("sleep failure");
            }

            /*shiftMemory();
            memory[0][0]=new Point(start.getX(),start.getY());
            memory[0][1]=new Point(end.getX(),end.getY());
            */
            game.move(start.getX(),start.getY(),end.getX(),end.getY());

        }
    }

    private void findLongestMove(Point given){
        ArrayList<Point> possibleMoves=game.returnPossibleMoves(given.getX(),given.getY());
        for(Point p:possibleMoves){
            if((distance(p,enemyCorner)-distance(given,enemyCorner))>=longestMove){

                longestMove=(distance(p,enemyCorner)-distance(given,enemyCorner));

                if(longestMove==0 && game.gameboard[given.getX()][given.getY()].getColor()!=null){
                    continue;
                }

                start=given;
                end=p;


                /*boolean repeated=false;
                if((distance(p,enemyCorner)-distance(given,enemyCorner))==0){
                    for(int i=0;i<4;i++){
                        if(memory[i][0]!=null&&memory[i][1]!=null) {
                            if (memory[i][0].getX()==given.getX() && memory[i][1].getX()==p.getX() &&
                                    memory[i][0].getY()==given.getY() && memory[i][1].getY()==p.getY()){
                                repeated=true;
                                break;
                            }
                        }
                    }
                    if(!repeated){
                        longestMove=(distance(p,enemyCorner)-distance(given,enemyCorner));
                        start=given;
                        end=p;
                    }
                }else{
                    longestMove=(distance(p,enemyCorner)-distance(given,enemyCorner));
                    start=given;
                    end=p;
                }*/

            }
        }
    }

    private int distance(Point a, Point b){
        return max(abs(a.getX()-b.getX()),(a.getY()-b.getY()));
    }

    private Point getEnemyTopPoint(PlayerColor playerColor){
        int radius = (boardSize-1)/4;
        switch (playerColor){
            case BLACK: return new Point(radius,4*radius);
            case BLUE: return new Point (0,3*radius);
            case GREEN: return new Point(radius,radius);
            case RED: return new Point(3*radius, 0);
            case WHITE:return new Point(4*radius,radius);
            default: return new Point(3*radius,3*radius);
        }
    }
}
