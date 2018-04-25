package gchess.chess.states;

import gchess.boardgame.Alliance;
import gchess.boardgame.states.GameEnded;

/**
 * A game state of type: King Is On The Hill.
 */
public class KingIsOnTheHill extends GameEnded {
    private final Alliance _winningAlliance;

    public KingIsOnTheHill(Alliance winningAlliance) {
        _winningAlliance = winningAlliance;
    }

    public Alliance getWinningAlliance() {
        return _winningAlliance;
    }
}
