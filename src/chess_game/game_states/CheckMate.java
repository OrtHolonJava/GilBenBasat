package chess_game.game_states;

import board_game.Alliance;
import board_game.game_states.GameEnded;
import board_game.Position;

public class CheckMate extends GameEnded {
    private final Position _checkedKingPos;
    private final Alliance _winningAlliance;

    public CheckMate(Position checkedKingPos, Alliance winningAlliance) {
        _checkedKingPos = checkedKingPos;
        _winningAlliance = winningAlliance;
    }

    public Position getCheckedKingPos() {
        return _checkedKingPos;
    }

    public Alliance getWinningAlliance() {
        return _winningAlliance;
    }
}
