package client;

import client.game.GameWindow;
import client.game.GameBoardPanel;
import gameParts.Gameboard;
import server.Port;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Client implements Port
{
	BufferedReader in;
	PrintWriter out;
	JFrame frame = new JFrame("Chatter");

	public Client()
	{
		frame.pack();
	}

	private void run() throws IOException
	{
		String serverAddress = getServerAddress();
		Socket socket = new Socket(serverAddress, PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		while (true)
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
		}

		while(true)
        {
            String line = in.readLine();
            if (line.startsWith("BOARDSIZE"))
            {
                out.println(getBoardSize());
            }
            else
            {
                GameWindow gw = new GameWindow();
                GameBoardPanel panel = new GameBoardPanel(Integer.valueOf(line));
                gw.add(panel);
//                Gameboard gameboard = new Gameboard(Integer.valueOf(line));
                break;
            }
        }
	}

	private String getServerAddress()
	{
	return JOptionPane.showInputDialog(
			frame,
			"Enter IP Address of the Server:",
			"Welcome to the Chatter",
			JOptionPane.QUESTION_MESSAGE);
	}

	private String getName()
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
		client.frame.setVisible(true);
		client.run();
	}
}
