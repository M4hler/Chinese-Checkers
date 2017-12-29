package client.game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame{
    /*
        opened after connection with server and game starting
        contains gameboardpanel and game optionspanel like pause,chat,save etc
     */
    private GameBoardPanel panel;

    public GameWindow(int radius/*, Controller controller, Color player*/){
        this.setResizable(false);
        this.setSize(1000,1000);
        this.setLayout(null);
        Controller controller=new Controller();
        panel = new GameBoardPanel(radius,null,controller);
        this.add(panel);

        this.setVisible(true);
    }
}
