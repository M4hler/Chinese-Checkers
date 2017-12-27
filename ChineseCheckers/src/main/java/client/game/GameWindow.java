package client.game;

import javax.swing.*;

public class GameWindow extends JFrame{
    /*
        opened after connection with server and game starting
        contains gameboardpanel and game optionspanel like pause,chat,save etc
     */
    private GameBoardPanel panel;

    public GameWindow(int radius){
        this.setResizable(false);
        this.setSize(1000,1000);
        this.setLayout(null);
        panel = new GameBoardPanel(radius);
        this.add(panel);
        this.setVisible(true);
    }
}
