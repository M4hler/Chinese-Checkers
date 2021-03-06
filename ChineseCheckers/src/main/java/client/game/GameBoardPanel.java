package client.game;

import gameParts.Field;
import gameParts.GameboardCreator;
import gameParts.Pawn;
import gameParts.Point;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBoardPanel extends JPanel
{
    private FieldButton[][] board;
    private Colors colors;
    private JLabel jlabel2;

    GameBoardPanel(int radius, Color player, Controller controller, int numberOfPlayers)
    {
        int size = 4 * radius + 1;
        this.board = new FieldButton[size][size];
        colors = new Colors();
        JLabel jlabel = new JLabel("This window belongs to: " + controller.getName());
        jlabel2 = new JLabel("Current player: ");
        this.add(jlabel);
        this.add(jlabel2);
        jlabel.setLocation(3,4);
        jlabel2.setLocation(3, 20);
        jlabel.setSize(300, 16);
        jlabel2.setSize(300, 16);

        this.setLayout(null);
        this.setSize(750, 750);

        Field[][] board = new GameboardCreator(radius,numberOfPlayers).getBoard();

        int portion = 600 / (2 * size);
        int baseX = 350;
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
                        controller.fieldButtonClicked(b);
                    });

                    this.board[x][y] = b;
                    this.add(b);
                }
            }
        }
        this.repaint();
    }

    void setCurrentPlayerDisplay(String s)
    {
        jlabel2.setText("Current player: " + s);
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