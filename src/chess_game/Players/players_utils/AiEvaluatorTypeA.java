package chess_game.Players.players_utils;

import board_game.Alliance;
import board_game.Position;
import chess_game.ChessGame;
import chess_game.ChessPiece;
import chess_game.interfaces.EvaluateMethodType;
import chess_game.pieces.*;

public class AiEvaluatorTypeA implements EvaluateMethodType {
    @Override
    public int evaluate(ChessGame game) {
        int evaluation = 0;
        for (Position pos : game.getOccupiedPositions()) {
            ChessPiece piece = game.getPiece(pos);
            int sign = piece.getAlliance() == Alliance.BLACK ? 1 : -1;
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
