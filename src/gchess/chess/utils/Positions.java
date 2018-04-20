package gchess.chess.utils;

import gchess.boardgame.Position;
import gchess.chess.enums.Direction;

public class Positions {
    public static Position transform(Position startPos, Direction direction) {
        return new Position(startPos.getX() + direction.x, startPos.getY() + direction.y);
    }
}
