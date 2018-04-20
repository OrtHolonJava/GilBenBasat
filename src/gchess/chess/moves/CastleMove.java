package gchess.chess.moves;

import gchess.boardgame.Move;
import gchess.boardgame.Position;

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
