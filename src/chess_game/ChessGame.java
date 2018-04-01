package chess_game;

import board_game.*;
import chess_game.enums.Direction;
import chess_game.moves.*;
import chess_game.pieces.King;
import chess_game.pieces.Pawn;
import chess_game.pieces.Rook;
import chess_game.utils.Positions;
import exceptions.IllegalMoveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static chess_game.enums.Direction.*;
import static chess_game.utils.BoardUtils.* ;

public class ChessGame extends BoardGame<ChessBoard> {
    public ChessGame(ChessBoard board) {
        super(board, Arrays.asList(Alliance.WHITE, Alliance.BLACK));
    }

    @Override
    public Collection<Move> getPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        Alliance alliance = _allianceCycle[_allianceTurnIndex];
        GameState currentGameState = getGameState();

        /* Add Regular Moves: includes pawn's double-tile move and pawn promotion */
        moves.addAll(getRegularMoves(alliance));

        /* Add Special Move: Pawn En Passant */
        moves.addAll(getEnPassentMoves(alliance));

        /* Add Special Move: Castle */
        moves.addAll(getCastleMoves(alliance));

        /* In Case King has to be Safe: remove all the moves that makes the king un-safe. */
        if (isKingHasToBeSafe()) {
            ArrayList<Move> safeForKingMoves = new ArrayList<>();
            Position kingPos = getKingPosition(alliance);
            if (kingPos == null) {
                throw new RuntimeException("King was not found.");
            }
            for (Move move : moves) {
                _board.executeMove(move);
                if(isPositionSafe(kingPos, alliance)) {
                    safeForKingMoves.add(move);
                }
                _board.undo();
            }
            moves = safeForKingMoves;
        }
        return moves;
    }

    protected Collection<Move> getRegularMoves(Alliance alliance) {
        ArrayList<Move> moves = new ArrayList<>();

        for (Position startPos : _board.getOcupiedPositions()) {
            ChessPiece piece = _board.getPiece(startPos);
            Position currentPos;
            for (Direction direction : piece.getMovementDirections()) {
                currentPos = Positions.transform(startPos, direction);
                if (_board.isEmpty(currentPos)) {
                    /* Special Case: Pawn promotion */
                    if (piece instanceof Pawn && isPromotionPos(currentPos, alliance)) {
                        moves.addAll(PromotionMove.getPromotionMoves(startPos, currentPos, alliance));
                    }
                    else {
                        moves.add(new Move(startPos, currentPos));
                    }
                }
                if (piece.isMovementContinuous()) {
                    currentPos = Positions.transform(currentPos, direction);
                    while (_board.isEmpty(currentPos)) {
                        moves.add(new Move(startPos, currentPos));
                        currentPos = Positions.transform(currentPos, direction);
                    }
                }

                if (piece instanceof Pawn) {
                    // Double-tile move
                    Position candidate = Positions.transform(currentPos, NORTH);
                    if (_board.isEmpty(candidate)) {
                        moves.add(new PawnDoubleTileMove(startPos, candidate));
                    }

                    // Attack moves
                    candidate = Positions.transform(currentPos, EAST);
                    if (_board.isOnBoard(candidate) && _board.getPiece(candidate).getAlliance() != alliance) {
                        moves.add(new AttackMove(startPos, candidate));
                    }
                    candidate = Positions.transform(currentPos, WEST);
                    if (_board.isOnBoard(candidate) && _board.getPiece(candidate).getAlliance() != alliance) {
                        moves.add(new AttackMove(startPos, candidate));
                    }
                }
                else {
                    // Attack move
                    if (_board.isOnBoard(currentPos) && _board.getPiece(currentPos).getAlliance() != alliance) {
                        moves.add(new AttackMove(startPos, currentPos));
                    }
                }
            }
        }

        return moves;
    }

    // TODO: make this more generic
    protected Collection<Move> getEnPassentMoves(Alliance alliance) {
        ArrayList<Move> moves = new ArrayList<>();
        Move lastMove = _board.getLastMove();
        if (lastMove instanceof PawnDoubleTileMove) {
            Position enemyPos = lastMove.getDestination();
            Position candidatePos = Positions.transform(enemyPos, EAST);
            Piece candidatePiece = _board.getPiece(candidatePos);
            if (candidatePiece instanceof Pawn && candidatePiece.getAlliance() != alliance) {
                Position candidateDestination = Positions.transform(enemyPos,
                        alliance == Alliance.WHITE ? NORTH : SOUTH);
                moves.add(new EnPassantMove(candidatePos, candidateDestination, enemyPos));
            }
            candidatePos = Positions.transform(enemyPos, WEST);
            candidatePiece = _board.getPiece(candidatePos);
            if (candidatePiece instanceof Pawn && candidatePiece.getAlliance() != alliance) {
                Position candidateDestination = Positions.transform(enemyPos,
                        alliance == Alliance.WHITE ? NORTH : SOUTH);
                moves.add(new EnPassantMove(candidatePos, candidateDestination, enemyPos));
            }
        }
        return moves;
    }
    // TODO: HAVE TO make this more generic
    protected Collection<Move> getCastleMoves(Alliance alliance) {
        ArrayList<Move> moves = new ArrayList<>();
        if (alliance == Alliance.BLACK) {
            Piece king = _board.getPiece(BLACK_KING_START_POSITION);
            if (king instanceof  King && ((King) king).getMoveCount() == 0) {
                // Check East castle
                if (_board.isEmpty(TILE_F8) && _board.isEmpty(TILE_G8)){
                    Piece rook = _board.getPiece(BLACK_EAST_ROOK_START_POSITION);
                    if (rook instanceof Rook && ((Rook) rook).getMoveCount() == 0) {
                        if (isPositionSafe(TILE_F8, alliance) && isPositionSafe(TILE_G8, alliance)) {
                            moves.add(new CastleMove(BLACK_KING_START_POSITION, TILE_G8,
                                    BLACK_EAST_ROOK_START_POSITION, TILE_F8 ));
                        }
                    }
                }
                // Check West castle
                if (_board.isEmpty(TILE_B8) && _board.isEmpty(TILE_C8) && _board.isEmpty(TILE_D8)){
                    Piece rook = _board.getPiece(BLACK_WEST_ROOK_START_POSITION);
                    if (rook instanceof Rook && ((Rook) rook).getMoveCount() == 0) {
                        if (isPositionSafe(TILE_C8, alliance) && isPositionSafe(TILE_D8, alliance)) {
                            moves.add(new CastleMove(BLACK_KING_START_POSITION, TILE_C8,
                                    BLACK_WEST_ROOK_START_POSITION, TILE_D8 ));
                        }
                    }
                }
            }

        }
        else {
            Piece king = _board.getPiece(WHITE_KING_START_POSITION);
            if (king instanceof  King && ((King) king).getMoveCount() == 0) {
                // Check East castle
                if (_board.isEmpty(TILE_F1) && _board.isEmpty(TILE_G1)){
                    Piece rook = _board.getPiece(WHITE_EAST_ROOK_START_POSITION);
                    if (rook instanceof Rook && ((Rook) rook).getMoveCount() == 0) {
                        if (isPositionSafe(TILE_F1, alliance) && isPositionSafe(TILE_G1, alliance)) {
                            moves.add(new CastleMove(WHITE_KING_START_POSITION, TILE_G1,
                                    WHITE_EAST_ROOK_START_POSITION, TILE_F1 ));
                        }
                    }
                }
                // Check West castle
                if (_board.isEmpty(TILE_B1) && _board.isEmpty(TILE_C1) && _board.isEmpty(TILE_D1)){
                    Piece rook = _board.getPiece(WHITE_WEST_ROOK_START_POSITION);
                    if (rook instanceof Rook && ((Rook) rook).getMoveCount() == 0) {
                        if (isPositionSafe(TILE_C1, alliance) && isPositionSafe(TILE_D1, alliance)) {
                            moves.add(new CastleMove(WHITE_KING_START_POSITION, TILE_C1,
                                    WHITE_WEST_ROOK_START_POSITION, TILE_D1 ));
                        }
                    }
                }
            }


        }
        return moves;
    }

    protected Position getKingPosition(Alliance alliance) {
        Position kingPos = null;
        for (Position position : _board.getOcupiedPositions()) {
            if (_board.getPiece(position) instanceof King  && _board.getPiece(position).getAlliance() == alliance) {
                kingPos = position;
                break;
            }
        }
        return kingPos;
    }

    protected boolean isPositionSafe(Position pos, Alliance defenceAlliance) {
        Alliance enemyAlliance = defenceAlliance == Alliance.WHITE ? Alliance.BLACK : Alliance.WHITE;
        for (Move move : getRegularMoves(enemyAlliance)) {
            if (move instanceof AttackMove && move.getDestination() == pos) {
                return false;
            }
        }
        return true;
    }

    protected boolean isKingHasToBeSafe() {
        return true; // for easy modifications of other game-mods that extends this game.
    }
    @Override
    public GameState getGameState() {
        return null;
    }

    @Override
    public void makeMove(Move move) {
        if (getPossibleMoves().contains(move)) {
            _board.executeMove(move);
        }
        else {
            throw new RuntimeException("IllegalMoveExeption");
        }
    }

    @Override
    public void undoMove() {
        _board.undo();
    }

    public static void main(String[] args) {
        ChessGame chessGame = new ChessGame(new ChessBoard());
    }
}
