package chess_game.enums;

public enum Direction {
        NORTH(0, -1),
        NORTH_EAST(1, -1),
        EAST(1, 0),
        SOUTH_EAST(1, 1),
        SOUTH(0, 1),
        SOUTH_WEST(-1, 1),
        WEST(-1, 0),
        NORTH_WEST(-1, -1),
        KNIGHT_NNE(1, -2),
        KNIGHT_NNW(-1, -2),
        KNIGHT_EEN(2, -1),
        KNIGHT_EES(2, 1),
        KNIGHT_SSE(1, 2),
        KNIGHT_SSW(-1, 2),
        KNIGHT_WWN(-2, -1),
        KNIGHT_WWS(-2, 1);

        public final int x;
        public final int y;

        private Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }
}
