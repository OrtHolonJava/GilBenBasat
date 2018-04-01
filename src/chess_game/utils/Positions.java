package chess_game.utils;

import board_game.Position;
import chess_game.enums.Direction;

public class Positions {
    public static Position transform(Position startPos, Direction direction) {
        return new Position(startPos.getX() + direction.x, startPos.getY() + direction.y);
    }
}
