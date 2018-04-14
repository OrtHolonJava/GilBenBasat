package chess_game.utils;

import board_game.Alliance;
import board_game.Position;
import chess_game.ChessBoard;
import chess_game.enums.Direction;

import static board_game.Alliance.*;

public class ChessGameUtils {
    public static Alliance enemyOf(Alliance alliance) {
        if (alliance == WHITE) {
            return BLACK;
        }
        return WHITE;
    }

    public static boolean isTilesEmptyBetween(ChessBoard board, Position source, Direction direction, Position destination) {
        Position currentPos = Positions.transform(source, direction);
        while (board.isEmpty(currentPos)) {
            if (currentPos.equals(destination)) {
                return true;
            }
            currentPos = Positions.transform(currentPos, direction);
        }
        return false;
    }
}
