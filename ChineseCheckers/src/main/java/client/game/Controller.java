package client.game;

import client.Client;
import gameParts.Point;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Controller
{
    private GameBoardPanel panel;
    FieldButton lastClicked;
    ArrayList<Point> highlighted;
    private Client client;

    private String name;
    private BufferedReader input;
    private PrintWriter output;

    public Controller(Client myclient)
    {
        lastClicked = null;
        highlighted = null;
        panel = null;
        name = "";
        client = myclient;

        try
        {
            Socket socket = new Socket(client.setServerAddress(), 8080);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        setName();
    }

    public void createGame(String s)
    {
        int i;
        while(true)
        {
            try
            {
                String playersNumber = getNumberOfPlayers();
                i = Integer.valueOf(playersNumber);
            }
            catch(NumberFormatException e)
            {
                continue;
            }
            break;
        }

        output.println("CREATE");
        output.println(s);
        output.println(i);
    }

    public void joinGame(int index)
    {
        output.println("JOIN GAME");
        output.println(index);
    }

    void pass()
    {
        output.println("PASS");
    }

    public void run()
    {
        while(true)
        {
            try
            {
                String line = input.readLine();

                if(line.equals("RESET"))
                {
                    client.clearLobby();
                }
                else if(line.equals("NEW GAME WINDOW"))
                {
                    String newline = input.readLine();
                    String number = input.readLine();
                    client.drawWindow(newline, number);
                }
                else if(line.equals("GAME"))
                {
                    client.clearLobby();
                    String newline = input.readLine();
                    String number = input.readLine();
                    client.drawWindow(newline, number);
                }
                else if(line.equals("NEW GAME"))
                {
                    ArrayList<String> players = collectInformationAboutGame();
                    client.refreshLobby(players);
                }
                else if(line.equals("REFRESH"))
                {
                    client.frame.setVisible(true);
                    client.refresh();
                }
                else if(line.equals("TEST"))
                {
                    String newline = input.readLine();
                    decodeMessage(newline);
                }
                else if(line.equals("CURRENT PLAYER"))
                {
                    String newline = input.readLine();
                    panel.setCurrentPlayerDisplay(newline);
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<String> collectInformationAboutGame()
    {
        ArrayList<String> players = new ArrayList<>();
        try
        {
            String line = input.readLine();
            while(!line.equals("STOP"))
            {
                players.add(line);
                line = input.readLine();
            }
            return players;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return players;
    }

    private String getNumberOfPlayers()
    {
        return JOptionPane.showInputDialog(
                client.frame,
                "Give number of players:",
                "Number of players",
                JOptionPane.PLAIN_MESSAGE);
    }

    void startGame()
    {
        output.println("START GAME");
    }

    void closeGame()
    {
        output.println("CLOSE GAME");
    }

    private void setName()
    {
        while(true)
        {
            try
            {
                String line = input.readLine();
                if(line.equals("SUBMITNAME"))
                {
                    name = client.getName();
                    output.println(name);
                }
                else if(line.equals("NAME ACCEPTED"))
                {
                    break;
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public String getName()
    {
        return name;
    }

    void addPanel(GameBoardPanel panel)
    {
        this.panel=panel;
    }

    void highlight(ArrayList<Point> array)
    {
        highlighted=array;
        panel.highlight(array);
    }

    void lowlight()
    {
        if(highlighted == null)
        {
            return;
        }

        if(highlighted.size()!=0)
        {
            panel.lowlight(highlighted);
            highlighted.clear();
        }
    }

    void doMove(int x1,int y1,int x2,int y2)
    {
        lowlight();
        lastClicked=null;
        panel.movePawn(x1,y1,x2,y2);
    }

    void fieldButtonClicked(FieldButton b) {
        if (lastClicked == null) {
            if (b.getPawn() != null) {
                lastClicked = b;
                sendToServer("getMoves," + b.getx() + "," + b.gety());
            }
        } else {
            if (lastClicked.equals(b)) {
                lastClicked = null;
                fieldButtonClicked(b);
            } else if (b.getPawn() != null) {
                lastClicked = b;
                lowlight();
                sendToServer("getMoves,"+b.getx()+","+b.gety());
            } else {
                int x1 = lastClicked.getx();
                int y1 = lastClicked.gety();
                int x2 = b.getx();
                int y2 = b.gety();
//                lowlight();
                sendToServer("canMove," + x1 + "," + y1 + "," + x2 + "," + y2);
            }
        }
    }

    void sendToServer(String code)
    {
        output.println(code);
    }

    void decodeMessage(String message)
    {
        String code[]=message.split(",");

        if(code[0].equals("move")){
            int x1,y1,x2,y2;
            x1=Integer.parseInt(code[1]);
            y1=Integer.parseInt(code[2]);
            x2=Integer.parseInt(code[3]);
            y2=Integer.parseInt(code[4]);
            doMove(x1,y1,x2,y2);

        }else if(code[0].equals("wrongMove")){
            lastClicked=null;
            lowlight();
            // add info to optionspanel or sth
        }else if(code[0].equals("returnMoves")){
            int i=1;
            int x,y;
            ArrayList<Point> array=new ArrayList<>();

            while(!code[i].equals("end")){
                x=Integer.parseInt(code[i]);
                y=Integer.parseInt(code[i+1]);
                i+=2;
                array.add(new Point(x,y));
            }
            highlight(array);
        }else if(code[0].equals("pog")){
            String msg=code[1];
            JOptionPane.showMessageDialog(null,msg+" WON!!!","Wow",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("pog.jpg"));

        }
    }
}
