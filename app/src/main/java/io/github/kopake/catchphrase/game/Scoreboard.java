package io.github.kopake.catchphrase.game;

import io.github.kopake.catchphrase.game.event.EventHandler;
import io.github.kopake.catchphrase.game.event.EventManager;
import io.github.kopake.catchphrase.game.event.GameEndEvent;
import io.github.kopake.catchphrase.game.event.GameStartEvent;
import io.github.kopake.catchphrase.game.event.PointAddEvent;
import io.github.kopake.catchphrase.game.event.listeners.Listener;
import io.github.kopake.catchphrase.game.team.Team;

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
    public void onPointAdd(PointAddEvent pointAddEvent) {
        switch (pointAddEvent.getTeam()) {
            case ONE:
                teamOneScore++;
                break;
            case TWO:
                teamTwoScore++;
                break;
        }

        if (teamOneScore >= 7)
            EventManager.getInstance().dispatchEvent(new GameEndEvent(Team.ONE));
        else if (teamTwoScore >= 7)
            EventManager.getInstance().dispatchEvent(new GameEndEvent(Team.TWO));
    }
}
