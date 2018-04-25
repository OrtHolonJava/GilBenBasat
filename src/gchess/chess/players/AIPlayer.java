package gchess.chess.players;

import gchess.boardgame.Alliance;
import gchess.boardgame.Move;
import gchess.chess.ChessGame;
import gchess.chess.ChessPlayer;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * This is an implementation of a board-game player played by an ai.
 */
public class AIPlayer extends ChessPlayer {
    private int _depth;
    private EvaluateMethodType _evaluationFunc;

    public AIPlayer(String name, Alliance alliance, int depth, EvaluateMethodType evaluationFunc) {
        super(name, alliance);
        _depth = depth;
        _evaluationFunc = evaluationFunc;
    }

    /**
     * This function is a minimax function and its goal is to assign value to a current game state.
     * It is done using a minimax tree of possible states up to a cetain depth.
     * @param game The current game.
     * @param depth The depth of the minimax tree.
     * @param alpha The highest value so far. (for alpha-beta puring, check for it with google)
     * @param beta The lowest value so far. (for alpha-beta puring, check for it with google)
     * @param isMaximizing Tells if the value that is assigned to the current state is from a maximizing prespective or
     *                     from a minimizing prespective.
     * @return The value of the state.
     */
    private int minimax(ChessGame game, int depth, int alpha, int beta, boolean isMaximizing) { // minimax with alpha-beta puring
        if (depth == 0) {
            return _evaluationFunc.evaluate(game, _alliance);
        }
        int temp = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Move move : game.getPossibleMoves()) {
            game.makeMove(move);
            int value = minimax(game, depth - 1, alpha, beta, !isMaximizing);
            game.undoMove();
            if (isMaximizing) {
                if (value > temp) {
                    temp = value;
                }
                alpha = max(value, alpha);
            } else {
                if (value < temp) {
                    temp = value;
                }
                beta = min(value, beta);
            }
            if (alpha >= beta) {
                break; // stops the search for this tree because it's redundant
            }
        }
        return temp;
    }

    @Override
    public void onPlayerMadeMove(ChessGame game) {

    }

    @Override
    public void onGameStarted(ChessGame game) {

    }

    /**
     * This function is called by the board-game platform as a request for a move from the player.
     * @param game The current game (feel free to get any data from the game and make any changes you want as it is
     *             only a copy of the game).
     */
    @Override
    public Move getNextMove(ChessGame game) {
        int maxValue = Integer.MIN_VALUE;
        Move bestMove = null;
        Collection<Move> possibleMoves = game.getPossibleMoves();
        List<Move> shuffeledMoves = (List<Move>) possibleMoves;
        Collections.shuffle(shuffeledMoves);
        for (Move move : shuffeledMoves) {
            game.makeMove(move);
            int value = minimax(game, _depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            game.undoMove();
            if (value > maxValue) {
                maxValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }

    @Override
    public void onGameEnded(ChessGame game) {

    }
}
