package server;
// an instance of single game (multiple games can be played at one time at server)

import gameParts.Gameboard;

public class Game
{
    private Gameboard gameboard;

    public Game(int boardSize)
    {
        gameboard = new Gameboard(boardSize);
    }
}
