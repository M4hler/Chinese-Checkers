package client.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameWindow extends JFrame
{
    public GameBoardPanel panel;
    public GameWindow(int radius,int numberOfPlayers, Controller controller)
    {
        this.setResizable(false);
        this.setSize(750,750);
        this.setLayout(null);

        JMenuBar jmenubar = new JMenuBar();
        JButton startGameButton = new JButton("Start Game");
        JButton passButton = new JButton("Pass");

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
