package io.github.kopake.spitball.event;

import io.github.kopake.spitball.game.team.Team;

public class GameEndEvent implements Event {
    private Team winningTeam;

    public GameEndEvent(Team winningTeam) {
        this.winningTeam = winningTeam;
    }

    public Team getWinningTeam() {
        return winningTeam;
    }
}
