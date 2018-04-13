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
}
