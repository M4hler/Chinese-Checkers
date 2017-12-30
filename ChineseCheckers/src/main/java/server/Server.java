package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server implements Port
{
    public static ArrayList<String> names;
    public static  ArrayList<Game> games;
    public static ArrayList<Player> players;

    private Server() throws IOException
    {
        ServerSocket listener = new ServerSocket(8080); //PORT is in use
        InetAddress  ip;
        names = new ArrayList<>();
        players = new ArrayList<>();
        games = new ArrayList<>();

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
