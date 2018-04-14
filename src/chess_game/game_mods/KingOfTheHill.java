package chess_game.game_mods;

import board_game.Alliance;
import board_game.Position;
import board_game.game_states.GameEnded;
import chess_game.ChessGame;
import chess_game.enums.Direction;
import chess_game.game_states.KingIsOnTheHill;

import java.util.List;

import static chess_game.enums.Direction.*;
import static chess_game.enums.Direction.SOUTH_WEST;
import static chess_game.utils.BoardUtils.*;
import static chess_game.utils.ChessGameUtils.enemyOf;

public class KingOfTheHill extends ChessGame {
    private final static List<Position> KING_WINNING_POSITIONS = List.of(TILE_D4, TILE_D5 ,TILE_E4, TILE_E5);
    @Override
    protected GameEnded getSpecialEndGameState() {
        Alliance kingAlliance = enemyOf(getCurrentTurnAlliance());
        Position kingPos = getKingPosition(kingAlliance);
        for (Position pos : KING_WINNING_POSITIONS) {
            if (pos.equals(kingPos)) {
                return new KingIsOnTheHill(kingAlliance);
            }
        }
        return null;
    }
}
