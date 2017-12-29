package client.game;

import gameParts.Field;
import gameParts.GameboardCreator;
import gameParts.Point;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBoardPanel extends JPanel{


    private FieldButton buttonForTesting;
    private FieldButton[][] board;
    Color player;

    GameBoardPanel(int radius,Color player,Controller controller) { //TODO: add player(by color?) to constructor to rotate map
        controller.addPanel(this);
        this.setLayout(null);
        this.setSize(1000, 1000);
        Field[][] board = new GameboardCreator(radius).getBoard();
        this.player = player;

        int size = 4 * radius + 1; //size is a range of board
        this.board = new FieldButton[size][size];

        int portion = 600 / (2 * size); // 600,450,350 -may change
        int baseX = 450;
        int baseY = 350;

        int tempX,tempY;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (board[x][y] != null) {
                    tempX = baseX + (portion*2*(board[x][y].getX()));
                    tempX = tempX + (((board[x][y].getY())*portion));
                    tempY = baseY + (portion*2*(board[x][y].getY()));

                    FieldButton b = new FieldButton(new Point(x,y));
                    b.setBounds(tempX, tempY, 15, 15);
                    b.setBackground((board[x][y].getColor()==null) ? Color.GRAY : board[x][y].getColor());
                    b.addActionListener(actionEvent -> {
                        //System.out.println(" x:" + b.coordinates.getX()+" y: "+b.coordinates.getY());
                        //swapTest(b);
//                        controller.fieldButtonClicked(b);
                    });
                    this.board[x][y] = b;

                    this.add(b);
                }
            }
        }
    }
    private void swapTest(FieldButton but){
        if(buttonForTesting==null){
            buttonForTesting=but;
        }else{
            Color c=buttonForTesting.getBackground();
            buttonForTesting.setBackground(but.getBackground());
            but.setBackground(c);
            buttonForTesting=null;
        }
    }
     void movePawn(int x1,int y1,int x2,int y2){}
     void higlight(ArrayList<Point> points){}
     void lowlight(ArrayList<Point> points){}


}