package server;
// an instance of single game (multiple games can be played at one time at server)

import gameParts.GameboardCreator;

public class Game
{
    private GameboardCreator gameboard;

    public Game(int boardSize)
    {
        gameboard = new GameboardCreator(boardSize);
    }
}
