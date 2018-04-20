package gchess.boardgame;

public class Position {
    private int _x, _y;
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
        return _x * 256 + _y;
    }
}
