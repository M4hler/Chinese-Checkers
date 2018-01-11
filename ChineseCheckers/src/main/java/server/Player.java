package server;

import gameParts.PlayerColor;

import java.net.Socket;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Player extends Thread
{
    private String name;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int status;
    private Game g;
    private PlayerColor color;

    public Player(Socket socket)
    {
        this.socket = socket;
        status = 0;
        g = null;
        color = null;
    }

    public void run()
    {
        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            Submitname();
            Games();

            while(true)
            {
                String command = in.readLine();

                if(status == 0)
                {
                    if (command.startsWith("JOIN GAME"))
                    {
                        String input = in.readLine();
                        int index = Integer.valueOf(input);
                        status = 1;

                        ArrayList<Player> playersInGame = Server.getPlayersFromConcreteGame(index);
                        if(!playersInGame.contains(this))
                        {
                            Server.addPlayerToGame(index, this);
                            g = Server.getConcreteGame(index);

                            setColor();

                            out.println("NEW GAME WINDOW");
                            out.println(Server.getConcreteGame(index).valueNeededForWindowToDrawBoard);
                            out.println(Server.getConcreteGame(index).numberOfPlayers);
                        }

                        for (Player p : Server.getAllPlayers())
                        {
                            p.Games();
                        }
                    }
                    else if(command.startsWith("CREATE"))
                    {
                        String newsize = in.readLine();
                        int numberOfPlayers = Integer.valueOf(in.readLine());
                        status = 1;
                        try
                        {
                            int size = Integer.parseInt(newsize);
                            out.println("GAME");
                            out.println(size);
                            out.println(numberOfPlayers);

                            g = new Game(size,numberOfPlayers);
                            Server.addGame(g);
                            setColor();
                            g.addPlayer(this);

                            for(Player p : Server.getAllPlayers())
                            {
                                p.Games();
                            }
                        }
                        catch(NumberFormatException e)
                        {
                            continue;
                        }
                    }
                }

                if(status == 1)
                {
                    if(command.startsWith("CLOSE GAME"))
                    {
                        Server.removePlayerFromGame(Server.getIndexOfGame(g), this);
                        if(Server.getPlayersFromConcreteGame(Server.getIndexOfGame(g)).isEmpty())
                        {
                            Server.removeGame(g);
                        }
                        g = null;
                        status = 0;

                        for(Player p : Server.getAllPlayers())
                        {
                            p.Games();
                            p.out.println("REFRESH");
                        }
                    }
                    else if(command.startsWith("START GAME"))
                    {
                        Server.getConcreteGame(Server.getIndexOfGame(g)).inProgress = true;
                        g.setStartingPlayer();
                        //System.out.println(g.currentPlayer.name + " " + g.currentPlayer.color);
                        g.refreshCurrentPlayerView();
                    }
                    else if(command.startsWith("getMoves") || command.startsWith("canMove"))
                    {
                        //System.out.println(g.currentPlayer.name + " " + g.currentPlayer.color);
                        if(g.getPlayerByColor(g.currentPlayer) == this)
                        {
                            g.decodeMessage(command);
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
        finally
        {
            Server.removeName(name);
        }
    }

    public void returnMessage(String s)
    {
        out.println("TEST");
        out.println(s);
    }

    public void changeCurrentPlayer(Player player)
    {
        out.println("CURRENT PLAYER");
        out.println(player.name);
    }

    private void Games()
    {
        out.println("RESET");
        for(Game g : Server.getAllGames())
        {
            out.println("NEW GAME");
            for(Player p : g.players)
            {
                out.println(p.name);
            }
            out.println("STOP");
        }
    }

    private void Submitname()
    {
        try
        {
            while (true)
            {
                out.println("SUBMITNAME");
                name = in.readLine();
                if (name == null)
                {
                    return;
                }
                synchronized (Server.names)
                {
                    if (!Server.getNames().contains(name))
                    {
                        out.println("NAME ACCEPTED");
                        Server.addName(name);
                        break;
                    }
                }
            }
        }
        catch(IOException e)
        {

        }
    }

    public PlayerColor getColor() {
        return color;
    }

    private void setColor()
    {
        for(PlayerColor pc : g.getPlayerColors())
        {
            if(g.currentColors.contains(pc))
            {
                System.out.println(pc + " in use");
            }
            else
            {
                g.currentColors.add(pc);
                System.out.println(pc + " not in use");
                this.color = pc;

                break;
            }
        }
    }
}
