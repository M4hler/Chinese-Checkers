package server;

import java.util.ArrayList;

public class DummyServer
{
    public static ArrayList<String> names;
    public static  ArrayList<Game> games;
    public static ArrayList<Player> players;

    public DummyServer()
    {
        names = new ArrayList<>();
        games = new ArrayList<>();
        players = new ArrayList<>();
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

    public static int getIndexOfGame(Game game)
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
}
