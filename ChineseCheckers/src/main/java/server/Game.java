package server;
// an instance of single game (multiple games can be played at one time at server)

import gameParts.*;
import gameParts.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Game
{
    ArrayList<Player> players;
    private ArrayList<AI> computerPlayers;
    private HashMap<PlayerColor,Integer> score;
    public Field[][] gameboard;
    int valueNeededForWindowToDrawBoard;
    boolean inProgress;
    PlayerColor currentPlayer;
    int numberOfPlayers;
    private ArrayList<PlayerColor> playerColors;
    ArrayList<PlayerColor> currentColors;

    private Point constants[];
    int boardSize;

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

        score = new HashMap<>();
        int x=((1+boardSize)*boardSize)/2;
        for(PlayerColor p:playerColors){
            score.put(p,x);
        }
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
        if(currentPlayer==null){
            return false;
        }
        return (returnPossibleMoves(x1, y1).contains(new Point(x2, y2)));
    }

    void decodeMessage(String message)
    {
        String code[]=message.split(",");
        if(code[0].equals("getMoves")) {
            int x, y;
            x = Integer.parseInt(code[1]);
            y = Integer.parseInt(code[2]);

            if(!(gameboard[x][y].getPawn().getColor() == currentPlayer))
            {
                return;
            }

            String array = "returnMoves";
            ArrayList<Point> p = returnPossibleMoves(x, y);
            if (p == null) {
                return;
            }
            for (Point point : p) {
                array = array + "," + point.getX() + "," + point.getY();
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

    private void sendMessage(String message)
    {
        if(currentPlayer==null) return;
        getPlayerByColor(currentPlayer).returnMessage(message);
    }

    void addPlayer(Player player)
    {
        players.add(player);
    }

    void setStartingPlayer()
    {
        if(players.size()<numberOfPlayers){
            fillWithAI();
        }
        Random ran = new Random();
        currentPlayer = players.get(ran.nextInt(players.size())).getColor();
    }

    private void fillWithAI(){
        computerPlayers=new ArrayList<>();
        for(PlayerColor p: playerColors){
            boolean add=true;
            for(Player player:players){
                if(player.getColor()==p){
                    add=false;
                }
                if(add){
                    computerPlayers.add(new AI(this,p));
                }
                add=true;
            }
        }
    }

    void changeTurn()
    {
        int i = playerColors.indexOf(currentPlayer);
        i++;
        i=i%numberOfPlayers;
        currentPlayer=playerColors.get(i);
        if(computerPlayers!=null) {
            for (AI ai : computerPlayers) {
                if (ai.getPlayerColor() == currentPlayer) {
                    ai.makeMove();
                    break;
                }
            }
        }
        for(Player p: players){
            if(p.getColor()==currentPlayer){
                refreshCurrentPlayerView();
                break;
            }
        }
    }
    Player getPlayerByColor(PlayerColor playerColor){
        Player player=null;
        for(Player p : players)
        {
            if(p.getColor()==playerColor){
                player=p;
            }
        }
        return player;
    }

    void refreshCurrentPlayerView(){

        for(Player p : players)
        {
            p.changeCurrentPlayer(getPlayerByColor(currentPlayer));
        }
    }

    void move(int x1,int y1,int x2,int y2)
    {
        if(gameboard[x1][y1].getColor()==null&&gameboard[x2][y2]!=null) {
            if (gameboard[x2][y2].getColor() == getEnemyColor(gameboard[x1][y1].getPawn().getColor())) {
                PlayerColor key=gameboard[x1][y1].getPawn().getColor();
                int val = score.get(key);
                val--;
                score.replace(key,val);
                if(val==0){
                    for(Player p : players)
                    {
                        p.returnMessage("pog,"+key.name());
                    }

                }
            }
        }
        gameboard[x2][y2].setPawn(new Pawn(gameboard[x1][y1].getPawn().getColor()));
        gameboard[x1][y1].setPawn(null);
        for(Player p : players)
        {
            p.returnMessage("move,"+x1+","+y1+","+x2+","+y2);
        }

        changeTurn();
    }

    //returns an arraylist, that contains all fields allowed to move from field[x][y]
    public ArrayList<Point> returnPossibleMoves(int x, int y)
    {
        if(currentPlayer==null){
            return null;
        }
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
        if(gameboard[x][y].getColor()!=null){
            if(gameboard[x][y].getColor()==getEnemyColor(gameboard[x][y].getPawn().getColor())) {

                PlayerColor c = getEnemyColor(gameboard[x][y].getPawn().getColor());

                Iterator<Point> iterator = possibleMoves.iterator();
                while (iterator.hasNext()) {
                    Point p = iterator.next();

                    if (gameboard[p.getX()][p.getY()].getColor() != c)
                        iterator.remove();
                }

            }
        }
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

    ArrayList<PlayerColor> getPlayerColors() {
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
                pc.add(PlayerColor.YELLOW);
                pc.add(PlayerColor.BLUE);
                break;
            case 4:
                pc.add(PlayerColor.RED);
                pc.add(PlayerColor.WHITE);
                pc.add(PlayerColor.BLACK);
                pc.add(PlayerColor.BLUE);

                break;
            case 6:
                pc.add(PlayerColor.RED);
                pc.add(PlayerColor.WHITE);
                pc.add(PlayerColor.YELLOW);
                pc.add(PlayerColor.BLACK);
                pc.add(PlayerColor.BLUE);
                pc.add(PlayerColor.GREEN);
                break;
        }
        return pc;
    }
}

