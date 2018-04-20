package gchess.chess.moves;

import gchess.boardgame.Alliance;
import gchess.boardgame.Move;
import gchess.boardgame.Position;
import gchess.chess.ChessPiece;
import gchess.chess.pieces.Bishop;
import gchess.chess.pieces.Knight;
import gchess.chess.pieces.Queen;
import gchess.chess.pieces.Rook;

import java.util.Arrays;
import java.util.Collection;

public class PromotionMove extends Move {
    private final ChessPiece _piece;
    public PromotionMove(Position source, Position destination, ChessPiece piece) {
        super(source, destination);
        if (piece instanceof Queen || piece instanceof Rook || piece instanceof Bishop || piece instanceof Knight) {
            _piece = piece;
        }
        else {
            throw new RuntimeException("Tried to promote to an unpromotable piece type.");
        }
    }

    public ChessPiece getPromotionPiece() {
        return _piece;
    }

    public static Collection<PromotionMove> getPromotionMoves(Position source, Position destination,
                                                              Alliance alliance) {
        return Arrays.asList(
                new PromotionMove(source, destination, new Queen(alliance)),
                new PromotionMove(source, destination, new Rook(alliance)),
                new PromotionMove(source, destination, new Bishop(alliance)),
                new PromotionMove(source, destination, new Knight(alliance)));
    }
}
