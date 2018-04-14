package chess_game.game_states;

import board_game.Alliance;
import board_game.game_states.GameEnded;

public class KingIsOnTheHill extends GameEnded {
    private final Alliance _winningAlliance;

    public KingIsOnTheHill(Alliance winningAlliance) {
        _winningAlliance = winningAlliance;
    }

    public Alliance getWinningAlliance() {
        return _winningAlliance;
    }
}
