package gchess.chess;

import gchess.boardgame.*;
import gchess.boardgame.states.GameEnded;
import gchess.boardgame.states.InGame;
import gchess.chess.enums.Direction;
import gchess.chess.moves.*;
import gchess.chess.pieces.*;
import gchess.chess.states.Check;
import gchess.chess.states.CheckMate;
import gchess.chess.states.Tie;
import gchess.chess.utils.Positions;

import java.util.ArrayList;
import java.util.Collection;

import static gchess.boardgame.Alliance.BLACK;
import static gchess.boardgame.Alliance.WHITE;
import static gchess.chess.enums.Direction.*;
import static gchess.chess.utils.BoardUtils.*;
import static gchess.chess.utils.ChessGameUtils.enemyOf;

/**
 * <h1>Chess Game</h1>
 * This class is the classic chess game and is the core of any implementation of any chess mode. Examples for modes
 * you can create on top of it: Chess960, Atomic, King-Of-The-Hill and so much more.
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public class ChessGame extends BoardGame<ChessBoard> {
    protected Alliance _currentAlliance;

    public ChessGame() {
        super(new ChessBoard());
        initBoard();
        _currentAlliance = WHITE;
    }

    /**
     * This function returns a collection of the possible (legal) moves of the current alliance.
     * @return Collection of possible moves.
     */
    @Override
    public Collection<Move> getPossibleMoves() {
        ArrayList<Move> moves = new ArrayList<>(75);
        Alliance alliance = getCurrentTurnAlliance();

        /* Add Regular Moves: includes pawn's double-tile move and pawn promotion */
        moves.addAll(getRegularMoves(alliance));

        /* Add Special Move: Pawn En Passant */
        moves.addAll(getEnPassentMoves(alliance));

        /* Add Special Move: Castle */
        moves.addAll(getCastleMoves(alliance));

        /* In Case King has to be Safe: remove all the moves that makes the king un-safe. */
        if (this.isKingHasToBeSafe()) {
            ArrayList<Move> safeForKingMoves = new ArrayList<>();
            if (getKingPosition(alliance) == null) {
                throw new RuntimeException("King was not found.");
            }
            for (Move move : moves) {
                makeMove(move);
                if (isPositionSafe(getKingPosition(alliance), alliance)) {
                    safeForKingMoves.add(move);
                }
                undoMove();
            }
            moves = safeForKingMoves;
        }
        return moves;
    }

    /**
     * This function returns all the regular possible moves of the alliance given as parameter, namely: simple movement,
     * normal attack, pawn double tile move and pawn promotion.
     * @param alliance the alliance we want to get the possible moves of (usually the current turn alliance).
     * @return Collection of possible regular moves.
     */
    private Collection<Move> getRegularMoves(Alliance alliance) {
        ArrayList<Move> moves = new ArrayList<>(75);
        for (Position piecePos : _board.getOccupiedPositions()) {
            ChessPiece piece = getPiece(piecePos);
            if (piece.getAlliance() != alliance) {
                continue;
            }
            Position currentPos;
            for (Direction direction : piece.getMovementDirections()) {
                currentPos = Positions.transform(piecePos, direction);
                if (_board.isEmpty(currentPos)) {
                    /* Special Case: Pawn special moves */
                    if (piece instanceof Pawn) {
                        /* Add Promotion Move: Only if its in a promotion position. */
                        if (isPromotionPos(currentPos, alliance)) {
                            moves.addAll(PromotionMove.getPromotionMoves(piecePos, currentPos, alliance));
                        }
                        /* Add Double-Tile Move: Only if pawn is in his initial location. */
                        if (isPawnInInitialRow(piecePos, alliance)) {
                            Position candidate = Positions.transform(currentPos, piece.getMovementDirections().iterator().next());
                            if (_board.isEmpty(candidate)) {
                                moves.add(new PawnDoubleTileMove(piecePos, candidate));
                            }
                        }
                    }
                    moves.add(new Move(piecePos, currentPos));
                }
                if (piece.isMovementContinuous()) {
                    while (_board.isEmpty(currentPos)) {
                        moves.add(new Move(piecePos, currentPos));
                        currentPos = Positions.transform(currentPos, direction);
                    }
                }
                /* Add Atack Moves */
                if (piece instanceof Pawn) {
                    Position candidate = Positions.transform(currentPos, EAST);
                    if (_board.isOccupied(candidate) && getPiece(candidate).getAlliance() != alliance) {
                        moves.add(new AttackMove(piecePos, candidate));
                    }
                    candidate = Positions.transform(currentPos, WEST);
                    if (_board.isOccupied(candidate) && getPiece(candidate).getAlliance() != alliance) {
                        moves.add(new AttackMove(piecePos, candidate));
                    }
                } else {
                    try {
                        if (_board.isOccupied(currentPos) && getPiece(currentPos).getAlliance() != alliance) {
                            moves.add(new AttackMove(piecePos, currentPos));
                        }
                    } catch (Exception ignored) {

                    }
                }
            }
        }

        return moves;
    }

    /**
     * This function returns all the possible pawn's en-passent moves of the alliance given as a parameter.
     * @param alliance the alliance we want to get the possible moves of (usually the current turn alliance).
     * @return Collection of possible en-passent moves.
     */
    private Collection<Move> getEnPassentMoves(Alliance alliance) {
        ArrayList<Move> moves = new ArrayList<>(2);
        Move lastMove = _board.getLastMove();
        if (lastMove instanceof PawnDoubleTileMove) {
            Position enemyPos = lastMove.getDestination();
            Position candidatePos = Positions.transform(enemyPos, EAST);
            Piece candidatePiece = getPiece(candidatePos);
            if (candidatePiece instanceof Pawn && candidatePiece.getAlliance() == alliance) {
                Position candidateDestination = Positions.transform(enemyPos,
                    alliance == Alliance.WHITE ? NORTH : SOUTH);
                moves.add(new EnPassantMove(candidatePos, candidateDestination, enemyPos));
            }
            candidatePos = Positions.transform(enemyPos, WEST);
            candidatePiece = getPiece(candidatePos);
            if (candidatePiece instanceof Pawn && candidatePiece.getAlliance() == alliance) {
                Position candidateDestination = Positions.transform(enemyPos,
                    alliance == Alliance.WHITE ? NORTH : SOUTH);
                moves.add(new EnPassantMove(candidatePos, candidateDestination, enemyPos));
            }
        }
        return moves;
    }

    /**
     * This function returns all the possible ccastle moves of the alliance given as a parameter.
     * @param alliance the alliance we want to get the possible moves of (usually the current turn alliance).
     * @return Collection of possible castle moves.
     */
    private Collection<Move> getCastleMoves(Alliance alliance) {
        ArrayList<Move> moves = new ArrayList<>(2);
        if (alliance == BLACK) {
            Piece king = getPiece(BLACK_KING_START_POSITION);
            if (king instanceof King && ((King) king).getMoveCount() == 0) {
                // Check East castle
                if (_board.isEmpty(TILE_F8) && _board.isEmpty(TILE_G8)) {
                    Piece rook = getPiece(BLACK_EAST_ROOK_START_POSITION);
                    if (rook instanceof Rook && ((Rook) rook).getMoveCount() == 0) {
                        if (isPositionSafe(TILE_F8, alliance) && isPositionSafe(TILE_G8, alliance)) {
                            moves.add(new CastleMove(BLACK_KING_START_POSITION, TILE_G8,
                                BLACK_EAST_ROOK_START_POSITION, TILE_F8));
                        }
                    }
                }
                // Check West castle
                if (_board.isEmpty(TILE_B8) && _board.isEmpty(TILE_C8) && _board.isEmpty(TILE_D8)) {
                    Piece rook = getPiece(BLACK_WEST_ROOK_START_POSITION);
                    if (rook instanceof Rook && ((Rook) rook).getMoveCount() == 0) {
                        if (isPositionSafe(TILE_C8, alliance) && isPositionSafe(TILE_D8, alliance)) {
                            moves.add(new CastleMove(BLACK_KING_START_POSITION, TILE_C8,
                                BLACK_WEST_ROOK_START_POSITION, TILE_D8));
                        }
                    }
                }
            }

        } else {
            Piece king = getPiece(WHITE_KING_START_POSITION);
            if (king instanceof King && ((King) king).getMoveCount() == 0) {
                // Check East castle
                if (_board.isEmpty(TILE_F1) && _board.isEmpty(TILE_G1)) {
                    Piece rook = getPiece(WHITE_EAST_ROOK_START_POSITION);
                    if (rook instanceof Rook && ((Rook) rook).getMoveCount() == 0) {
                        if (isPositionSafe(TILE_F1, alliance) && isPositionSafe(TILE_G1, alliance)) {
                            moves.add(new CastleMove(WHITE_KING_START_POSITION, TILE_G1,
                                WHITE_EAST_ROOK_START_POSITION, TILE_F1));
                        }
                    }
                }
                // Check West castle
                if (_board.isEmpty(TILE_B1) && _board.isEmpty(TILE_C1) && _board.isEmpty(TILE_D1)) {
                    Piece rook = getPiece(WHITE_WEST_ROOK_START_POSITION);
                    if (rook instanceof Rook && ((Rook) rook).getMoveCount() == 0) {
                        if (isPositionSafe(TILE_C1, alliance) && isPositionSafe(TILE_D1, alliance)) {
                            moves.add(new CastleMove(WHITE_KING_START_POSITION, TILE_C1,
                                WHITE_WEST_ROOK_START_POSITION, TILE_D1));
                        }
                    }
                }
            }


        }
        return moves;
    }

    /**
     * This function returns the king position of the requested alliance.
     * @param alliance The alliance of the wanted king.
     * @return The position of the wanted king.
     */
    protected Position getKingPosition(Alliance alliance) {
        Position kingPos = null;
        for (Position position : _board.getOccupiedPositions()) {
            if (getPiece(position) instanceof King && getPiece(position).getAlliance() == alliance) {
                kingPos = position;
                break;
            }
        }
        return kingPos;
    }

    /**
     * This function checks if a position is safe for a certain alliance.
     * Safe position is a position which is not a destination of any of the enemy attack moves.
     * @param pos The position to be checked.
     * @param defenceAlliance The alliance which his enemy is the one the position need to be safe from.
     * @return true if the position is safe, false otherwise.
     */
    private boolean isPositionSafe(Position pos, Alliance defenceAlliance) {
        for (Move move : getRegularMoves(enemyOf(defenceAlliance))) {
            if (move instanceof AttackMove && move.getDestination().equals(pos)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function returns the current game state. e.g: Check, Tie etc.
     * @return The current game state.
     */
    @Override
    public GameState getGameState() {
        Alliance defenceAlliance = getCurrentTurnAlliance();
        Position kingPos = getKingPosition(defenceAlliance);

        GameState state = getEndGameState();
        // if its not an end game state- check if there's a check.
        if (state == null) {
            if (isUnderCheck()) {
                return new Check(kingPos);
            } else {
                return new InGame();
            }
        }
        return state;
    }

    /**
     * This function executes the move that's given as a parameter and makes changes in the board as needed.
     * @param move The move that needs to be executed.
     */
    @Override
    public void makeMove(Move move) {
        onMakeMoveStarted(move);
        ChessPiece movingPiece = getPiece(move.getSource());
        if (movingPiece instanceof Rook) {
            ((Rook) movingPiece).incMoveCount();
        } else if (movingPiece instanceof King) {
            ((King) movingPiece).incMoveCount();
        }

        if (move instanceof AttackMove) {
            Position attackedPiecePos;
            if (move instanceof EnPassantMove) {
                attackedPiecePos = ((EnPassantMove) move).getEnemyPos();
            } else {
                attackedPiecePos = move.getDestination();
            }
            _board.getTakenOutPieces().push(getPiece(attackedPiecePos));
            _board.removePiece(attackedPiecePos);
        } else if (move instanceof CastleMove) {
            _board.movePiece(((CastleMove) move).getRookSource(), ((CastleMove) move).getRookDestination());
        } else if (move instanceof PromotionMove) {
            _board.setPiece(move.getSource(), ((PromotionMove) move).getPromotionPiece());
        }
        _board.movePiece(move.getSource(), move.getDestination());
        _board.getMoveHistory().push(move);
        _currentAlliance = _currentAlliance == WHITE ? BLACK : WHITE;
        onMakeMoveFinished(move);
    }

    /**
     * This function return to the state of the game before the last move. Undo twice to go back two states before and
     * so on.
     */
    @Override
    public void undoMove() {
        Move move = _board.getMoveHistory().pop();
        onUndoMoveStarted(move);
        ChessPiece movingPiece = getPiece(move.getDestination());

        _board.movePiece(move.getDestination(), move.getSource());

        if (movingPiece instanceof Rook) {
            ((Rook) movingPiece).decMoveCount();
        } else if (movingPiece instanceof King) {
            ((King) movingPiece).decMoveCount();
        }

        if (move instanceof AttackMove) {
            Position attackedPiecePos;
            if (move instanceof EnPassantMove) {
                attackedPiecePos = ((EnPassantMove) move).getEnemyPos();
            } else {
                attackedPiecePos = move.getDestination();
            }
            _board.setPiece(attackedPiecePos, _board.getTakenOutPieces().pop());
        } else if (move instanceof CastleMove) {
            _board.movePiece(((CastleMove) move).getRookDestination(), ((CastleMove) move).getRookSource());
        } else if (move instanceof PromotionMove) {
            _board.setPiece(move.getSource(), new Pawn(movingPiece.getAlliance()));
        }
        _currentAlliance = _currentAlliance == WHITE ? BLACK : WHITE;
        onUndoMoveFinished(move);
    }

    /**
     * This function returns a copy of the current game so that any modifications on the copy won't affect the current
     * game.
     * @return A copy of the game.
     */
    @Override
    public BoardGame<ChessBoard> getCopy() {
        ChessGame game = new ChessGame();
        game._currentAlliance = _currentAlliance;
        game._board = (ChessBoard) _board.getCopy();
        return game;
    }

    /**
     * This function returns all the positions that are occupied by pieces.
     * @return Collection of positions of occupied positions.
     */
    public Collection<Position> getOccupiedPositions() {
        return _board.getOccupiedPositions();
    }

    /**
     * This function returns the piece that seats in the position received as parameter.
     * @param pos The position of the piece we want to get.
     * @return The piece that was requested.
     */
    public ChessPiece getPiece(Position pos) {
        return _board.getPiece(pos);
    }

    /**
     * This function returns a nice and easy representation of the chess board using N x M matrix and enums of pieces.
     * @return N x M matrix of pieces enums.
     */
    public gchess.chess.enums.Piece[][] getBoardRepresentation() {
        return _board.getBoardRepresentation();
    }

    /**
     * This function determines whether the king needs to be safe or not.
     * Safe means that no enemy piece can attack the king in the current state.
     * <b>Note:</b> This function is there to be overrated by game-modes that don't need the king to be safe.
     * @return true if the king needs to be safe, false otherwise.
     */
    protected boolean isKingHasToBeSafe() {
        return true;
    }

    /**
     * This function initiate the board with pieces for when the game starts.
     */
    protected void initBoard() {
        _board.setPiece(new Position(0, 0), new Rook(BLACK));
        _board.setPiece(new Position(1, 0), new Knight(BLACK));
        _board.setPiece(new Position(2, 0), new Bishop(BLACK));
        _board.setPiece(new Position(3, 0), new Queen(BLACK));
        _board.setPiece(new Position(4, 0), new King(BLACK));
        _board.setPiece(new Position(5, 0), new Bishop(BLACK));
        _board.setPiece(new Position(6, 0), new Knight(BLACK));
        _board.setPiece(new Position(7, 0), new Rook(BLACK));

        _board.setPiece(new Position(0, 1), new Pawn(BLACK));
        _board.setPiece(new Position(1, 1), new Pawn(BLACK));
        _board.setPiece(new Position(2, 1), new Pawn(BLACK));
        _board.setPiece(new Position(3, 1), new Pawn(BLACK));
        _board.setPiece(new Position(4, 1), new Pawn(BLACK));
        _board.setPiece(new Position(5, 1), new Pawn(BLACK));
        _board.setPiece(new Position(6, 1), new Pawn(BLACK));
        _board.setPiece(new Position(7, 1), new Pawn(BLACK));

        _board.setPiece(new Position(0, 7), new Rook(WHITE));
        _board.setPiece(new Position(1, 7), new Knight(WHITE));
        _board.setPiece(new Position(2, 7), new Bishop(WHITE));
        _board.setPiece(new Position(3, 7), new Queen(WHITE));
        _board.setPiece(new Position(4, 7), new King(WHITE));
        _board.setPiece(new Position(5, 7), new Bishop(WHITE));
        _board.setPiece(new Position(6, 7), new Knight(WHITE));
        _board.setPiece(new Position(7, 7), new Rook(WHITE));

        _board.setPiece(new Position(0, 6), new Pawn(WHITE));
        _board.setPiece(new Position(1, 6), new Pawn(WHITE));
        _board.setPiece(new Position(2, 6), new Pawn(WHITE));
        _board.setPiece(new Position(3, 6), new Pawn(WHITE));
        _board.setPiece(new Position(4, 6), new Pawn(WHITE));
        _board.setPiece(new Position(5, 6), new Pawn(WHITE));
        _board.setPiece(new Position(6, 6), new Pawn(WHITE));
        _board.setPiece(new Position(7, 6), new Pawn(WHITE));
    }

    /**
     * This function checks if the king of current alliance is under check.
     * @return
     */
    protected boolean isUnderCheck() {
        Alliance defenceAlliance = getCurrentTurnAlliance();
        Position kingPos = getKingPosition(defenceAlliance);
        return !isPositionSafe(kingPos, defenceAlliance);
    }

    /**
     * This function returns the current end-game state. If there isn't any, it returns null.
     * @return Current end-game state.
     */
    private GameEnded getEndGameState() {
        Alliance defenceAlliance = getCurrentTurnAlliance();
        Position kingPos = getKingPosition(defenceAlliance);
        if (getSpecialEndGameState() != null)
            return getSpecialEndGameState();
        else {
            if (getPossibleMoves().isEmpty()) {
                if (isUnderCheck()) {
                    return new CheckMate(kingPos, enemyOf(defenceAlliance));
                } else {
                    return new Tie();
                }
            }
        }
        return null;
    }

    /**
     * This function returns the current special end game state, if there isn't any it returns null.
     * <b>Note:</b> This function is meant to be overrated by other game-modes that have special end-game states.
     * @return Current special end-game state
     */
    protected GameEnded getSpecialEndGameState() {
        return null;
    }

    /**
     * This function returns the current turn alliance.
     * @return Alliance of current turn.
     */
    protected Alliance getCurrentTurnAlliance() {
        return _currentAlliance;
    }

    /**
     * This function is called before a move is executed.
     * <b>Note:</b> This function is meant to be overrated by other game-modes that needs this kind of additional functionality.
     * @param move The move that is going to be executed after this call.
     */
    private void onMakeMoveStarted(Move move) {
    }

    /**
     * This function is called after a move has finished executing.
     * <b>Note:</b> This function is meant to be overrated by other game-modes that needs this kind of additional functionality.
     * @param move The move that has finished executing before this call.
     */
    protected void onMakeMoveFinished(Move move) {
    }

    /**
     * This function is called before an undo operation is executed.
     * <b>Note:</b> This function is meant to be overrated by other game-modes that needs this kind of additional functionality.
     * @param move The move that is going to be canceled after this call.
     */
    protected void onUndoMoveStarted(Move move) {
    }

    /**
     * This function is called after an undo operation has finished executing.
     * <b>Note:</b> This function is meant to be overrated by other game-modes that needs this kind of additional functionality.
     * @param move The move that was canceled as a result of the undo operation.
     */
    private void onUndoMoveFinished(Move move) {
    }
}
