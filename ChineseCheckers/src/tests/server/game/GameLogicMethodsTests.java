package server.game;

import gameParts.Pawn;
import gameParts.Point;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.Game;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameLogicMethodsTests {
    Game game;
    ArrayList<Point> p;

    @Before
    public void setUp(){
        game = new Game(2);
        p=null;
        game.gameboard[3][5].addPawn(new Pawn(Color.WHITE));
        game.gameboard[5][3].addPawn(new Pawn(Color.WHITE));
        game.gameboard[3][6].addPawn(new Pawn(Color.WHITE));
        game.gameboard[4][6].addPawn(new Pawn(Color.WHITE));
        game.gameboard[2][7].addPawn(new Pawn(Color.WHITE));
    }
    @Test
    public  void returnTest(){

        p=game.returnPossibleMoves(2,6);

        /*for(Point point: p ){
            System.out.println("x:"+ point.getX()+"y:"+point.getY());
        }*/
        Assert.assertTrue(p.contains(new Point(6,2)));
        Assert.assertTrue(p.contains(new Point(4,4)));
        Assert.assertTrue(p.contains(new Point(2,8)));
        Assert.assertTrue(p.contains(new Point(2,5)));
        Assert.assertTrue(p.contains(new Point(1,6)));
    }

    @Test
    public void canMoveTest(){
        assertTrue(game.canMove(2,6,6,2));
        assertFalse(game.canMove(2,6,2,4));
    }

}