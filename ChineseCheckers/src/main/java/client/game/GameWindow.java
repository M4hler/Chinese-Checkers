package client.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Controller controller;

    public GameWindow(int radius,int numberOfPlayers, Controller controller/*, Color player*/)
    {
        this.controller = controller;

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

        panel = new GameBoardPanel(radius,null, controller, numberOfPlayers);
        this.add(panel);
        controller.addPanel(panel);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.startGame();
            }
        });

        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.pass();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                controller.closeGame();
                super.windowClosing(e);
            }
        });

        this.setVisible(true);
    }
}
