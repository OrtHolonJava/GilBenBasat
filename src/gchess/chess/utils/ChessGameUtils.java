package gchess.chess.utils;

import gchess.boardgame.Alliance;
import gchess.boardgame.Position;
import gchess.chess.ChessBoard;
import gchess.chess.enums.Direction;

import static gchess.boardgame.Alliance.*;

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
