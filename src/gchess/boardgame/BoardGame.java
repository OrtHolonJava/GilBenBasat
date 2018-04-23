package gchess.boardgame;

import java.util.Collection;

/**
 * <h1>Board Game</h1>
 * This class is the core of any implementation of a board game. Examples for games you can create on top of it:
 * Checkers, Chess, Backgammon, Monopoly and so much more.
 * @param <T> This is the board of the specific game we're using.
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public abstract class BoardGame <T extends Board> {
    protected T _board;
    protected BoardGame(T board) {
        _board = board;
    }

    /**
     * This function returns a collection of the possible (legal) moves of the current alliance.
     * @return Collection of possible moves.
     */
    public abstract Collection<Move> getPossibleMoves();

    /**
     * This function returns the current game state. e.g: IN_GAME, GAME_ENDED etc.
     * @return The current game state.
     */
    public abstract GameState getGameState();

    /**
     * This function executes the move that's given as a parameter and makes changes in the board as needed.
     * @param move The move that needs to be executed.
     */
    public abstract void makeMove(Move move);

    /**
     * This function return to the state of the game before the last move. Undo twice to go back two states before and
     * so on.
     */
    public abstract void undoMove();

    /**
     * This function returns a copy of the current game so that any modifications on the copy won't affect the current
     * game.
     * @return A copy of the game.
     */
    public abstract BoardGame<T> getCopy();
}
