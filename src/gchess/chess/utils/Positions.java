package gchess.chess.utils;

import gchess.boardgame.Position;
import gchess.chess.enums.Direction;

public class Positions {
    /**
     * This function returns the next position from a starting position and a direction.
     * @param startPos The position from which you start moving.
     * @param direction The direction of movement.
     * @return A new instance of Position.
     */
    public static Position transform(Position startPos, Direction direction) {
        return new Position(startPos.getX() + direction.x, startPos.getY() + direction.y);
    }
}
