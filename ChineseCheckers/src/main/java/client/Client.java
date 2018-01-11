package client;

import client.game.Controller;
import client.game.GameWindow;
import server.Port;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Client implements Port
{
	public JFrame frame;
	private JMenuBar menubar;
	private JButton helpMenu;
	private JButton createGameButton;
	private ArrayList<JPanel> jpanels;
	private GameWindow window;
	private Controller controller;

	public Client()
	{
		controller = new Controller(this);

		frame = new JFrame("Chinese checkers");

		jpanels = new ArrayList<>();
		menubar = new JMenuBar();
		createGameButton = new JButton("Create game");
		helpMenu = new JButton("Help");

		createGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String line = getBoardSize();
				controller.createGame(line);
			}
		});

		helpMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				controller.output.println("TEST");
			}
		});

		menubar.add(createGameButton);
		menubar.add(Box.createHorizontalGlue());
		menubar.add(helpMenu);

		frame.setJMenuBar(menubar);
		Container pane = frame.getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		frame.setSize(400, 400);
		frame.setVisible(true);
	}

	private void run()
	{
		controller.run();
	}

	public void clearLobby()
	{
		frame.getContentPane().removeAll();
		jpanels = new ArrayList<>();
	}

	public void refresh()
	{
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}

	public void refreshLobby(ArrayList<String> players)
	{
		JPanel panel = addAButton(frame.getContentPane());

		for(String s : players)
		{
			JLabel jl = new JLabel(s);
			panel.add(jl);
		}

		jpanels.add(panel);
		frame.getContentPane().add(panel);

		refresh();
	}

	public void drawWindow(String size, String number)
	{
		int i = Integer.valueOf(number);
		window = new GameWindow(Integer.valueOf(size), /*in, out,*/ i, controller);
		frame.setVisible(false);
	}

	public void move(String s)
	{
		String[] regex = s.split(",");
		window.panel.movePawn(Integer.valueOf(regex[0]), Integer.valueOf(regex[1]), Integer.valueOf(regex[2]), Integer.valueOf(regex[3]));
	}

	private JPanel addAButton(Container container)
	{
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton jb = new JButton("Join Game");
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				controller.joinGame(jpanels.indexOf(panel));
			}
		});

		jb.setAlignmentX(JButton.RIGHT);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(jb);

		return panel;
	}

	public String setServerAddress()
	{
		return JOptionPane.showInputDialog(
				frame,
				"Enter IP Address of the Server:",
				"Welcome to the game",
				JOptionPane.QUESTION_MESSAGE);
	}

	public String getName()
    {
		return JOptionPane.showInputDialog(
				frame,
				"Choose a screen name:",
				"Screen name selection",
				JOptionPane.PLAIN_MESSAGE);
	}

	private String getBoardSize()
    {
        return JOptionPane.showInputDialog(
                frame,
                "Give board size:",
                "Board size",
                JOptionPane.PLAIN_MESSAGE);
    }

	public static void main(String[] args)
	{
		Client client = new Client();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.run();
	}
}
