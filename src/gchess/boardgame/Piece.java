package gchess.boardgame;

public abstract class Piece {
    protected final Alliance _alliance;

    protected Piece(Alliance alliance) {
        _alliance = alliance;
    }

    public Alliance getAlliance() {
        return _alliance;
    }
}
