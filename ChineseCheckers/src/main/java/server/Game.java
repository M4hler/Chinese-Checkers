package server;
// an instance of single game (multiple games can be played at one time at server)

import gameParts.GameboardCreator;
import java.util.ArrayList;

public class Game
{
    private GameboardCreator gameboard;
    public ArrayList<Player> players;

    public Game(int boardSize)
    {
        players = new ArrayList<Player>();
        gameboard = new GameboardCreator(boardSize);
    }
}
