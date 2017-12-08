package client.mainMenu;

import javax.swing.*;
import java.awt.*;

public class StartingWindow extends JFrame{
    /* connects with server,
        waits for other users, sets number of players, plocked during the game(maybe closed)
      */
    private JButton createGame;
    private JButton joinGame;
    private JButton exit;
    StartingWindow(){
        super();
        setLayout(new GridLayout(0,1));
        setLocation(200,60);
        setSize(640,480);
        setResizable(false);
        createGame =new JButton("CREATE GAME");
        joinGame = new JButton("JOIN GAME");
        exit = new JButton("EXIT");
        this.add(createGame);
        this.add(joinGame);
        this.add(exit);
    }

}
