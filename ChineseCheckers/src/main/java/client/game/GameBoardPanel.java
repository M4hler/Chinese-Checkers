package client.game;

import gameParts.Field;
import gameParts.GameboardCreator;
import gameParts.Pawn;
import gameParts.Point;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameBoardPanel extends JPanel
{
    private FieldButton[][] board;
    private Colors colors;

    GameBoardPanel(int radius, Color player, Controller controller, PrintWriter out, BufferedReader in, int numberOfPlayers)
    {
        int size = 4 * radius + 1;
        this.board = new FieldButton[size][size];
        colors = new Colors();


        this.setLayout(null);
        this.setSize(1000, 1000);

        Field[][] board = new GameboardCreator(radius,numberOfPlayers).getBoard();

        int portion = 600 / (2 * size);
        int baseX = 450;
        int baseY = 350;
        int tempX, tempY;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (board[x][y] != null) {
                    tempX = baseX + (portion * 2 * (board[x][y].getX()));
                    tempX = tempX + (((board[x][y].getY()) * portion));
                    tempY = baseY + (portion * 2 * (board[x][y].getY()));

                    FieldButton b = new FieldButton(x,y);
                    b.setBounds(tempX, tempY, 20, 20);
                    b.setPawn(board[x][y].getPawn());
                    b.setBackground((board[x][y].getColor() == null) ? colors.field : colors.getPawnColor(board[x][y].getColor()));
                    b.setDefaultColor((board[x][y].getColor() == null) ? colors.field : colors.getFieldColor(board[x][y].getColor()));
                    b.colorPawn(colors);

                    b.addActionListener(actionEvent -> {
//                        System.out.println(" x:" + b.coordinates.getx()+" y: "+b.coordinates.gety());
                        controller.fieldButtonClicked(b);
                    });

                    this.board[x][y] = b;
                    this.add(b);
                }
            }
        }
        this.repaint();
    }

    public void movePawn(int x1, int y1, int x2, int y2)
    {
        Pawn pawn = new Pawn(board[x1][y1].getPawn().getColor());

        board[x1][y1].setPawn(null);
        board[x2][y2].setPawn(pawn);

        board[x1][y1].colorPawn(colors);
        board[x2][y2].colorPawn(colors);
    }

    void highlight(ArrayList<Point> points) {
        int x, y;
        for (Point p : points) {
            x = p.getX();
            y = p.getY();
            board[x][y].setBackground(colors.highlighted);
        }
    }

    void lowlight(ArrayList<Point> points) {
        int x, y;
        for (Point p : points) {
            x = p.getX();
            y = p.getY();
            board[x][y].colorPawn(colors);
        }
    }
}