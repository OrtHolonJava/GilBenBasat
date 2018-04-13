package chess_game.moves;

import board_game.Move;
import board_game.Position;

public class AttackMove extends Move {
    public AttackMove(Position source, Position destination) {
        super(source, destination);
    }
}
