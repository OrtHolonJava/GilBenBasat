package chess_game.game_mods;

import board_game.BoardGame;
import board_game.Move;
import board_game.Position;
import board_game.game_states.GameEnded;
import chess_game.ChessBoard;
import chess_game.ChessGame;
import chess_game.ChessPiece;
import chess_game.enums.Direction;
import chess_game.game_states.KingIsDead;
import chess_game.moves.AttackMove;
import chess_game.pieces.Pawn;
import chess_game.utils.Positions;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static chess_game.enums.Direction.*;
import static chess_game.utils.ChessGameUtils.enemyOf;

public class Atomic extends ChessGame{
    private final static List<Direction> ATOMIC_RADIUS_DIRECTIONS = List.of(NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST,
        SOUTH_EAST, SOUTH_WEST);

    @Override
    public BoardGame<ChessBoard> getCopy() {
        Atomic game = new Atomic();
        game._allianceCycle = _allianceCycle.clone();
        game._allianceTurnIndex = _allianceTurnIndex;
        game._board = (ChessBoard) _board.getCopy();
        return game;
    }

    @Override
    protected void onMakeMoveFinished(Move move) {
        if (move instanceof AttackMove) {
            Stack<ChessPiece> takenOutPieces = _board.getTakenOutPieces();
            Position explosionSourcePos = move.getDestination();
            takenOutPieces.push(getPiece(explosionSourcePos));
            _board.removePiece(explosionSourcePos);
            for (Direction direction : ATOMIC_RADIUS_DIRECTIONS) {
                Position explosionPos = Positions.transform(explosionSourcePos, direction);
                if (!(getPiece(explosionPos) instanceof Pawn)) {
                    takenOutPieces.push(getPiece(explosionPos));
                    _board.removePiece(explosionPos);
                }
            }
        }
    }

    @Override
    protected void onUndoMoveStarted(Move move) {
        if (move instanceof AttackMove) {
            Stack<ChessPiece> takenOutPieces = _board.getTakenOutPieces();
            List<Direction> reversedDirections = ATOMIC_RADIUS_DIRECTIONS.subList(0, ATOMIC_RADIUS_DIRECTIONS.size());
            Collections.reverse(reversedDirections);
            Position explosionSourcePos = move.getDestination();
            for (Direction direction : reversedDirections) {
                Position explosionPos = Positions.transform(explosionSourcePos, direction);
                if (!(getPiece(explosionPos) instanceof Pawn)) {
                    _board.setPiece(explosionPos, takenOutPieces.pop());
                }
            }
            _board.setPiece(explosionSourcePos, takenOutPieces.pop());
        }
    }

    @Override
    protected boolean isKingHasToBeSafe() {
        return false;
    }

    @Override
    protected boolean isUnderCheck() {
        return false; // There are no checks in Atomic-Chess
    }

    @Override
    protected GameEnded getSpecialEndGameState() {
        if (getKingPosition(getCurrentTurnAlliance()) == null) {
            return new KingIsDead(enemyOf(getCurrentTurnAlliance()));
        }
        return null;
    }
}
