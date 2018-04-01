package chess_game.moves;

import board_game.Move;
import board_game.Position;

public class CastleMove extends Move {
    private final Position _rookSource, _rookDestination;
    public CastleMove(Position source, Position destination, Position rookSource, Position rookDestination) {
        super(source, destination);
        _rookSource = rookSource;
        _rookDestination = rookDestination;
    }

    public Position getRookSource() {
        return _rookSource;
    }

    public Position getRookDestination() {
        return _rookDestination;
    }
}
