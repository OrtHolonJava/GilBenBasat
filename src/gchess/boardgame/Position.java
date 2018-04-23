package gchess.boardgame;

/**
 * <h1>Position</h1>
 * A position is defined by two integers: x and y.
 * A position is immutable- once it was created it cannot be changed.
 *
 * @author  Gil Ben Basat
 * @version 1.0
 * @since   2018-04-23
 */
public class Position {
    // This is the largest x or y that a position can have for it to function correctly with hashCode().
    private final int MAX_INDEX = 256;
    private final int _x, _y;
    public Position(int x, int y) {
        _x = x;
        _y = y;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    @Override
    public String toString() {
        return "" + (char)('A' + _x) + (char)('8' - _y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            return _x == ((Position) obj)._x && _y == ((Position) obj)._y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return _x * MAX_INDEX + _y;
    }
}
