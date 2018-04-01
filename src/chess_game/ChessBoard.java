package chess_game;

import board_game.*;
import chess_game.moves.AttackMove;
import chess_game.moves.CastleMove;
import chess_game.moves.EnPassantMove;
import chess_game.moves.PromotionMove;
import chess_game.pieces.King;
import chess_game.pieces.Pawn;
import chess_game.pieces.Rook;

import java.util.Collection;
import java.util.Stack;

public class ChessBoard extends Board<ChessPiece> {
    private Stack<ChessPiece> _takenOutPieces;

    protected ChessBoard() {
        super();
        _takenOutPieces = new Stack<>();
    }

    public Collection<Position> getOcupiedPositions() {
        return _positionsToPieces.keySet();
    }

    @Override
    public boolean isOnBoard(Position pos) {
        return false;
    }

    @Override
    public void executeMove(Move move) {
        ChessPiece movingPiece = getPiece(move.getSource());

        if (movingPiece instanceof Rook) {
            ((Rook) movingPiece).incMoveCount();
        }
        else if (movingPiece instanceof King) {
            ((King) movingPiece).incMoveCount();
        }

        if (move instanceof AttackMove) {
            Position attackedPiecePos;
            if (move instanceof EnPassantMove) {
                attackedPiecePos = ((EnPassantMove) move).getEnemyPos();
            }
            else {
                attackedPiecePos = move.getDestination();
            }
            _takenOutPieces.push(getPiece(attackedPiecePos));
            removePiece(attackedPiecePos);
        }
        else if (move instanceof CastleMove) {
            movePiece(((CastleMove) move).getRookSource(), ((CastleMove) move).getRookDestination());
        }
        else if (move instanceof PromotionMove) {
            setPiece(move.getSource(), ((PromotionMove) move).getPromotionPiece());
        }
        movePiece(move.getSource(), move.getDestination());
        _moveHistory.push(move);

    }

    @Override
    public void undo() {
        Move move = _moveHistory.pop();
        ChessPiece movingPiece = getPiece(move.getDestination());

        movePiece(move.getDestination(), move.getSource());

        if (movingPiece instanceof Rook) {
            ((Rook) movingPiece).decMoveCount();
        }
        else if (movingPiece instanceof King) {
            ((King) movingPiece).decMoveCount();
        }

        if (move instanceof AttackMove) {
            Position attackedPiecePos;
            if (move instanceof EnPassantMove) {
                attackedPiecePos = ((EnPassantMove) move).getEnemyPos();
            }
            else {
                attackedPiecePos = move.getDestination();
            }
            setPiece(attackedPiecePos, _takenOutPieces.pop());
        }
        else if (move instanceof CastleMove) {
            movePiece(((CastleMove) move).getRookDestination(), ((CastleMove) move).getRookSource());
        }
        else if (move instanceof PromotionMove) {
            setPiece(move.getSource(), new Pawn(movingPiece.getAlliance()));
        }
    }

    public boolean isEmpty(Position pos) {
        return isOnBoard(pos) && getPiece(pos) == null;
    }
}
