package gchess.chess.players.evaluators;

import gchess.boardgame.Alliance;
import gchess.boardgame.Position;
import gchess.chess.ChessGame;
import gchess.chess.ChessPiece;
import gchess.chess.pieces.*;
import gchess.chess.players.EvaluateMethodType;

public class AiEvaluatorTypeA implements EvaluateMethodType {
    @Override
    public int evaluate(ChessGame game, Alliance aiAlliance) {
        int evaluation = 0;
        for (Position pos : game.getOccupiedPositions()) {
            ChessPiece piece = game.getPiece(pos);
            int sign = piece.getAlliance() == aiAlliance ? 1 : -1;
            if (piece instanceof Pawn) {
                evaluation += sign;
            } else if (piece instanceof Knight) {
                evaluation += 3 * sign;
            } else if (piece instanceof Bishop) {
                evaluation += 3 * sign;
            } else if (piece instanceof Rook) {
                evaluation += 5 * sign;
            } else if (piece instanceof Queen) {
                evaluation += 9 * sign;
            } else if (piece instanceof King) {
                evaluation += 100 * sign;
            }
        }
        return evaluation;
    }

}
