package gchess.chess.moves;

import gchess.boardgame.Position;

public class EnPassantMove extends AttackMove {
    private Position _enemyPos;
    public EnPassantMove(Position source, Position destination, Position enemyPos) {
        super(source, destination);
        _enemyPos = enemyPos;
    }

    public Position getEnemyPos() {
        return _enemyPos;
    }
}
