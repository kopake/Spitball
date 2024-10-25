package io.github.kopake.spitball.game;

import io.github.kopake.spitball.game.event.EventHandler;
import io.github.kopake.spitball.game.event.EventManager;
import io.github.kopake.spitball.game.event.GameEndEvent;
import io.github.kopake.spitball.game.event.GameStartEvent;
import io.github.kopake.spitball.game.event.ScoreModifyEvent;
import io.github.kopake.spitball.game.event.listeners.Listener;
import io.github.kopake.spitball.game.team.Team;

public class Scoreboard implements Listener {

    private static Scoreboard instance = new Scoreboard();
    private int teamOneScore = 0;
    private int teamTwoScore = 0;

    private Scoreboard() {
    }

    public static Scoreboard getInstance() {
        return instance;
    }

    public int getTeamOneScore() {
        return teamOneScore;
    }

    public int getTeamTwoScore() {
        return teamTwoScore;
    }

    public int getScore(Team team) {
        switch (team) {
            case ONE:
                return getTeamOneScore();
            case TWO:
                return getTeamTwoScore();
        }
        return -1;
    }

    @EventHandler
    public void onGameStart(GameStartEvent gameStartEvent) {
        teamOneScore = 0;
        teamTwoScore = 0;
    }

    @EventHandler
    public void onScoreModify(ScoreModifyEvent scoreModifyEvent) {
        switch (scoreModifyEvent.getTeam()) {
            case ONE:
                teamOneScore = teamOneScore + scoreModifyEvent.getValueChange();
                break;
            case TWO:
                teamTwoScore = teamTwoScore + scoreModifyEvent.getValueChange();
                break;
        }

        if (teamOneScore < 0)
            teamOneScore = 0;
        if (teamTwoScore < 0)
            teamTwoScore = 0;

        if (teamOneScore >= 7)
            EventManager.getInstance().dispatchEvent(new GameEndEvent(Team.ONE));
        else if (teamTwoScore >= 7)
            EventManager.getInstance().dispatchEvent(new GameEndEvent(Team.TWO));
    }
}
