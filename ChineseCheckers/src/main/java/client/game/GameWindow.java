package client.game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame{
    /*
        opened after connection with server and game starting
        contains gameboardpanel and game optionspanel like pause,chat,save etc
     */
    private GameBoardPanel panel;

    public GameWindow(int radius, Controller controller, Color player){
        this.setResizable(false);
        this.setSize(1000,1000);
        this.setLayout(null);

        panel = new GameBoardPanel(radius,player,new Controller(panel));
        this.add(panel);

        this.setVisible(true);
    }
}
