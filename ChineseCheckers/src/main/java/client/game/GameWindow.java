package client.game;

import javax.swing.*;

public class GameWindow extends JFrame{
    /*
        opened after connection with server and game starting
        contains gameboardpanel and game optionspanel like pause,chat,save etc
     */
    GameBoardPanel panel;

    public GameWindow(){
        this.setResizable(false);
        this.setSize(1000,1000);
        this.setLayout(null);
//        panel = new GameBoardPanel(4);
 //       this.add(panel);
//        this.setVisible(true);
    }
}
