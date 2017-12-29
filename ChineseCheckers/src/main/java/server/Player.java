package server;

import java.net.Socket;
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

    public Player(Socket socket)
    {
        this.socket = socket;
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
                if(boardsize.startsWith("JOIN GAME"))
                {
                    String input = in.readLine();
                    Server.games.get(Integer.valueOf(input)).players.add(this);
                    for(Player p : Server.players)
                    {
                        p.Games();
                    }
                }

                if(boardsize == null)
                {
                    return;
                }
                else
                {
                    try
                    {
                        int size = Integer.parseInt(boardsize);
                        out.println("RETURN");
                        out.println(boardsize);
                        Game g = new Game(size,4);
                        Server.games.add(g);
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

    public void Games()
    {
        out.println("START");
        for(Game g : Server.games)
        {
            out.println("GAMES");
            for(Player p : g.players)
            {
                out.println(p.name);
            }
            out.println("NEXT");
        }
        out.println("NOMORE");
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
                        out.println("NAMEACCEPTED");
                        Server.names.add(name);
                        break;
                    }
                }
            }
        }
        catch(IOException e)
        {

        }
        return;
    }
}
