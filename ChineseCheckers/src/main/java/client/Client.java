package client;

import client.game.GameBoardPanel;
import client.game.GameWindow;
import client.mainMenu.StartingWindow;
import gameParts.Gameboard;
import server.Port;
import javax.swing.JOptionPane;
import java.net.Socket;
import java.io.PrintWriter;

public class Client implements Port
{
	public static void main(String[] args) throws Exception
	{
/*		System.out.println("Hello, World!");
		System.out.println("07 zgłaszam się");

		Gameboard gb = new Gameboard(1);
		GameWindow gw = new GameWindow(); */

		String serverAddress = JOptionPane.showInputDialog
				("Enter IP Address of a machine that is\n" +
						"running the date service on port " + PORT + ": ");
		Socket socket = new Socket(serverAddress, PORT);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		String boardSize = JOptionPane.showInputDialog(
				"Enter board size: "
		);
		out.println(Integer.valueOf(boardSize));
	}
}
