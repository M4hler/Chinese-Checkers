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
    private int status;
    private Game g;

    public Player(Socket socket)
    {
        this.socket = socket;
        status = 0;
        g = null;
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
                            out.println("RETURN");
                            out.println(Server.games.get(Integer.valueOf(input)).valueNeededForWindowToDrawBoard);
                        }

                        for (Player p : Server.players) {
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
                        status = 1;
                        try
                        {
                            int size = Integer.parseInt(newsize);
                            out.println("RETURN");
                            out.println(size);
                            g = new Game(size,4);
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

                if(status == 1)
                {
                    if(boardsize.startsWith("CLOSED"))
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

                    if(boardsize.startsWith("INCOMING"))
                    {
                        String s = in.readLine();
                        for(Player p : this.g.players)
                        {
                            if(p.equals(this))
                            {
                                continue;
                            }
                            p.out.println("REGEX");
                            p.out.println(s);
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
