package server.game;

import gameParts.Pawn;
import gameParts.Point;
import org.junit.Before;
import org.junit.Test;
import server.Game;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class returnPossibleMovesTest {
    Game game;
    ArrayList<Point> p;

    @Before
    public void setUp(){

    }
    @Test
    public  void costamtest(){
        game = new Game(2);
        p=null;
        game.gameboard[3][5].addPawn(new Pawn(Color.WHITE));
        game.gameboard[5][3].addPawn(new Pawn(Color.WHITE));
        game.gameboard[3][6].addPawn(new Pawn(Color.WHITE));
        game.gameboard[4][6].addPawn(new Pawn(Color.WHITE));
        game.gameboard[2][7].addPawn(new Pawn(Color.WHITE));
        game.returnPossibleMoves(2,6);
        p=game.getPossibleMoves();
        for(Point point: p ){
            System.out.println("x:"+ point.getX()+"y:"+point.getY());
        }
    }

}