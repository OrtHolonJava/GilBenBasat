package gchess.chess.players;

import gchess.boardgame.Alliance;
import gchess.boardgame.Move;
import gchess.chess.ChessGame;
import gchess.chess.ChessPlayer;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class AIPlayer extends ChessPlayer {
    private int _depth;
    private EvaluateMethodType _evaluationFunc;

    public AIPlayer(String name, Alliance alliance, int depth, EvaluateMethodType evaluationFunc) {
        super(name, alliance);
        _depth = depth;
        _evaluationFunc = evaluationFunc;
    }

    private int minimax(ChessGame game, int depth, int alpha, int beta, boolean isMaximizing) { // minimax with alpha-beta puring
        if (depth == 0) {
            int boardEvaluation = _evaluationFunc.evaluate(game, _alliance);
            return isMaximizing ? boardEvaluation : -boardEvaluation;
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

    @Override
    public Move getNextMove(ChessGame game) {
        int maxValue = Integer.MIN_VALUE;
        Move bestMove = null;
        Collection<Move> possibleMoves = game.getPossibleMoves();
        Collections.shuffle((List<?>) possibleMoves);
        for (Move move : possibleMoves) {
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
