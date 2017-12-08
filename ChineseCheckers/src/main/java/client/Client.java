package client;

import client.game.GameWindow;
import client.mainMenu.StartingWindow;
import gameParts.Gameboard;

public class Client {

	public static void main(String[] args) 
	{
		System.out.println("Hello, World!");
		System.out.println("07 zgłaszam się");
		Gameboard gb = new Gameboard(1);
		GameWindow gw = new GameWindow();
	}

}
