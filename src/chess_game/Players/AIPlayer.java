package chess_game.Players;

import board_game.Alliance;
import board_game.Move;
import board_game.Player;
import chess_game.ChessGame;
import chess_game.ChessPlayer;
import chess_game.interfaces.EvaluateMethodType;

import java.util.concurrent.Callable;

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
            int boardEvaluation = _evaluationFunc.evaluate(game);
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
            }
		    else {
                if (temp < value) {
                    temp = value;
                }
                beta = min(value, beta);
                if (alpha >= beta) {
                    break; // stops the search for this tree because it's redundant
                }
            }
        }
        return temp;
    }

    @Override
    public Move getNextMove(ChessGame game) {
        int maxValue = Integer.MIN_VALUE;
        Move bestMove = null;
        for (Move move : game.getPossibleMoves()){
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
