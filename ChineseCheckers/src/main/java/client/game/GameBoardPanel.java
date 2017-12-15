package client.game;

import gameParts.Field;
import gameParts.Gameboard;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GameBoardPanel extends JPanel
{
    Gameboard gb;
    int portion;
    int dy, dx, x, y, tempx, tempy;
    Field [][] board;
    ArrayList<Ellipse2D> points1;
    ArrayList<Ellipse2D> points2;

    public GameBoardPanel(int size)
    {
        this.setSize(1000,1000);
        gb = new Gameboard(size);
        portion = 600 / (2*(size*4+1)); // 4 times because of y!
        x=450;
        y=350;
        dx=portion;
        dy=2*portion;
        board = gb.getBoard();
        points1=new ArrayList<Ellipse2D>();
        points2=new ArrayList<Ellipse2D>();

        for(int x=0;x<4*size+1;x++)
        {
            for(int y=0;y<4*size+1;y++)
            {
                if(board[x][y]!=null)
                {
                    tempx = this.x+(dx*2*(board[x][y].getX()));
                    tempx = tempx+(((board[x][y].getY())*dx));
                    tempy = this.y+(dy*(board[x][y].getY()));
                    if(board[x][y].isTriangle==false)
                    {
                        points1.add(new Ellipse2D.Double((double) tempx, (double) tempy, 20, 20));
                    }
                    else
                    {
                        points2.add(new Ellipse2D.Double((double) tempx, (double) tempy, 20, 20));
                    }
                }
            }
        }
        repaint();
    }
    private void doDrawing(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g.create();

        g2d.setColor(Color.BLACK);
        for(Ellipse2D point: points1){
            g2d.fill(point);
        }
        g2d.setColor(Color.RED);
        for(Ellipse2D point: points2){
            g2d.fill(point);
        }
        g.dispose();

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDrawing(g);
    }

}
