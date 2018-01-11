package server;

import client.game.Colors;
import gameParts.PlayerColor;

import java.awt.*;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
                String boardsize = in.readLine();

                if(status == 0)
                {
                    if (boardsize.startsWith("JOIN GAME")) {
                        String input = in.readLine();
                        status = 1;
                        if (!Server.games.get(Integer.valueOf(input)).players.contains(this)) {
                            Server.games.get(Integer.valueOf(input)).players.add(this);
                            g = Server.games.get(Integer.valueOf(input));

//                            color = setColor();
                            setColor();

                            out.println("NEW GAME WINDOW");
                            out.println(Server.games.get(Integer.valueOf(input)).valueNeededForWindowToDrawBoard);
                            out.println(Server.games.get(Integer.valueOf(input)).numberOfPlayers);
                        }

                        for (Player p : Server.players)
                        {
                            p.Games();
                        }
                    }

                    if(boardsize == null)
                    {
                        return;
                    }
                    else if(boardsize.startsWith("CREATE"))
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
                            Server.games.add(g);
//                            color = setColor();
                            setColor();
                            g.players.add(this);
                            for(Player p : Server.players)
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
                    if(boardsize.startsWith("CLOSE GAME"))
                    {
                        Server.games.get(Server.games.indexOf(g)).players.remove(this);
                        if(Server.games.get(Server.games.indexOf(g)).players.isEmpty())
                        {
                            Server.games.remove(g);
                        }
                        g = null;
                        status = 0;
                        for(Player p : Server.players)
                        {
                            p.Games();
                            p.out.println("REFRESH");
                        }
                    }

                    if(boardsize.startsWith("START GAME"))
                    {
                        Server.games.get(Server.games.indexOf(g)).inProgress = true;
                        g.setStartingPlayer();
                        System.out.println(g.currentPlayer.name + " " + g.currentPlayer.color);
                    }

                    if(boardsize.startsWith("getMoves") || boardsize.startsWith("canMove"))
                    {
                        g.decodeMessage(boardsize);
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
            Server.names.remove(name);
        }
    }

    public void returnMessage(String s)
    {
        out.println("TEST");
        out.println(s);
    }

    public void Games()
    {
        out.println("RESET");
        for(Game g : Server.games)
        {
            out.println("NEW GAME");
            for(Player p : g.players)
            {
                out.println(p.name);
            }
            out.println("STOP");
        }
        return;
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
                    if (!Server.names.contains(name))
                    {
                        out.println("NAME ACCEPTED");
                        Server.names.add(name);
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
                continue;
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
