package gchess.chess.players;

import gchess.boardgame.Alliance;
import gchess.chess.ChessGame;

/**
 * This interface allows for multiple evaluation functions for the ai.
 */
public interface EvaluateMethodType {
    /**
     * This function evaluates a current game and assign a value to it. It is used by an ai.
     * @param game The current game to be evaluated.
     * @param aiAlliance The alliance of the ai for it to evaluate for the ai's alliance.
     * @return The evaluation of the game.
     */
    public int evaluate(ChessGame game, Alliance aiAlliance);
}
