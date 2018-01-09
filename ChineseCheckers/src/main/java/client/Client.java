package client;

import client.game.Controller;
import client.game.GameWindow;
import gameParts.Point;
import server.Port;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;

public class Client implements Port
{
	public BufferedReader in;
	public PrintWriter out;
	public JFrame frame /*= new JFrame("Chinese checkers")*/;
	private JMenuBar menubar;
	private JButton helpMenu;
	private JButton createGameButton;
	private ArrayList<JPanel> jpanels;
	private GameWindow window;
	private Controller controller;
	private String serverAddress;

	public Client()
	{
//		serverAddress = setServerAddress();
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

	private void run() throws IOException
	{
//		String serverAddress = getServerAddress();
//		Socket socket = new Socket(serverAddress, 8080); //temporary change
		controller.run();

		in = controller.input; /*new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
		out = controller.output; /*new PrintWriter(socket.getOutputStream(), true);*/

/*		while (true)
		{
			String line = in.readLine();
			if (line.startsWith("SUBMITNAME"))
			{
				out.println(getName());
			}
			else if (line.startsWith("NAMEACCEPTED"))
			{
				break;
			}
		}*/

		while(true)
		{
			String line = in.readLine();
/*			if(line.startsWith("START"))
			{
				frame.getContentPane().removeAll();
				jpanels = new ArrayList<JPanel>();
			}
			if(line.startsWith("GAMES"))
			{
				JPanel panel = addAButton(frame.getContentPane());

				String l = in.readLine();
				while(!l.equals("NEXT"))
				{
					JLabel jl = new JLabel(l);
					panel.add(jl);
					l = in.readLine();
				}
				jpanels.add(panel);
				frame.getContentPane().add(panel);

				frame.invalidate();
				frame.validate();
				frame.repaint();
			}*/

/*			if(line.startsWith("RETURN"))
			{
				String size = in.readLine();
//				GameWindow gw = new GameWindow(Integer.valueOf(size), in, out);
				window = new GameWindow(Integer.valueOf(size), in, out,6*//*, controller*//*);
			}*/

/*			if(line.startsWith("REGEX"))
			{
				String s = in.readLine();
				String[] regex = s.split(",");
//				System.out.println(regex[0] + " " + regex[1] + " " + regex[2] + " " + regex[3]);
				window.panel.movePawn(Integer.valueOf(regex[0]), Integer.valueOf(regex[1]), Integer.valueOf(regex[2]), Integer.valueOf(regex[3]));
			}*/

/*			if(line.startsWith("REFRESH"))
			{
				frame.invalidate();
				frame.validate();
				frame.repaint();
			}*/
		}
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

/*		while(!l.equals("NEXT"))
		{
			JLabel jl = new JLabel(l);
			panel.add(jl);
			l = in.readLine();
		}*/
		jpanels.add(panel);
		frame.getContentPane().add(panel);

		refresh();
	}

/*	public void lowlight(ArrayList<Point> points)
	{
		window.panel.lowlight(points);
	}*/

	public void drawWindow(String size)
	{
		window = new GameWindow(Integer.valueOf(size), in, out,6, controller);
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
//				out.println("JOIN GAME" );
//				out.println(jpanels.indexOf(panel));
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
				"Welcome to the Chatter",
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

	public static void main(String[] args) throws Exception
	{
		Client client = new Client();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		client.frame.setVisible(true);
		client.run();
	}
}
