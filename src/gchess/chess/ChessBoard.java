package gchess.chess;

import gchess.boardgame.*;
import gchess.chess.enums.Piece;
import gchess.chess.pieces.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

import static gchess.boardgame.Alliance.*;
import static gchess.chess.enums.Piece.*;

public class ChessBoard extends Board<ChessPiece> {
    private static final int N = 8, M = 8;
    private Stack<ChessPiece> _takenOutPieces;

    protected ChessBoard() {
        super();
        _takenOutPieces = new Stack<>();
    }

    @Override
    public boolean isOnBoard(Position pos) {
        return pos.getX() >= 0 && pos.getX() < N && pos.getY() >= 0 && pos.getY() < M;
    }

    @Override
    public Board<ChessPiece> getCopy() {
        ChessBoard board = new ChessBoard();
        board._moveHistory = (Stack<Move>)_moveHistory.clone();
        board._takenOutPieces = (Stack<ChessPiece>) _takenOutPieces.clone();
        board._positionsToPieces = (HashMap<Position, ChessPiece>) _positionsToPieces.clone();
        return board;
    }

    public Collection<Position> getOccupiedPositions() {
        return _positionsToPieces.keySet();
    }

    public boolean isEmpty(Position pos) {
        return isOnBoard(pos) && getPiece(pos) == null;
    }
    public boolean isOccupied(Position pos) { return _positionsToPieces.get(pos) != null; };

     public Stack<Move> getMoveHistory() {
        return _moveHistory;
     }

     public Stack<ChessPiece> getTakenOutPieces() {
        return _takenOutPieces;
     }

     public Piece[][] getBoardRepresentation() {
         Piece[][] boardRep = new Piece[N][M];
         for (Position pos : getOccupiedPositions()) {
             boardRep[pos.getX()][pos.getY()] = pieceToEnum(getPiece(pos));
         }
         return boardRep;
     }

     private Piece pieceToEnum(ChessPiece piece) {
         if (piece instanceof Pawn)  {
             return piece.getAlliance() == WHITE ? WHITE_PAWN : BLACK_PAWN;
         } else if (piece instanceof Knight) {
             return piece.getAlliance() == WHITE ? WHITE_KNIGHT : BLACK_KNIGHT;
         } else if (piece instanceof Bishop) {
             return piece.getAlliance() == WHITE ? WHITE_BISHOP : BLACK_BISHOP;
         } else if (piece instanceof Rook) {
             return piece.getAlliance() == WHITE ? WHITE_ROOK : BLACK_ROOK;
         } else if (piece instanceof Queen) {
             return piece.getAlliance() == WHITE ? WHITE_QUEEN : BLACK_QUEEN;
         } else if (piece instanceof King) {
             return piece.getAlliance() == WHITE ? WHITE_KING : BLACK_KING;
         }
         return null;
     }
}
