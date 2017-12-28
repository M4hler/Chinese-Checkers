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
                        Server.games.add(new Game(size));
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

    private void Games()
    {
        for(Game g : Server.games)
        {
            out.println("GAMES");
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
