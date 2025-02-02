package io.github.kopake.spitball.event;

import io.github.kopake.spitball.game.team.Team;

public class ScoreModifyEvent implements Event {
    private final Team team;

    private final int valueChange;

    public ScoreModifyEvent(Team team, int valueChange) {
        this.team = team;
        this.valueChange = valueChange;
    }

    public Team getTeam() {
        return team;
    }

    public int getValueChange() {
        return valueChange;
    }
}
