package chess_game.utils;

import board_game.Alliance;

import static board_game.Alliance.*;

public class ChessGameUtils {
    public static Alliance enemyOf(Alliance alliance) {
        if (alliance == WHITE) {
            return BLACK;
        }
        return WHITE;
    }
}
