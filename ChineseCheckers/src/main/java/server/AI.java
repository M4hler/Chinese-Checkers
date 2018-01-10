package server;

import gameParts.PlayerColor;
import gameParts.Point;
import java.util.ArrayList;
import static java.lang.Math.*;

public class AI {
    private Game game;
    private PlayerColor playerColor;
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
            if(distance(p,given)>=longestMove){
                longestMove=distance(p,given);
                start=given;
                end=p;
            }
        }
    }

    int distance(Point a, Point b){
        return max(abs(a.getX()-b.getX()),(a.getY()-b.getY()));
    }
}
