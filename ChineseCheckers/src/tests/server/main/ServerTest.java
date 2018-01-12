package server.main;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.DummyServer;
import server.Game;
import server.Player;
import server.Server;

import java.util.ArrayList;

public class ServerTest
{
    DummyServer server;

    @Before
    public void setUp()
    {
        server = new DummyServer();
    }

    @After
    public void tearDown()
    {
        server = null;
    }

    @Test
    public void getAllPlayers()
    {
        server.players.add(new Player(null));
        server.players.add(new Player(null));
        ArrayList<Player> p = server.getAllPlayers();
        Assert.assertTrue(p.size() == 2);
    }

    @Test
    public void addGame()
    {
        server.addGame(new Game(2,2));
        Assert.assertTrue(server.games.size() == 1);
    }

    @Test
    public void removeGame()
    {
        Game g = new Game(2,2);
        server.addGame(g);
        server.removeGame(g);
        Assert.assertTrue(server.games.size() == 0);
    }

    @Test
    public void getConcreteGame()
    {
        Game g = new Game(2,2);
        server.addGame(g);
        Game g2 = server.getConcreteGame(0);
        Assert.assertEquals(g, g2);
    }

    @Test
    public void removePlayerFromGame()
    {
        Player p = new Player(null);
        Game g = new Game(2,2);
        server.players.add(p);
        server.addGame(g);
        server.addPlayerToGame(0, p);
        server.removePlayerFromGame(0, p);
        Assert.assertTrue(server.getPlayersFromConcreteGame(0).size() == 0);
    }

    @Test
    public void getIndexOfGame()
    {
        Game g = new Game(2,2);
        server.addGame(g);
        Assert.assertEquals(server.getIndexOfGame(g), 0);
    }

    @Test
    public void getAllGames()
    {
        Game g1 = new Game(2,2);
        Game g2 = new Game(3,3);
        server.addGame(g1);
        server.addGame(g2);
        ArrayList<Game> games = server.getAllGames();
        Assert.assertEquals(games.size(), 2);
        Assert.assertEquals(games.get(0), g1);
        Assert.assertEquals(games.get(1), g2);
    }

    @Test
    public void getPlayersFromConcreteGame()
    {
        Game g = new Game(2,2);
        Player p1 = new Player(null);
        Player p2 = new Player(null);
        server.addGame(g);
        server.addPlayerToGame(0, p1);
        server.addPlayerToGame(0, p2);
        ArrayList<Player> p = server.getPlayersFromConcreteGame(0);
        Assert.assertEquals(p.size(), 2);
    }

    @Test
    public void addPlayerToGame()
    {
        Game g = new Game(2,2);
        Player p = new Player(null);
        server.addGame(g);
        server.addPlayerToGame(0, p);
        Assert.assertEquals(server.getPlayersFromConcreteGame(0).size(), 1);
    }

    @Test
    public void addName()
    {
        server.addName("test name");
        Assert.assertEquals(server.names.size(), 1);
    }

    @Test
    public void getNames()
    {
        server.addName("name1");
        server.addName("name2");
        ArrayList<String> n = server.getNames();
        Assert.assertEquals(n.size(), 2);
    }

    @Test
    public void removeName()
    {
        server.addName("name1");
        server.addName("name2");
        server.removeName("name1");
        Assert.assertEquals(server.names.size(), 1);
    }
}