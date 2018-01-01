package client.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class GameWindow extends JFrame
{
    /*
        opened after connection with server and game starting
        contains gameboardpanel and game optionspanel like pause,chat,save etc
     */
    public GameBoardPanel panel;
    private JMenuBar jmenubar;
    private JButton startGameButton;
    private JButton passButton;
    private BufferedReader in;
    private PrintWriter out;

    public GameWindow(int radius, BufferedReader in, PrintWriter out,int numberOfPlayers/*, Controller controller, Color player*/)
    {
        this.in = in;
        this.out = out;

        this.setResizable(false);
        this.setSize(1000,1000);
        this.setLayout(null);

        jmenubar = new JMenuBar();
        startGameButton = new JButton("Start Game");
        passButton = new JButton("Pass");

        jmenubar.add(startGameButton);
        jmenubar.add(Box.createHorizontalGlue());
        jmenubar.add(passButton);
        this.setJMenuBar(jmenubar);

        Controller controller=new DummyController();
        panel = new GameBoardPanel(radius,null,controller, out,numberOfPlayers);
        this.add(panel);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                out.println("CLOSED");
                super.windowClosing(e);
            }
        });

        this.setVisible(true);
    }
}
