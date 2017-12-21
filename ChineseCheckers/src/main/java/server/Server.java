package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Server implements Port
{
    public static ArrayList<String> names;
    private ArrayList<Player> players;

    public Server() throws IOException
    {
        ServerSocket listener = new ServerSocket(PORT);
        InetAddress  ip;
        names = new ArrayList<String>();
        players = new ArrayList<Player>();

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
            while(true)
            {
                Player player = new Player(listener.accept());
                player.start();
                players.add(player);
            }
        }
        finally
        {
            listener.close();
        }
    }

    public static void main(String[] args) throws IOException
    {
        Server server = new Server();
    }
}
