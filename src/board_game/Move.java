package board_game;

public class Move {
    private Position _source, _destination;
    public Move(Position source, Position destination) {
        _source = source;
        _destination = destination;
    }

    public Position getSource() {
        return _source;
    }

    public Position getDestination() {
        return _destination;
    }

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
