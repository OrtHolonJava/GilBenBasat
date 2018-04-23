package gchess.boardgame;

/**
 * <h1>Piece</h1>
 * This class represents the object that seats in the Board as part of the game we're playing.
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public abstract class Piece {
    protected final Alliance _alliance;

    protected Piece(Alliance alliance) {
        _alliance = alliance;
    }

    public Alliance getAlliance() {
        return _alliance;
    }
}
