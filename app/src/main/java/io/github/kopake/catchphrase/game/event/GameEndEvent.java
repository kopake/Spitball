package io.github.kopake.catchphrase.game.event;

import io.github.kopake.catchphrase.game.team.Team;

public class GameEndEvent implements Event {
    private Team winningTeam;

    public GameEndEvent(Team winningTeam) {
        this.winningTeam = winningTeam;
    }

    public Team getWinningTeam() {
        return winningTeam;
    }
}
