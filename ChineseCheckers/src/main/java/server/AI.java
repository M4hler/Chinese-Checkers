package server;

import gameParts.PlayerColor;
import gameParts.Point;
import java.util.ArrayList;
import static java.lang.Math.*;

public class AI {
    private Game game;
    private PlayerColor playerColor;
    private Point enemyCorner;
    private int boardSize;

    private int longestMove;
    private Point start,end;

    public AI(Game game, PlayerColor playerColor){
        this.game=game;
        this.playerColor=playerColor;
        longestMove=0;
        start=null;
        end=null;
        boardSize=game.boardSize;
        PlayerColor enemyColor=game.getEnemyColor(playerColor);
        enemyCorner=getEnemyTopPoint(enemyColor);
    }


    public void makeMove(){
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                if(game.gameboard[i][j]!=null){
                    if(game.gameboard[i][j].getPawn().getColor()==playerColor){
                        findLongestMove(new Point(i,j));
                    }
                }
            }
        }
        if(start ==null || end ==null){
            //end turn
        }else{
            //makeMove(start,end);
        }
        longestMove=0;
        start=null;
        end=null;
    }

    private void findLongestMove(Point given){
        ArrayList<Point> possibleMoves=game.returnPossibleMoves(given.getX(),given.getY());
        for(Point p:possibleMoves){
            if((distance(p,enemyCorner)-distance(given,enemyCorner))>=longestMove){
                longestMove=(distance(p,enemyCorner)-distance(given,enemyCorner));
                start=given;
                end=p;
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
