package server;
// an instance of single game (multiple games can be played at one time at server)

import gameParts.*;

import java.util.ArrayList;
import java.util.Random;

public class Game
{
    public ArrayList<Player> players;
    public Field[][] gameboard;
    public int valueNeededForWindowToDrawBoard; //temporary name
    public boolean inProgress; //added
    public Player currentPlayer; //added
    public int numberOfPlayers;
    public ArrayList<PlayerColor> playerColors;
    public ArrayList<PlayerColor> currentColors;

    private Point constants[];
    public int boardSize;

    //ArrayList<Point> possibleMoves = new ArrayList<>();

    public Game(int boardSize,int numberOfPlayers)
    {
        GameboardCreator creator = new GameboardCreator(boardSize,numberOfPlayers);
        constants = creator.getConstants();
        gameboard = creator.getBoard();
        this.boardSize = 4 * boardSize + 1;
        this.numberOfPlayers = numberOfPlayers;
        valueNeededForWindowToDrawBoard = boardSize;

        players = new ArrayList<>();
        inProgress = false; //added
        currentPlayer = null; //added

        playerColors = setPlayerColors(numberOfPlayers);
        currentColors = new ArrayList<>();
    }


    PlayerColor getEnemyColor(PlayerColor p){
        switch (p){
            case YELLOW: return PlayerColor.GREEN;
            case WHITE: return PlayerColor.BLUE;
            case RED: return PlayerColor.BLACK;
            case GREEN:return PlayerColor.YELLOW;
            case BLUE:return PlayerColor.WHITE;
            default: return PlayerColor.RED;
        }
    }

    public boolean canMove(int x1, int y1, int x2, int y2)
    {
        return (returnPossibleMoves(x1, y1).contains(new Point(x2, y2)));
    }

    void decodeMessage(String message)
    {
        String code[]=message.split(",");
        if(code[0].equals("getMoves"))
        {
            int x,y;
            x=Integer.parseInt(code[1]);
            y=Integer.parseInt(code[2]);
            String array="returnMoves";
            ArrayList<Point> p = returnPossibleMoves(x,y);
            for(Point point: p){
                array=array+","+point.getX()+","+point.getY();
            }
            array += ",end";
            sendMessage(array); //sending fileds where player can move

        }
        else if(code[0].equals("canMove"))
        {
            int x1,y1,x2,y2;
            x1=Integer.parseInt(code[1]);
            y1=Integer.parseInt((code[2]));
            x2=Integer.parseInt((code[3]));
            y2=Integer.parseInt((code[4]));
            boolean b=canMove(x1,y1,x2,y2);
            if(b)
            {
                move(x1,y1,x2,y2);
                for(Player p : players)
                {
//                    sendMessage("move,"+x1+","+y1+","+x2+","+y2); //TODO: TO ALL PLAYERS
                    p.returnMessage("move,"+x1+","+y1+","+x2+","+y2);
                }

                changeTurn();
            }
            else
            {
                sendMessage("wrongMove");
            }
        }
        else if(code[0].equals("changeTurn"))
        {
            changeTurn();
        }
    }

    void sendMessage(String message)
    {
        currentPlayer.returnMessage(message);
    }

    void setStartingPlayer()
    {
        Random ran = new Random();
        currentPlayer = players.get(ran.nextInt(players.size()));
    }

    private void changeTurn()
    {
        int i = players.indexOf(currentPlayer);
        if(i == players.size() - 1)
        {
            currentPlayer = players.get(0);
        }
        else
        {
            currentPlayer = players.get(i + 1);
        }
    }

    public void move(int x1,int y1,int x2,int y2)
    {
        gameboard[x2][y2].setPawn(new Pawn(gameboard[x1][y1].getPawn().getColor()));
        gameboard[x1][y1].setPawn(null);
    }








    //returns an arraylist, that contains all fields allowed to move from field[x][y]
    public ArrayList<Point> returnPossibleMoves(int x, int y)
    {
        ArrayList<Point> possibleMoves = new ArrayList<>();
        int tempX, tempY;

        for (Point direction : constants) {
            tempX = x + direction.getX();
            tempY = y + direction.getY();

            if (tempX < 0 || tempY < 0 || tempX >= boardSize || tempY >= boardSize || gameboard[tempX][tempY] == null)
            {
                continue;
            }

            if (gameboard[tempX][tempY].getPawn() == null) { //empty filed, able to move
                if (!possibleMoves.contains(new Point(tempX, tempY)))
                    possibleMoves.add(new Point(tempX, tempY));
            }
            else { //checks if is possible to jump over pawn
                tempX += direction.getX();
                tempY += direction.getY();
                if (tempX < 0 || tempY < 0 || tempX >= boardSize || tempY >= boardSize || gameboard[tempX][tempY] == null)
                {
                    continue;
                }

                if (gameboard[tempX][tempY].getPawn() == null)
                {
                    if (!possibleMoves.contains(new Point(tempX, tempY))) {
                        possibleMoves.add(new Point(tempX, tempY));
                        searchJumps(tempX, tempY, direction, possibleMoves);
                    }
                }
            }
        }
        //deletes moves that allows to get out from enemy's triangle (Its forbidden by rules)
        /*if(gameboard[x][y].getColor()!=null){
            if(gameboard[x][y].getColor()==getEnemyColor(currentPlayer.getColor())){
                PlayerColor c = getEnemyColor(currentPlayer.getColor());
                for(Point p: possibleMoves){
                    if(gameboard[p.getY()][p.getY()].getColor()!=c){
                        possibleMoves.remove(p);
                    }
                }
            }
        }*/
        return possibleMoves;
    }

    private void searchJumps(int x, int y, Point comingFrom, ArrayList<Point> possibleMoves)
    {
        int tempX, tempY;
        for (Point direction : constants) {
            if ((direction.getX() == -(comingFrom.getX())) && (direction.getY() == -(comingFrom.getY())))
            {
                continue;
            }
            //^^avoid checking direction where we did come from
            tempX = x + direction.getX();
            tempY = y + direction.getY();

            if (tempX < 0 || tempY < 0 || tempX >= boardSize || tempY >= boardSize || gameboard[tempX][tempY] == null)
            {
                continue;
            }

            if (gameboard[tempX][tempY].getPawn() != null)
            {
                tempX += direction.getX();
                tempY += direction.getY();

                if (tempX < 0 || tempY < 0 || tempX >= boardSize || tempY >= boardSize || gameboard[tempX][tempY] == null)
                {
                    continue;
                }

                if (gameboard[tempX][tempY].getPawn() == null)
                {
                    if (!possibleMoves.contains(new Point(tempX, tempY)))
                    {
                        possibleMoves.add(new Point(tempX, tempY));
                        searchJumps(tempX, tempY, direction, possibleMoves);
                    }
                }
            }
        }
    }

    public ArrayList<PlayerColor> getPlayerColors() {
        return playerColors;
    }

    private ArrayList<PlayerColor> setPlayerColors(int number)
    {
        ArrayList<PlayerColor> pc = new ArrayList<>();

        switch(number)
        {
            case 2:
                pc.add(PlayerColor.RED);
                pc.add(PlayerColor.BLACK);
                break;
            case 3:
                pc.add(PlayerColor.RED);
                pc.add(PlayerColor.BLUE);
                pc.add(PlayerColor.YELLOW);
                break;
            case 4:
                pc.add(PlayerColor.RED);
                pc.add(PlayerColor.BLACK);
                pc.add(PlayerColor.BLUE);
                pc.add(PlayerColor.WHITE);
                break;
            case 6:
                pc.add(PlayerColor.RED);
                pc.add(PlayerColor.BLACK);
                pc.add(PlayerColor.BLUE);
                pc.add(PlayerColor.WHITE);
                pc.add(PlayerColor.GREEN);
                pc.add(PlayerColor.YELLOW);
                break;
        }
        return pc;
    }

}

