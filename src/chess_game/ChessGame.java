package chess_game;

import board_game.*;
import chess_game.enums.Direction;
import chess_game.game_states.Check;
import chess_game.game_states.CheckMate;
import board_game.game_states.GameEnded;
import board_game.game_states.InGame;
import chess_game.game_states.Tie;
import chess_game.moves.*;
import chess_game.pieces.*;
import chess_game.utils.Positions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static board_game.Alliance.BLACK;
import static board_game.Alliance.WHITE;
import static chess_game.enums.Direction.*;
import static chess_game.utils.BoardUtils.* ;
import static chess_game.utils.ChessGameUtils.enemyOf;

public class ChessGame extends BoardGame<ChessBoard> {
    public ChessGame() {
        super(new ChessBoard(), Arrays.asList(WHITE, BLACK));
        initBoard();
    }

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

    public Collection<Move> getRegularMoves(Alliance alliance) {
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
                    try {
                        if (_board.isOccupied(candidate) && getPiece(candidate).getAlliance() != alliance) {
                            moves.add(new AttackMove(piecePos, candidate));
                        }
                    } catch (Exception ignore) {
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

    // TODO: make this more generic
    public Collection<Move> getEnPassentMoves(Alliance alliance) {
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

    // TODO: HAVE TO make this more generic
    protected Collection<Move> getCastleMoves(Alliance alliance) {
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

    protected boolean isPositionSafe(Position pos, Alliance defenceAlliance) {
        for (Move move : getRegularMoves(enemyOf(defenceAlliance))) {
            if (move instanceof AttackMove && move.getDestination().equals(pos)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public GameState getGameState() {
        Alliance defenceAlliance = getCurrentTurnAlliance();
        Position kingPos = getKingPosition(defenceAlliance);

        GameState state = getEndGameState();
        // if its not an end game state- check if theres a check.
        if (state == null) {
            if (isUnderCheck()) {
                return new Check(kingPos);
            } else {
                return new InGame();
            }
        }
        return state;
    }

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
        _allianceTurnIndex++;
        onMakeMoveFinished(move);
    }

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
        _allianceTurnIndex--;
        onUndoMoveFinished(move);
    }

    @Override
    public BoardGame<ChessBoard> getCopy() {
        ChessGame game = new ChessGame();
        game._allianceCycle = _allianceCycle.clone();
        game._allianceTurnIndex = _allianceTurnIndex;
        game._board = (ChessBoard) _board.getCopy();
        return game;
    }

    public Collection<Position> getOccupiedPositions() {
        return _board.getOccupiedPositions();
    }

    public ChessPiece getPiece(Position pos) {
        return _board.getPiece(pos);
    }

    public String[][] getBoardRepresentation() {
        return _board.getBoardRepresentation();
    }

    /* Modular Functions Section */

    protected boolean isKingHasToBeSafe() {
        return true;
    }

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

    protected boolean isUnderCheck() {
        Alliance defenceAlliance = getCurrentTurnAlliance();
        Position kingPos = getKingPosition(defenceAlliance);
        return !isPositionSafe(kingPos, defenceAlliance);
    }

    protected  GameEnded getEndGameState() {
        Alliance defenceAlliance = getCurrentTurnAlliance();
        Position kingPos = getKingPosition(defenceAlliance);
        if (getPossibleMoves().isEmpty()) {
            if (isUnderCheck()) {
                return new CheckMate(kingPos, enemyOf(defenceAlliance));
            }
            else {
                return new Tie();
            }
        }
        else {
            return getSpecialEndGameState();
        }
    }

    protected GameEnded getSpecialEndGameState() {
        return null;
    }
    
    protected Alliance getCurrentTurnAlliance() {
        return _allianceCycle[_allianceTurnIndex % 2];
    }

    protected void onMakeMoveStarted(Move move){}

    protected void onMakeMoveFinished(Move move){}

    protected void onUndoMoveStarted(Move move){}

    protected void onUndoMoveFinished(Move move){}
}
