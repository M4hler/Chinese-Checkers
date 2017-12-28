package server;
// an instance of single game (multiple games can be played at one time at server)

import gameParts.Field;
import gameParts.GameboardCreator;
import gameParts.Point;

import java.util.ArrayList;

public class Game
{
    public Field[][] gameboard;
    private Point constants[];
    int boardSize;

    ArrayList<Point> possibleMoves = new ArrayList<>();

    public Game(int boardSize) {
        GameboardCreator creator = new GameboardCreator(boardSize);
        constants=creator.getConstants();
        //TODO: gameboard.addPawns(int playersNumber);
        gameboard=creator.getBoard();
        this.boardSize=4*boardSize+1;
    }


    public void returnPossibleMoves(int x,int y) {
        int tempX, tempY;
        for (Point direction : constants) {
            tempX = x + direction.getX();
            tempY = y + direction.getY();
            if (tempX < 0 || tempY < 0) {
                continue;
            }
            if (tempX >= boardSize || tempY >= boardSize) {
                continue;
            }
            if (gameboard[tempX][tempY] != null) {
                if (gameboard[tempX][tempY].getPawn() == null) {
                    if (!possibleMoves.contains(new Point(tempX, tempY))) {
                        possibleMoves.add(new Point(tempX, tempY));
                    }

                } else {
                    tempX += direction.getX();
                    tempY += direction.getY();
                    if(tempX<0 || tempY<0){continue;}
                    if(tempX>=boardSize || tempY>=boardSize){continue;}

                    if (gameboard[tempX][tempY] != null) {
                        if (gameboard[tempX][tempY].getPawn() == null) {
                            if (!possibleMoves.contains(new Point(tempX, tempY))) {
                                possibleMoves.add(new Point(tempX, tempY));
                                searchJumps(tempX, tempY, direction);
                            }
                        }
                    }
                }
            }
        }
    }
    void searchJumps(int x,int y,Point comingFrom){
        int tempX,tempY;
        for(Point direction: constants) {
            if((direction.getX()==-(comingFrom.getX()))&&(direction.getY()==-(comingFrom.getY()))) {continue;} //avoid checking direction where we did come from?
            tempX = x + direction.getX();
            tempY = y + direction.getY();
            if(tempX<0 || tempY<0){continue;} if(tempX>=boardSize || tempY>=boardSize){continue;}
            if (gameboard[tempX][tempY] != null) {
                if (gameboard[tempX][tempY].getPawn() != null) {
                    tempX += direction.getX();
                    tempY += direction.getY();
                    if(tempX<0 || tempY<0){continue;} if(tempX>=boardSize || tempY>=boardSize){continue;}
                    if (gameboard[tempX][tempY] != null) {
                        if (gameboard[tempX][tempY].getPawn() == null) {
                            if (!possibleMoves.contains(new Point(tempX, tempY))) {
                                possibleMoves.add(new Point(tempX, tempY));
                                searchJumps(tempX, tempY,direction);
                            }
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Point> getPossibleMoves() {
        return possibleMoves;
    }
}
