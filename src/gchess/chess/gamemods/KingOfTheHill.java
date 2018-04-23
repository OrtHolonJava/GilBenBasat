package gchess.chess.gamemods;

import gchess.boardgame.Alliance;
import gchess.boardgame.BoardGame;
import gchess.boardgame.Position;
import gchess.boardgame.states.GameEnded;
import gchess.chess.ChessBoard;
import gchess.chess.ChessGame;
import gchess.chess.states.KingIsOnTheHill;

import java.util.List;

import static gchess.chess.utils.BoardUtils.*;
import static gchess.chess.utils.ChessGameUtils.enemyOf;

public class KingOfTheHill extends ChessGame {
    private final static List<Position> KING_WINNING_POSITIONS = List.of(TILE_D4, TILE_D5 ,TILE_E4, TILE_E5);

    @Override
    public BoardGame<ChessBoard> getCopy() {
        KingOfTheHill game = new KingOfTheHill();
        game._currentAlliance = _currentAlliance;
        game._board = (ChessBoard) _board.getCopy();
        return game;
    }

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
