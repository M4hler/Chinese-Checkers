package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server implements Port
{
    public static ArrayList<String> names;
    private static  ArrayList<Game> games;
    private static ArrayList<Player> players;

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

    public static ArrayList<Player> getAllPlayers()
    {
        return players;
    }

    public static void addGame(Game game)
    {
        games.add(game);
    }

    public static void removeGame(Game game)
    {
        games.remove(game);
    }

    public static Game getConcreteGame(int index)
    {
        return games.get(index);
    }

    public static void removePlayerFromGame(int index, Player player)
    {
        games.get(index).players.remove(player);
    }

    public static int getIndexOfgame(Game game)
    {
        return games.indexOf(game);
    }

    public static ArrayList<Game> getAllGames()
    {
        return games;
    }

    public static ArrayList<Player> getPlayersFromConcreteGame(int index)
    {
        return games.get(index).players;
    }

    public static void addPlayerToGame(int index, Player player)
    {
        games.get(index).players.add(player);
    }

    public static void addName(String name)
    {
        names.add(name);
    }

    public static ArrayList<String> getNames()
    {
        return names;
    }

    public static void removeName(String name)
    {
        names.remove(name);
    }

    public static void main(String[] args) throws IOException
    {
        Server server = new Server();
    }
}
