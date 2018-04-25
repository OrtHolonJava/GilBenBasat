package gchess.chess.players;

import gchess.boardgame.Move;
import gchess.chess.ChessGame;

/**
 * This interface is for creating new user interfaces that connects to HumanPlayer.
 */
public interface UI {
    /**
     * This function is called by the board-game platform as a request for a move from the player.
     * @param game The current game (feel free to get any data from the game and make any changes you want as it is
     *             only a copy of the game).
     */
    public Move getNextMove(ChessGame game);

    /**
     * This function is called by the board-game platform when the game initially started.
     * @param game The current game.
     */
    public void onGameStarted(ChessGame game);

    /**
     * This function is called by the board-game platform when after of the players of the game has made a move.
     * @param game The current game (after the move).
     */
    public void onPlayerMadeMove(ChessGame game);

    /**
     * This function is called by the board-game platform after the game has ended.
     * @param game The current game.
     */
    public void onGameEnded(ChessGame game);
}
