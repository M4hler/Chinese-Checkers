package client.game;

import gameParts.Field;
import gameParts.GameboardCreator;
import gameParts.Pawn;
import gameParts.Point;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameBoardPanel extends JPanel
{
    private FieldButton buttonForTesting;
    private FieldButton[][] board;
    Color player;
    Colors pawnColors;
    private PrintWriter out;
    private BufferedReader in;
    int size;

    GameBoardPanel(int radius, Color player, Controller controller, PrintWriter out, BufferedReader in, int numberOfPlayers)
    {
        this.out = out; //it is TEMPORARY! whole in-out logic needs to be moved to the Controller class, this is only for establishing basis board update purposes
        this.in = in;

        controller.addPanel(this);
        this.setLayout(null);
        this.setSize(1000, 1000);
        Field[][] board = new GameboardCreator(radius,numberOfPlayers).getBoard();
        this.player = player;
        pawnColors = new Colors();

        /*int*/ size = 4 * radius + 1; //size is a range of board
        this.board = new FieldButton[size][size];

        int portion = 600 / (2 * size); // 600,450,350 -may change
        int baseX = 450;
        int baseY = 350;

        int tempX, tempY;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (board[x][y] != null) {
                    tempX = baseX + (portion * 2 * (board[x][y].getX()));
                    tempX = tempX + (((board[x][y].getY()) * portion));
                    tempY = baseY + (portion * 2 * (board[x][y].getY()));

                    FieldButton b = new FieldButton(new Point(x, y));
                    b.setBounds(tempX, tempY, 20, 20);

                    b.setPawn(board[x][y].getPawn());

                    b.setBackground((board[x][y].getColor() == null) ? Color.GRAY : board[x][y].getColor());
                    b.setDefaultColor((board[x][y].getColor() == null) ? Color.GRAY : board[x][y].getColor());

                    b.colorPawn(pawnColors);

                    b.addActionListener(actionEvent -> {
//                        System.out.println(" x:" + b.coordinates.getX()+" y: "+b.coordinates.getY());
                          swapTest(b);
//                        controller.fieldButtonClicked(b);
                    });
                    this.board[x][y] = b;

                    this.add(b);
                }
            }
        }
    }

    private FieldButton[][] returnBoard()
    {
        return this.board;
    }

    private void swapTest(FieldButton but)
    {
        if (buttonForTesting == null) {
            if(but.getPawn()!=null) {
                buttonForTesting = but;
            }
        }
        else
        {
            if(buttonForTesting.equals(but))
            {
                buttonForTesting = null;
                return;
            }else if(but.getPawn()!=null){
                buttonForTesting=but;
            } else{
//            movePawn(buttonForTesting.getCoordinates().getX(), buttonForTesting.getCoordinates().getY(), but.getCoordinates().getX(), but.getCoordinates().getY());
            /*Color c = buttonForTesting.getBackground();
            buttonForTesting.setBackground(but.getBackground());
            but.setBackground(c);*/
            String s = buttonForTesting.getCoordinates().getX() + ","
                    + buttonForTesting.getCoordinates().getY()
                    + "," + but.getCoordinates().getX() + ","
                    + but.getCoordinates().getY();
            out.println("INCOMING");
            out.println(s);
            buttonForTesting = null;}
        }
    }

    public void movePawn(int x1, int y1, int x2, int y2)
    {
        Pawn pawn = new Pawn(board[x1][y1].getPawn().getColor()); //earlier was reference to the same pawn

        board[x1][y1].setPawn(null);
        board[x2][y2].setPawn(pawn);

        board[x1][y1].colorPawn(pawnColors);
        board[x2][y2].colorPawn(pawnColors);
    }

    void higlight(ArrayList<Point> points) {
        int x, y;
        for (Point p : points) {
            x = p.getX();
            y = p.getY();
            board[x][y].setBackground(pawnColors.highlighted);
        }
    }

    void lowlight(ArrayList<Point> points) {
        int x, y;
        for (Point p : points) {
            x = p.getX();
            y = p.getY();
            board[x][y].colorPawn(pawnColors);
            //board[x][y].setDefaultBackgroundColor();
        }
    }
}