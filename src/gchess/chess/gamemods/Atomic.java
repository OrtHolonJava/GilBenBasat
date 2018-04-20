package gchess.chess.gamemods;

import gchess.boardgame.BoardGame;
import gchess.boardgame.Move;
import gchess.boardgame.Position;
import gchess.boardgame.states.GameEnded;
import gchess.chess.ChessBoard;
import gchess.chess.ChessGame;
import gchess.chess.ChessPiece;
import gchess.chess.enums.Direction;
import gchess.chess.states.KingIsDead;
import gchess.chess.moves.AttackMove;
import gchess.chess.pieces.Pawn;
import gchess.chess.utils.Positions;

import java.util.*;

import static gchess.chess.enums.Direction.*;
import static gchess.chess.utils.ChessGameUtils.enemyOf;

public class Atomic extends ChessGame{
    private final static List<Direction> ATOMIC_RADIUS_DIRECTIONS = Arrays.asList(NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST,
        SOUTH_EAST, SOUTH_WEST);

    @Override
    public BoardGame<ChessBoard> getCopy() {
        Atomic game = new Atomic();
        game._currentAllianceTurn = _currentAllianceTurn;
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
