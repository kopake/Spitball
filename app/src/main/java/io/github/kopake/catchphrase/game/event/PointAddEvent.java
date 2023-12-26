package io.github.kopake.catchphrase.game.event;

import io.github.kopake.catchphrase.game.team.Team;

public class PointAddEvent implements Event {
    private Team team;

    public PointAddEvent(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}
