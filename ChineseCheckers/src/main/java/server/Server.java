package server;

import client.mainMenu.StartingWindow;
import gameParts.Gameboard;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server implements Port
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket listener = new ServerSocket(PORT);
        InetAddress  ip;

        try
        {
            ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

        try
        {
            while (true)
            {
                Socket socket = listener.accept();
                System.out.println("User connected");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String gameSize = in.readLine();
                Game game = new Game(Integer.valueOf(gameSize));
                System.out.println("Input: " + gameSize);
                System.exit(0);
            }
        }
        finally
        {
            listener.close();
        }
    }
}
