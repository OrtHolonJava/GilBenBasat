package gchess.boardgame;

/**
 * <h1>Move</h1>
 * A move is defined by two positions: a source and a destination.
 * This is what a player sends to a game in order to make a move.
 *
 * @see Position
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public class Move {
    private Position _source, _destination;
    public Move(Position source, Position destination) {
        _source = source;
        _destination = destination;
    }

    /**
     * Returns the source position of the move.
     * @return The source position.
     */
    public Position getSource() {
        return _source;
    }

    /**
     * Returns the destination position os the move.
     * @return The destination position.
     */
    public Position getDestination() {
        return _destination;
    }

    /**
     * Returns a nice representation of a move: pos1 -> pos2.
     * @return String representation of this move.
     */
    @Override
    public String toString() {
        return _source.toString() + " -> " + _destination.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move) {
            if (_source != null && _destination != null) {
                return _source.equals(((Move) obj)._source) && _destination.equals(((Move) obj)._destination);
            }
        }
        return false;
    }
}
