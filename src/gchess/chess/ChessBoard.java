package gchess.chess;

import gchess.boardgame.Board;
import gchess.boardgame.Move;
import gchess.boardgame.Position;
import gchess.chess.enums.Piece;

import java.util.Collection;
import java.util.HashMap;
import java.util.Stack;

import static gchess.chess.utils.BoardUtils.pieceToEnum;

/**
 * <h1>Chess Board</h1>
 * This class helps implementing moves of pieces in a chess game.
 * <b>Note:</b> The board is not responsible for any move executions, it only saves data about the board state.
 *
 * @author Gil Ben Basat
 * @version 1.0
 * @since 2018-04-23
 */
public class ChessBoard extends Board<ChessPiece> {
    private static final int N = 8, M = 8; // The size of chess board: 8 X 8.
    private Stack<ChessPiece> _takenOutPieces; // Stack of all the pieces that have been eaten (for the undo operation).

    protected ChessBoard() {
        super();
        _takenOutPieces = new Stack<>();
    }

    /**
     * This function checks if a certain position is on the board.
     *
     * @param pos the position to be checked.
     * @return true if the position is on the board and false otherwise.
     */
    @Override
    public boolean isOnBoard(Position pos) {
        return pos.getX() >= 0 && pos.getX() < N && pos.getY() >= 0 && pos.getY() < M;
    }

    /**
     * This function returns a copy of the board so that any modifications on the copy won't affect the current
     * board.
     *
     * @return A copy of the board.
     */
    @Override
    public Board<ChessPiece> getCopy() {
        ChessBoard board = new ChessBoard();
        board._moveHistory = (Stack<Move>) _moveHistory.clone();
        board._takenOutPieces = (Stack<ChessPiece>) _takenOutPieces.clone();
        board._positionsToPieces = (HashMap<Position, ChessPiece>) _positionsToPieces.clone();
        return board;
    }

    /**
     * This function returns all the positions that are occupied by pieces.
     * @return Collection of positions of occupied positions.
     */
    public Collection<Position> getOccupiedPositions() {
        return _positionsToPieces.keySet();
    }

    /**
     * This function checks if a certain position is empty- that means on board and not occupied by any piece.
     * @param pos The position to be checked.
     * @return true if the position is empty, false otherwise.
     */
    public boolean isEmpty(Position pos) {
        return isOnBoard(pos) && getPiece(pos) == null;
    }

    /**
     * This function checks if a certain position is occupied by a piece.
     * @param pos The position to be checked.
     * @return true if the position is occupied, false otherwise.
     */
    public boolean isOccupied(Position pos) {
        return getPiece(pos) != null;
    }

    public Stack<Move> getMoveHistory() {
        return _moveHistory;
    }

    public Stack<ChessPiece> getTakenOutPieces() {
        return _takenOutPieces;
    }

    /**
     * This function returns a nice and easy representation of the chess board using N x M matrix and enums of pieces.
     * @return N x M matrix of pieces enums.
     */
    public Piece[][] getBoardRepresentation() {
        Piece[][] boardRep = new Piece[N][M];
        for (Position pos : getOccupiedPositions()) {
            boardRep[pos.getX()][pos.getY()] = pieceToEnum(getPiece(pos));
        }
        return boardRep;
    }
}
