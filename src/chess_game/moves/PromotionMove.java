package chess_game.moves;

import board_game.Alliance;
import board_game.Move;
import board_game.Position;
import chess_game.ChessPiece;
import chess_game.pieces.Bishop;
import chess_game.pieces.Knight;
import chess_game.pieces.Queen;
import chess_game.pieces.Rook;

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
