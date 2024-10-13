package io.github.kopake.catchphrase.game.event;

import io.github.kopake.catchphrase.game.team.Team;

public class ScoreModifyEvent implements Event {
    private Team team;

    private int valueChange;

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
