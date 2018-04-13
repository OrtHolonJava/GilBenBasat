package chess_game;

import board_game.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

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

     public String[][] getBoardRepresentation() {
        String[][] boardRep = new String[N][M];
         for (Position pos : getOccupiedPositions()) {
             boardRep[pos.getX()][pos.getY()] = getPiece(pos).toString();
         }
         return boardRep;
     }
}
